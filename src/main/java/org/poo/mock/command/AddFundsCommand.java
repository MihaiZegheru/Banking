package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.account.Account;

import java.util.Optional;

public class AddFundsCommand extends BankingCommand {
    private final double amount;
    private final String iban;
    private final int timestamp;

    public AddFundsCommand(String command, double amount, String iban, int timestamp) {
        super(command);
        this.amount = amount;
        this.iban = iban;
        this.timestamp = timestamp;
    }

    @Override
    public Optional<ObjectNode> execute() {
        Optional<Account> result = BankingManager.getInstance().getAccountByIban(iban);
        if (result.isEmpty()) {
            // TODO: Report issue
            return Optional.empty();
        }
        Account account = result.get();
        account.addFunds(amount);
        return Optional.empty();
    }
}
