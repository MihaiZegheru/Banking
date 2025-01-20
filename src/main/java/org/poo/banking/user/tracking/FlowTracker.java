package org.poo.banking.user.tracking;

import lombok.Getter;
import org.poo.banking.user.account.Account;


import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.groupingBy;

@Getter
public final class FlowTracker implements AccountTracker, TransactionTracker {
    private final List<TrackingNode> history = new ArrayList<>();

    /**
     * Generates an overall report of the producer.
     * @param startTimestamp starting time frame
     * @param endTimestamp ending time frame
     * @param producer tracked account
     * @return List<TrackingNode>
     */
    public List<TrackingNode> generateReport(final int startTimestamp, final int endTimestamp,
                                             final Account producer) {
        return  history.stream()
                .filter(node -> startTimestamp <= node.getTimestamp()
                        && node.getTimestamp() <= endTimestamp
                        && node.getProducer() == producer)
                .toList();
    }

    /**
     * Generates a spendings only report of the producer.
     * @param startTimestamp starting time frame
     * @param endTimestamp ending time frame
     * @param producer tracked account
     * @return List<TrackingNode>
     */
    public List<TrackingNode> generateSpendingsReport(final int startTimestamp,
                                                      final int endTimestamp,
                                                      final Account producer) {
        return  history.stream()
                .filter(node -> startTimestamp <= node.getTimestamp()
                        && node.getTimestamp() <= endTimestamp
                        && node.getProducer() == producer
                        && Objects.equals(node.getDescription(), "Card payment"))
                .toList();
    }

    /**
     * Generates a report with the interactions with sellers of the producer.
     * @param startTimestamp starting time frame
     * @param endTimestamp ending time frame
     * @param producer tracked account
     * @return List<TrackingNode>
     */
    public List<SellerTrackingNode> generateSellersReport(final int startTimestamp,
                                                          final int endTimestamp,
                                                          final Account producer) {
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
    public void onAccountCreated(final TrackingNode trackingNode) {
        history.add(trackingNode);
    }

    @Override
    public void onAccountDeleted(final TrackingNode trackingNode) {
        history.add(trackingNode);
    }

    @Override
    public void onCardCreated(final TrackingNode trackingNode) {
        history.add(trackingNode);
    }

    @Override
    public void onCardDeleted(final TrackingNode trackingNode) {
        history.add(trackingNode);
    }

    @Override
    public void onCardFrozen(final TrackingNode trackingNode) {
        history.add(trackingNode);
    }

    @Override
    public void onInterestRateChanged(final TrackingNode trackingNode) {
        history.add(trackingNode);
    }

    @Override
    public void onPlanUpgraded(TrackingNode trackingNode) {
        history.add(trackingNode);
    }

    @Override
    public void onTransaction(final TrackingNode trackingNode) {
        history.add(trackingNode);
    }

    @Override
    public void onCashWithdrawal(final TrackingNode trackingNode) {
        history.add(trackingNode);
    }
}
