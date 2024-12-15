package org.poo.banking.user.account;

import org.poo.banking.BankingManager;
import org.poo.banking.currency.ForexGenie;
import org.poo.banking.user.User;
import org.poo.banking.user.account.exception.InsufficientFundsException;

public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String type, String iban, String currency,
                          double interestRate, User owner) {
        super(type, iban, currency, owner);
        this.interestRate = interestRate;
    }

    @Override
    public void collect() {
        balance += interestRate * balance;
    }

    @Override
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
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
    public void giveBack(double amount, String currency) {
        ForexGenie forexGenie = BankingManager.getInstance().getForexGenie();
        amount = forexGenie.queryRate(currency, this.currency, amount);
        balance += amount;
    }

    @Override
    public void receive(double amount, String currency) {
        ForexGenie forexGenie = BankingManager.getInstance().getForexGenie();
        amount = forexGenie.queryRate(currency, this.currency, amount);
        balance += amount;
    }
}
