package org.poo.banking.user.tracking;

public interface AccountTracker {
    void OnAccountCreated(TrackingNode trackingNode);
    void OnAccountDeleted(TrackingNode trackingNode);
    void OnCardCreated(TrackingNode trackingNode);
    void OnCardDeleted(TrackingNode trackingNode);
    void OnCardFrozen(TrackingNode trackingNode);
    void OnInterestRateChanged(TrackingNode trackingNode);
}
