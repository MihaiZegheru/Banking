package org.poo.banking.user.account;

public class SavingsAccountStrategy extends Account implements SavingStrategy {
    private double interestRate;

    public SavingsAccountStrategy(String type, String iban, String currency,
                                  double interestRate) {
        super(type, iban, currency);
        this.interestRate = interestRate;
    }

    @Override
    public void collect() {

    }
}
