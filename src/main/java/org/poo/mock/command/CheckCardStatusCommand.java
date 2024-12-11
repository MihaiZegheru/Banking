package org.poo.mock.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Card;
import org.poo.banking.user.tracking.TrackingNode;

import java.util.Optional;

public class CheckCardStatusCommand extends BankingCommand {
    private final String cardNumber;
    private final int timestamp;

    public CheckCardStatusCommand(String command, String cardNumber, int timestamp) {
        super(command);
        this.cardNumber = cardNumber;
        this.timestamp = timestamp;
    }

    @Override
    public Optional<ObjectNode> execute() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command);
        objectNode.put("timestamp", timestamp);

        ObjectNode outputNode = objectMapper.createObjectNode();
        outputNode.put("timestamp", timestamp);

        Optional<User> userResult = BankingManager.getInstance().getUserByFeature(cardNumber);
        if (userResult.isEmpty()) {
            outputNode.put("description", "Card not found");
            objectNode.put("output", outputNode);
            return Optional.of(objectNode);
        }
        User user = userResult.get();
        Optional<Card> cardResult = user.getCardByCardNumber(cardNumber);
        if (cardResult.isEmpty()) {
            outputNode.put("description", "Card not found");
            objectNode.put("output", outputNode);
            return Optional.of(objectNode);
        }
        Card card = cardResult.get();

        if (card.getOwner().getBalance() <= card.getOwner().getMinBalance()) {
            card.OnFrozen();
            user.getUserTracker().OnCardFrozen(new TrackingNode.TrackingNodeBuilder()
                    .setDescription("You have reached the minimum amount of funds, the card will be frozen")
                    .setTimestamp(timestamp)
                    .build());
        }

        return Optional.empty();
    }
}
