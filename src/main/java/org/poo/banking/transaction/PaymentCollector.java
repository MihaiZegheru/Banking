package org.poo.banking.transaction;

/**
 * Should be implemented by entities that handle payment collection and redistribution.
 */
public interface PaymentCollector {
    /**
     * Collect payment from PaymentCollectees and give it to PaymentReceivers.
     */
    void collect();
}
