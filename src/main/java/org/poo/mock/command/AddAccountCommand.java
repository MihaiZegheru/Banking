package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Account;
import org.poo.banking.user.account.AccountFactory;
import org.poo.banking.user.tracking.TrackingNode;

import java.util.Optional;

public final class AddAccountCommand extends BankingCommand {
    private final String accountType;
    private final String currency;
    private final String email;
    private final double interestRate;
    private final int timestamp;

    public AddAccountCommand(final String command, final String accountType, final String currency,
                             final String email, final double interestRate, final int timestamp) {
        super(command);
        this.accountType = accountType;
        this.currency = currency;
        this.email = email;
        this.interestRate = interestRate;
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
        Account account = AccountFactory.createAccount(accountType, currency, interestRate, user);
        if (account == null) {
            return Optional.empty();
        }
        user.addAccountByIban(account);

        user.getUserTracker().onAccountCreated(new TrackingNode.TrackingNodeBuilder()
                .setDescription("New account created")
                .setTimestamp(timestamp)
                .setProducer(account)
                .build());
        return Optional.empty();
    }
}
