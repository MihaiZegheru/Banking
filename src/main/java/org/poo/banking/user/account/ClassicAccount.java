package org.poo.banking.user.account;

import org.poo.banking.BankingManager;
import org.poo.banking.currency.ForexGenie;
import org.poo.banking.user.User;
import org.poo.banking.user.account.exception.AccountIsNotSavingsAccount;
import org.poo.banking.user.account.exception.InsufficientFundsException;

public final class ClassicAccount extends Account {
    public ClassicAccount(final String type, final String iban, final String currency,
                          final User owner) {
        super(type, iban, currency, owner);
    }

    @Override
    public void ask(final double amount, final String currency) throws InsufficientFundsException {
        ForexGenie forexGenie = BankingManager.getInstance().getForexGenie();
        double newAmount = forexGenie.queryRate(currency, this.currency, amount);
        if (newAmount > balance) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        balance -= newAmount;
        owningUser.getServicePlan().collectCommission(amount, currency, this);
    }

    @Override
    public void payCommission(final double amount, final String currency) {
        ForexGenie forexGenie = BankingManager.getInstance().getForexGenie();
        double newAmount = forexGenie.queryRate(currency, this.currency, amount);
        if (newAmount > balance) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        balance -= newAmount;
    }

    @Override
    public void receive(final double amount, final String currency) {
        ForexGenie forexGenie = BankingManager.getInstance().getForexGenie();
        double newAmount = forexGenie.queryRate(currency, this.currency, amount);
        balance += newAmount;
    }

    @Override
    public double collect() throws AccountIsNotSavingsAccount {
        throw new AccountIsNotSavingsAccount("This is not a savings account");
    }

    @Override
    public void setInterestRate(final double interestRate) throws AccountIsNotSavingsAccount {
        throw new AccountIsNotSavingsAccount("This is not a savings account");
    }

    @Override
    public void giveBack(final double amount, final String currency) {
        ForexGenie forexGenie = BankingManager.getInstance().getForexGenie();
        double newAmount = forexGenie.queryRate(currency, this.currency, amount);
        balance += newAmount;
    }
}
