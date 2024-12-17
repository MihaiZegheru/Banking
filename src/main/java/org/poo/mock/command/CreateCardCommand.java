package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Account;
import org.poo.banking.user.card.Card;
import org.poo.banking.user.card.ClassicCard;
import org.poo.banking.user.tracking.TrackingNode;
import org.poo.utils.Utils;

import java.util.Optional;

public final class CreateCardCommand extends BankingCommand {
    private final String iban;
    private final String email;
    private final int timestamp;

    public CreateCardCommand(final String command, final String iban, final String email,
                             final int timestamp) {
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
            return Optional.empty();
        }
        Account account = accountResult.get();
        Card card = new ClassicCard(Utils.generateCardNumber(), "active", account);
        user.addCardByCardNumber(card);

        user.getUserTracker().onCardCreated(new TrackingNode.TrackingNodeBuilder()
                .setAccount(iban)
                .setCard(card.getCardNumber())
                .setCardHolder(email)
                .setDescription("New card created")
                .setTimestamp(timestamp)
                .setProducer(account)
                .build());
        return Optional.empty();
    }
}
