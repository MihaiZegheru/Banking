package org.poo.banking.transaction;

/**
 * Should be implemented by entities that can be queried for payment.
 */
public interface PaymentCollectee {
    /**
     * Asks for amount from callee.
     */
    void ask(double amount, String currency);

    /**
     * Returns asked amount to callee.
     */
    void giveBack(double amount, String currency);

    /**
     * Returns some personal identification of implementation choice.
     */
    String resolveId();
}
