package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Account;

import java.util.Optional;

public final class SetMinimumBalanceCommand extends BankingCommand {
    private final double amount;
    private final String iban;
    private final int timestamp;

    public SetMinimumBalanceCommand(final String command, final double amount, final String iban,
                                    final int timestamp) {
        super(command);
        this.amount = amount;
        this.iban = iban;
        this.timestamp = timestamp;
    }

    @Override
    public Optional<ObjectNode> execute() {
        BankingManager.getInstance().setTime(timestamp);
        Optional<User> userResult = BankingManager.getInstance().getUserByFeature(iban);
        if (userResult.isEmpty()) {
            return Optional.empty();
        }
        User user = userResult.get();

        Optional<Account> accountResult = user.getAccountByIban(iban);
        if (accountResult.isEmpty()) {
            return Optional.empty();
        }
        Account account = accountResult.get();
        account.setMinBalance(amount);
        return Optional.empty();
    }
}
