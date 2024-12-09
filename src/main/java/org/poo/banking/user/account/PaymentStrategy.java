package org.poo.banking.user.account;

public interface PaymentStrategy {
    /**
     * Pays and returns the given amount in the provided currency;
     * @param amount
     * @param currency
     * @return
     */
    public double pay(double amount, String currency);
}
