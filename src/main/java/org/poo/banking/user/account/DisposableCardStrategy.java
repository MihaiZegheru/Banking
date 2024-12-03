package org.poo.banking.user.account;

public class DisposableCardStrategy extends Card implements PaymentStrategy {
    public DisposableCardStrategy(String cardNumber, String status) {
        super(cardNumber, status);
    }

    @Override
    public void pay(float amount) {

    }
}
