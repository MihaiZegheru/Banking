package org.poo.banking.user.account;

import org.poo.banking.BankingManager;
import org.poo.banking.currency.ForexGenie;
import org.poo.banking.user.User;
import org.poo.banking.user.account.exception.InsufficientFundsException;
import org.poo.banking.user.serviceplan.ServicePlan;

public final class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(final String type, final String iban, final String currency,
                          final double interestRate, final User owner) {
        super(type, iban, currency, owner);
        this.interestRate = interestRate;
    }

    @Override
    public double collect() {
        double amount = interestRate * balance;
        balance += amount;
        return amount;
    }

    @Override
    public void setInterestRate(final double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public void ask(final double amount, final String currency) throws InsufficientFundsException {
        ForexGenie forexGenie = BankingManager.getInstance().getForexGenie();
        double newAmount = forexGenie.queryRate(currency, this.currency, amount);
        if (newAmount > balance) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        balance -= newAmount;
        owningUser.getServicePlan().CollectCommission(amount, currency, this);
    }

    @Override
    public void payCommission(double amount, String currency) {
        ForexGenie forexGenie = BankingManager.getInstance().getForexGenie();
        double newAmount = forexGenie.queryRate(currency, this.currency, amount);
        if (newAmount > balance) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        balance -= newAmount;
    }

    @Override
    public void giveBack(final double amount, final String currency) {
        ForexGenie forexGenie = BankingManager.getInstance().getForexGenie();
        double newAmount = forexGenie.queryRate(currency, this.currency, amount);
        balance += amount;
    }

    @Override
    public void receive(final double amount, final String currency) {
        ForexGenie forexGenie = BankingManager.getInstance().getForexGenie();
        double newAmount = forexGenie.queryRate(currency, this.currency, amount);
        balance += newAmount;
    }
}
