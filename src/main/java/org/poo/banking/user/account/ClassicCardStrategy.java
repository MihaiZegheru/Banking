package org.poo.banking.user.account;

public class ClassicCardStrategy extends Card implements PaymentStrategy {
    public ClassicCardStrategy(String cardNumber, String status) {
        super(cardNumber, status);
    }

    @Override
    public void pay(float amount) {

    }
}
