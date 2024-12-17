package org.poo.banking.user.tracking;

/**
 * Offers an interface for tracking transaction related commands.
 */
interface TransactionTracker {
    void onTransaction(TrackingNode trackingNode);
}
