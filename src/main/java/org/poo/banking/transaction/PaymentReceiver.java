package org.poo.banking.transaction;

/**
 * Should be implemented by entities that can receive payment.
 */
public interface PaymentReceiver {
    /**
     * Receives payment.
     */
    void receive(double amount, String currency);
}
