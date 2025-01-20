package org.poo.banking.user.tracking;

/**
 * Offers an interface for tracking account related commands.
 */
interface AccountTracker {
    void onAccountCreated(TrackingNode trackingNode);
    void onAccountDeleted(TrackingNode trackingNode);
    void onCardCreated(TrackingNode trackingNode);
    void onCardDeleted(TrackingNode trackingNode);
    void onCardFrozen(TrackingNode trackingNode);
    void onInterestRateChanged(TrackingNode trackingNode);
    void onPlanUpgraded(TrackingNode trackingNode);
}
