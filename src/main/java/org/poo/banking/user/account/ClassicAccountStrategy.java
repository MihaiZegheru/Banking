package org.poo.banking.user.account;

import org.poo.banking.user.User;

public class ClassicAccountStrategy extends Account implements PaymentStrategy {
    public ClassicAccountStrategy(String type, String iban, String currency, User owner) {
        super(type, iban, currency, owner);
    }

    @Override
    public double pay(double amount, String currency) {
        return amount;
    }
}
