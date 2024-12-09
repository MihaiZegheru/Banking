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
    public double pay(Account receiver, double amount, String currency) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        ForexGenie forexGenie = BankingManager.getInstance().getForexGenie();
        balance -= amount;
        amount = forexGenie.queryRate(currency, receiver.currency, amount);
        receiver.addFunds(amount);
        return amount;
    }

    @Override
    public void collect() {

    }
}
