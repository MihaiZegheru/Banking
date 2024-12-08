package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.account.Account;
import org.poo.banking.user.account.Card;
import org.poo.banking.user.account.ClassicCardStrategy;
import org.poo.utils.Utils;

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
        Optional<Card> result = BankingManager.getInstance().removeCardByCardNumber(cardNumber);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        Card card = result.get();
        Account account = card.getOwner();
        account.removeCard(card);
        return Optional.empty();
    }
}
