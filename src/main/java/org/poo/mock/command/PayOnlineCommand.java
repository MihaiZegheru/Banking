package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Card;

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
        Optional<User> userResult = BankingManager.getInstance().getUserByFeature(email);
        if (userResult.isEmpty()) {
            return Optional.empty();
        }
        User user = userResult.get();

        Optional<Card> cardResult = user.getCardByCardNumber(cardNumber);
        if (cardResult.isEmpty()) {
            return Optional.empty();
        }
        Card card = cardResult.get();;
        card.pay(amount);
        return Optional.empty();
    }
}
