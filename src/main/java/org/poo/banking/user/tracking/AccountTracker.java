package org.poo.banking.user.tracking;

public interface AccountTracker {
    void OnAccountCreated(TrackingNode trackingNode);
    void OnCardCreated(TrackingNode trackingNode);
    void OnCardDeleted(TrackingNode trackingNode);
}
