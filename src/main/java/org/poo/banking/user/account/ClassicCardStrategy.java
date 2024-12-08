package org.poo.banking.user.account;

public class ClassicCardStrategy extends Card implements PaymentStrategy {
    public ClassicCardStrategy(String cardNumber, String status, Account owner) {
        super(cardNumber, status, owner);
    }

    @Override
    public void pay(float amount) {

    }
}
