package org.poo.banking.user.account;

/**
 * Should be implemented by entities that handle interest collection.
 */
public interface SavingsCollector {
    /**
     * Collect interest and add it to balance.
     */
    void collect();

    /**
     * Set the interest rate.
     */
    void setInterestRate(double interestRate);
}
