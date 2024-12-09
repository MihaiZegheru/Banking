package org.poo.banking.user.account;

public interface PaymentStrategy {
    /**
     * Pays and returns the given amount in the provided currency;
     * @param amount
     * @param currency
     * @return
     */
    double pay(Account receiver, double amount, String currency);
}
