package org.poo.banking.user.tracking;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class FlowTracker implements AccountTracker, TransactionTracker {
    @Getter
    protected final List<TrackingNode> history = new ArrayList<>();

    @Override
    public void OnAccountCreated(TrackingNode trackingNode) {
        history.add(trackingNode);
    }

    @Override
    public void OnTransaction(TrackingNode trackingNode) {
        history.add(trackingNode);
    }
}
