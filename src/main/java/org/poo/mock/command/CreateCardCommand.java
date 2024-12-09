package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Account;
import org.poo.banking.user.account.Card;
import org.poo.banking.user.account.ClassicCardStrategy;
import org.poo.banking.user.tracking.TrackingNode;
import org.poo.utils.Utils;

import java.util.Optional;

public class CreateCardCommand extends BankingCommand {
    private final String iban;
    private final String email;
    private final int timestamp;

    public CreateCardCommand(String command, String iban, String email, int timestamp) {
        super(command);
        this.iban = iban;
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

        Optional<Account> accountResult = user.getAccountByIban(iban);
        if (accountResult.isEmpty()) {
            // TODO: Report issue
            return Optional.empty();
        }
        Card card = new ClassicCardStrategy(Utils.generateCardNumber(), "active",
                accountResult.get());
        user.addCardByCardNumber(card);

        user.getFlowTracker().OnCardCreated(new TrackingNode.TrackingNodeBuilder()
                .setAccount(iban)
                .setCard(card.getCardNumber())
                .setCardHolder(email)
                .setDescription("New card created")
                .setTimestamp(timestamp)
                .build());
        return Optional.empty();
    }
}
