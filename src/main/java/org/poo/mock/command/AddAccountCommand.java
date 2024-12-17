package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Account;
import org.poo.banking.user.account.ClassicAccount;
import org.poo.banking.user.account.SavingsAccount;
import org.poo.banking.user.tracking.TrackingNode;
import org.poo.utils.Utils;

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
        Account account = null;
        switch (accountType) {
            case "classic" -> {
                account = new ClassicAccount(accountType, Utils.generateIBAN(),
                        currency, user);
                user.addAccountByIban(account);
            }
            case "savings" -> {
                account = new SavingsAccount(accountType, Utils.generateIBAN(),
                        currency, interestRate, user);
                user.addAccountByIban(account);
            }
            default -> {
                return Optional.empty();
            }
        }
        user.getUserTracker().onAccountCreated(new TrackingNode.TrackingNodeBuilder()
                .setDescription("New account created")
                .setTimestamp(timestamp)
                .setProducer(account)
                .build());
        return Optional.empty();
    }
}
