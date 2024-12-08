package org.poo.banking.user.account;

import org.poo.banking.user.User;

public class SavingsAccountStrategy extends Account implements SavingStrategy {
    private double interestRate;

    public SavingsAccountStrategy(String type, String iban, String currency,
                                  double interestRate, User owner) {
        super(type, iban, currency, owner);
        this.interestRate = interestRate;
    }

    @Override
    public void collect() {

    }
}
