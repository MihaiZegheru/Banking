package org.poo.banking.user.tracking;

import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import lombok.Getter;
import org.poo.banking.user.account.Account;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;

@Getter
public class FlowTracker implements AccountTracker, TransactionTracker {
    protected final List<TrackingNode> history = new ArrayList<>();

    public List<TrackingNode> generateReport(int startTimestamp, int endTimestamp, Account producer) {
        return  history.stream()
                .filter(node -> startTimestamp <= node.getTimestamp()
                        && node.getTimestamp() <= endTimestamp
                        && node.getProducer() == producer)
                .toList();
    }

    public List<TrackingNode> generateSpendingsReport(int startTimestamp, int endTimestamp,
                                                      Account producer) {
        return  history.stream()
                .filter(node -> startTimestamp <= node.getTimestamp()
                        && node.getTimestamp() <= endTimestamp
                        && node.getProducer() == producer
                        && Objects.equals(node.getDescription(), "Card payment"))
                .toList();
    }

    public List<SellerTrackingNode> generateSellersReport(int startTimestamp, int endTimestamp,
                                                          Account producer) {
        Map<String, List<TrackingNode>> data =
                history.stream()
                        .filter(node -> startTimestamp <= node.getTimestamp()
                                && node.getTimestamp() <= endTimestamp
                                && node.getProducer() == producer
                                && Objects.equals(node.getDescription(), "Card payment"))
                        .collect(groupingBy(TrackingNode::getSeller));
        Map<String, Double> sellers = new HashMap<>();
        data.forEach((s, node) ->
                sellers.put(s, node.stream()
                                   .mapToDouble(TrackingNode::getAmount)
                                   .sum()
                )
        );
        List<SellerTrackingNode> sellerNodes = new ArrayList<>();
        sellers.forEach((seller, total) ->
                sellerNodes.add(new SellerTrackingNode(seller, total)
                )
        );
        sellerNodes.sort((s1, s2) -> s1.getCommerciant().compareTo(s2.getCommerciant()));
        return sellerNodes;
    }

    @Override
    public void OnAccountCreated(TrackingNode trackingNode) {
        history.add(trackingNode);
    }

    @Override
    public void OnAccountDeleted(TrackingNode trackingNode) {
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
    public void OnInterestRateChanged(TrackingNode trackingNode) {
        history.add(trackingNode);
    }

    @Override
    public void OnTransaction(TrackingNode trackingNode) {
        history.add(trackingNode);
    }
}
