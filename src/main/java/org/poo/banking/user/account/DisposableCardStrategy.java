package org.poo.banking.user.account;

public class DisposableCardStrategy extends Card implements PaymentStrategy {
    public DisposableCardStrategy(String cardNumber, String status, Account owner) {
        super(cardNumber, status, owner);
    }

    @Override
    public void pay(double amount) {
        owner.balance -= amount;
        // TODO: Generate another card.
    }
}
