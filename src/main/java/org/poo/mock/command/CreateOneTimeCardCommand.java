package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Account;
import org.poo.banking.user.card.Card;
import org.poo.banking.user.card.DisposableCard;
import org.poo.banking.user.tracking.TrackingNode;
import org.poo.utils.Utils;

import java.util.Optional;

public final class CreateOneTimeCardCommand extends BankingCommand {
    private final String iban;
    private final String email;
    private final int timestamp;

    public CreateOneTimeCardCommand(final String command, final String iban, final String email,
                                    final int timestamp) {
        super(command);
        this.iban = iban;
        this.email = email;
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

        Optional<Account> accountResult = user.getAccountByIban(iban);
        if (accountResult.isEmpty()) {
            return Optional.empty();
        }
        Account account = accountResult.get();
        Card card = new DisposableCard(Utils.generateCardNumber(), "active",
                accountResult.get());
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
