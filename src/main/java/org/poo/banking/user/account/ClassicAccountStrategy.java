package org.poo.banking.user.account;

public class ClassicAccountStrategy extends Account implements PaymentStrategy {
    public ClassicAccountStrategy(String type, String iban, String currency) {
        super(type, iban, currency);
    }

    @Override
    public void pay(float amount) {

    }
}
