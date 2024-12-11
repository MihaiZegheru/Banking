package org.poo.banking.user.tracking;

import lombok.Getter;
import org.poo.banking.user.account.Account;

import java.util.ArrayList;
import java.util.List;

public class FlowTracker implements AccountTracker, TransactionTracker {
    @Getter
    protected final List<TrackingNode> history = new ArrayList<>();

    public List<TrackingNode> generateReport(int startTimestamp, int endTimestamp, Account producer) {
        return  history.stream().filter(node -> startTimestamp <= node.getTimestamp()
                && node.getTimestamp() <= endTimestamp && node.getProducer() == producer).toList();
    }

    @Override
    public void OnAccountCreated(TrackingNode trackingNode) {
        history.add(trackingNode);
    }

    @Override
    public void OnCardCreated(TrackingNode trackingNode) {
        history.add(trackingNode);
    }

    @Override
    public void OnCardDeleted(TrackingNode trackingNode) {
        history.add(trackingNode);
    }

    @Override
    public void OnCardFrozen(TrackingNode trackingNode) {
        history.add(trackingNode);
    }

    @Override
    public void OnTransaction(TrackingNode trackingNode) {
        history.add(trackingNode);
    }
}
