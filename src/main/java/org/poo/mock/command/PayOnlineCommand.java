package org.poo.mock.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.currency.ForexGenie;
import org.poo.banking.seller.Seller;
import org.poo.banking.transaction.TransactionTable;
import org.poo.banking.transaction.ZeroPaymentReceiver;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Account;
import org.poo.banking.user.card.Card;
import org.poo.banking.user.account.exception.FrozenCardException;
import org.poo.banking.user.account.exception.InsufficientFundsException;
import org.poo.banking.user.account.exception.MinimumBalanceReachedException;
import org.poo.banking.user.tracking.TrackingNode;

import java.util.Objects;
import java.util.Optional;

public final class PayOnlineCommand extends BankingCommand {
    private final String cardNumber;
    private final double amount;
    private final String currency;
    private final String description;
    private final String sellerName;
    private final String email;
    private final int timestamp;

    public PayOnlineCommand(final String command, final String cardNumber, final double amount,
                            final String currency, final String description, final String sellerName,
                            final String email, final int timestamp) {
        super(command);
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.sellerName = sellerName;
        this.email = email;
        this.timestamp = timestamp;
    }

    @Override
    public Optional<ObjectNode> execute() {
        if (amount == 0) {
            return Optional.empty();
        }
        BankingManager.getInstance().setTime(timestamp);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        Optional<User> userResult = BankingManager.getInstance().getUserByFeature(email);
        if (userResult.isEmpty()) {
            return Optional.empty();
        }
        User user = userResult.get();

        Optional<Card> cardResult = user.getCardByCardNumber(cardNumber);
        if (cardResult.isEmpty()) {
            ObjectNode outputNode = objectMapper.createObjectNode();
            outputNode.put("description", "Card not found");
            outputNode.put("timestamp", timestamp);

            objectNode.put("command", command);
            objectNode.put("timestamp", timestamp);
            objectNode.put("output", outputNode);
            return Optional.ofNullable(objectNode);
        }
        Card card = cardResult.get();

        Optional<Seller> sellerResult = BankingManager.getInstance().getSellerByFeature(sellerName);
        if (sellerResult.isEmpty()) {
            return Optional.empty();
        }
        Seller seller = sellerResult.get();

        TrackingNode.TrackingNodeBuilder trackingBuilder = new TrackingNode.TrackingNodeBuilder()
                .setTimestamp(timestamp)
                .setProducer(card.getOwner());
        TransactionTable transaction = new TransactionTable(card, new ZeroPaymentReceiver(), amount,
                currency);
        try {
            transaction.collect();
            ForexGenie forexGenie = BankingManager.getInstance().getForexGenie();
            double convertedPaidAmount = forexGenie.queryRate(currency,
                    card.getOwner().getCurrency(), amount);
            trackingBuilder.setAmount(convertedPaidAmount)
                    .setSeller(sellerName)
                    .setDescription("Card payment");

            // Handle Cashback adding
            Account account = card.getOwner();
            if (Objects.equals(seller.getCashbackStrategy(), "nrOfTransactions")) {
//                account.setNumberOfTransactions(account.getNumberOfTransactions() + 1);
            } else if (Objects.equals(seller.getCashbackStrategy(), "spendingThreshold")) {
                account.getOwningUser().getServicePlan().HandleCashbackForSpending(convertedPaidAmount,
                        account.getCurrency(), account);
            }
        } catch (InsufficientFundsException
                 | MinimumBalanceReachedException
                 | FrozenCardException e) {
            trackingBuilder.setDescription(e.getMessage());
        } finally {
            user.getUserTracker().onTransaction(trackingBuilder.build());
        }
        return Optional.empty();
    }
}
