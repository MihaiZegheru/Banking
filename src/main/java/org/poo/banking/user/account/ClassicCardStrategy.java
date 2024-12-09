package org.poo.banking.user.account;

import org.poo.banking.BankingManager;

public class ClassicCardStrategy extends Card implements PaymentStrategy {
    public ClassicCardStrategy(String cardNumber, String status, Account owner) {
        super(cardNumber, status, owner);
    }

    @Override
    public void pay(double amount, String currency) {
        amount = BankingManager.getInstance().getForexGenie().queryRate(currency, owner.currency,
                amount);
        if (owner.balance < amount) {
            return;
        }
        owner.balance -= amount;
    }
}
