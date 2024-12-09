package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Account;

import java.util.Optional;

public class AddCurrencyCommand extends BankingCommand {
    private final String fromCurrency;
    private final String toCurrency;
    private final double rate;

    public AddCurrencyCommand(String command, String fromCurrency, String toCurrency, double rate) {
        super(command);
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.rate = rate;
    }


    @Override
    public Optional<ObjectNode> execute() {
        BankingManager.getInstance().getForexGenie().addCurrency(fromCurrency, toCurrency, rate);
        return Optional.empty();
    }
}
