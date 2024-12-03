package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Account;
import org.poo.banking.user.account.ClassicAccountStrategy;
import org.poo.banking.user.account.SavingsAccountStrategy;
import org.poo.utils.Utils;

import java.util.Optional;

public class AddAccountCommand extends BankingCommand {
    private final String accountType;
    private final String currency;
    private final String email;
    private final double interestRate;
    private final int timestamp;

    public AddAccountCommand(String command, String accountType, String currency, String email,
                             double interestRate, int timestamp) {
        super(command);
        this.accountType = accountType;
        this.currency = currency;
        this.email = email;
        this.interestRate = interestRate;
        this.timestamp = timestamp;
    }

    @Override
    public Optional<ObjectNode> execute() {
        Optional<User> result = BankingManager.getInstance().getUserByEmail(email);
        if (result.isEmpty()) {
            // TODO: Report issue
            return Optional.empty();
        }
        User user = result.get();
        switch (accountType) {
            case "classic" -> {
                Account account = new ClassicAccountStrategy(accountType, Utils.generateIBAN(),
                        currency);
                user.addAccount(account);
                BankingManager.getInstance().addAccountByIban(account);
            }
            case "savings" -> {
                Account account = new SavingsAccountStrategy(accountType, Utils.generateIBAN(),
                        currency, interestRate);
                user.addAccount(account);
                BankingManager.getInstance().addAccountByIban(account);
            }
        }
        return Optional.empty();
    }
}
