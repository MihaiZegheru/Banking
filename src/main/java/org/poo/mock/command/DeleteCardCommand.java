package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;
import org.poo.banking.user.card.Card;
import org.poo.banking.user.tracking.TrackingNode;

import java.util.Optional;

public final class DeleteCardCommand extends BankingCommand {
    private final String cardNumber;
    private final int timestamp;

    public DeleteCardCommand(final String command, final String cardNumber, final int timestamp) {
        super(command);
        this.cardNumber = cardNumber;
        this.timestamp = timestamp;
    }

    @Override
    public Optional<ObjectNode> execute() {
        BankingManager.getInstance().setTime(timestamp);
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

        user.getUserTracker().onCardDeleted(new TrackingNode.TrackingNodeBuilder()
                .setAccount(card.getOwner().getIban())
                .setCard(card.getCardNumber())
                .setCardHolder(user.getEmail())
                .setDescription("The card has been destroyed")
                .setTimestamp(timestamp)
                .setProducer(card.getOwner())
                .build());
        return Optional.empty();
    }
}
