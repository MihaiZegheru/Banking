package org.poo.mock.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Card;
import org.poo.banking.user.account.exception.InsufficientFundsException;
import org.poo.banking.user.tracking.TrackingNode;

import java.util.Optional;

public class PayOnlineCommand extends BankingCommand {
    private final String cardNumber;
    private final double amount;
    private final String currency;
    private final String description;
    private final String seller;
    private final String email;
    private final int timestamp;

    public PayOnlineCommand(String command, String cardNumber, double amount, String currency,
                            String description, String seller, String email, int timestamp) {
        super(command);
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.seller = seller;
        this.email = email;
        this.timestamp = timestamp;
    }

    @Override
    public Optional<ObjectNode> execute() {
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

        TrackingNode.TrackingNodeBuilder trackingBuilder = new TrackingNode.TrackingNodeBuilder()
                .setTimestamp(timestamp);
        Card card = cardResult.get();
        try {
            double convertedPaidAmount = card.pay(amount, currency);
            trackingBuilder.setAmount(convertedPaidAmount)
                    .setSeller(seller)
                    .setDescription("Card payment");
        } catch (InsufficientFundsException e) {
            trackingBuilder.setDescription(e.getMessage());
        } finally {
            user.getFlowTracker().OnTransaction(trackingBuilder.build());
        }
        return Optional.empty();
    }
}
