package org.poo.mock.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.currency.ForexGenie;
import org.poo.banking.transaction.TransactionTable;
import org.poo.banking.transaction.ZeroPaymentReceiver;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Account;
import org.poo.banking.user.account.exception.InsufficientFundsException;
import org.poo.banking.user.card.Card;
import org.poo.banking.user.tracking.TrackingNode;

import java.util.Optional;

public final class CashWithdrawalCommand extends BankingCommand {
    private final String cardNumber;
    private final double amount;
    private final String email;
    private final String location;
    private final int timestamp;

    public CashWithdrawalCommand(String command, String cardNumber, double amount, String email,
                                 String location, int timestamp) {
        super(command);
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.email = email;
        this.location = location;
        this.timestamp = timestamp;
    }

    @Override
    public Optional<ObjectNode> execute() {
        BankingManager.getInstance().setTime(timestamp);
        Optional<User> userResult = BankingManager.getInstance().getUserByFeature(email);
        if (userResult.isEmpty()) {
            return Optional.empty();
        }
        User user = userResult.get();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command);
        objectNode.put("timestamp", timestamp);

        ObjectNode outputNode = objectMapper.createObjectNode();

        Optional<Card> cardResult = user.getCardByCardNumber(cardNumber);
        if (cardResult.isEmpty()) {
            outputNode.put("description", "Card not found");
            outputNode.put("timestamp", timestamp);
            objectNode.put("output", outputNode);
            return Optional.of(objectNode);
        }
        Card card = cardResult.get();

        TrackingNode.TrackingNodeBuilder senderTrackingBuilder =
                new TrackingNode.TrackingNodeBuilder()
                        .setTimestamp(timestamp)
                        .setProducer(card.getAccount());


        TransactionTable transaction = new TransactionTable(card, new ZeroPaymentReceiver(), amount,
                card.getAccount().getCurrency());
        try {
            transaction.collect();
//            user.getUserTracker().onTransaction(senderTrackingBuilder
//                    .setAmountLiteral(amount + " " + card.getCurrency())
//                    .setDescription(description)
//                    .setSenderIban(iban)
//                    .setReceiverIban(receiverIban)
//                    .setTransferType("sent")
//                    .setProducer(card)
//                    .build());
//
//            ForexGenie genie = BankingManager.getInstance().getForexGenie();
//            receiverUser.getUserTracker().onTransaction(receiverTrackingBuilder
//                    .setAmountLiteral(
//                            genie.queryRate(card.getCurrency(),
//                                    receiverAccount.getCurrency(), amount) + " "
//                                    + receiverAccount.getCurrency())
//                    .setDescription(description)
//                    .setSenderIban(iban)
//                    .setReceiverIban(receiverIban)
//                    .setTransferType("received")
//                    .setProducer(receiverAccount)
//                    .build());
        } catch (InsufficientFundsException e) {
            senderTrackingBuilder.setDescription(e.getMessage());
            user.getUserTracker().onTransaction(senderTrackingBuilder.build());
        }
        return Optional.empty();
    }
}
