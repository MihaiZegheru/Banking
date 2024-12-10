package org.poo.banking.user.account;

import org.poo.banking.BankingManager;
import org.poo.banking.currency.ForexGenie;
import org.poo.banking.user.User;
import org.poo.banking.user.account.exception.InsufficientFundsException;

public class ClassicAccountStrategy extends Account {
    public ClassicAccountStrategy(String type, String iban, String currency, User owner) {
        super(type, iban, currency, owner);
    }

    @Override
    public void ask(double amount, String currency) throws InsufficientFundsException {
        ForexGenie forexGenie = BankingManager.getInstance().getForexGenie();
        amount = forexGenie.queryRate(currency, this.currency, amount);
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        balance -= amount;
    }

    @Override
    public void receive(double amount, String currency) {
        ForexGenie forexGenie = BankingManager.getInstance().getForexGenie();
        amount = forexGenie.queryRate(currency, this.currency, amount);
        balance += amount;
    }

    @Override
    public void collect() {

    }
}
