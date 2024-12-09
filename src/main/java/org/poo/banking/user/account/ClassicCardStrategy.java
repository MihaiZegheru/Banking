package org.poo.banking.user.account;

import org.poo.banking.BankingManager;
import org.poo.banking.user.account.exception.InsufficientFundsException;

public class ClassicCardStrategy extends Card implements PaymentStrategy {
    public ClassicCardStrategy(String cardNumber, String status, Account owner) {
        super(cardNumber, status, owner);
    }

    @Override
    public double pay(double amount, String currency) throws InsufficientFundsException {
        amount = BankingManager.getInstance().getForexGenie().queryRate(currency, owner.currency,
                amount);
        if (owner.balance < amount) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        owner.balance -= amount;
        return amount;
    }
}
