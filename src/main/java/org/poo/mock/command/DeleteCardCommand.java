package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Card;
import org.poo.banking.user.tracking.TrackingNode;

import java.util.Optional;

public class DeleteCardCommand extends BankingCommand {
    private final String cardNumber;
    private final int timestamp;

    public DeleteCardCommand(String command, String cardNumber, int timestamp) {
        super(command);
        this.cardNumber = cardNumber;
        this.timestamp = timestamp;
    }

    @Override
    public Optional<ObjectNode> execute() {
        Optional<User> userResult = BankingManager.getInstance().getUserByFeature(cardNumber);
        if (userResult.isEmpty()) {
            return Optional.empty();
        }
        User user = userResult.get();

        Optional<Card> cardResult = user.removeCardByCardNumber(cardNumber);
        if (cardResult.isEmpty()) {
            return Optional.empty();
        }
        Card card = cardResult.get();
        BankingManager.getInstance().removeFeature(cardNumber);

        user.getFlowTracker().OnCardCreated(new TrackingNode.TrackingNodeBuilder()
                .setAccount(card.getOwner().getIban())
                .setCard(card.getCardNumber())
                .setCardHolder(user.getEmail())
                .setDescription("The card has been destroyed")
                .setTimestamp(timestamp)
                .build());
        return Optional.empty();
    }
}
