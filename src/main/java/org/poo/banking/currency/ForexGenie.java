package org.poo.banking.currency;

import org.poo.utils.IDGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;

public final class ForexGenie {
    private final Map<FXNode, List<FXEdge>> fxGraph = new HashMap<>();
    private final Map<String, FXNode> currencies = new HashMap<>();
    private final IDGenerator idGenerator = new IDGenerator();

    /**
     * Adds a currency exchange.
     */
    public void addCurrency(final String from, final String to, final double rate) {
        if (!currencies.containsKey(from)) {
            currencies.put(from, new FXNode(from, idGenerator.next()));
            fxGraph.put(currencies.get(from), new ArrayList<>());
        }
        if (!currencies.containsKey(to)) {
            currencies.put(to, new FXNode(to, idGenerator.next()));
            fxGraph.put(currencies.get(to), new ArrayList<>());
        }
        fxGraph.get(currencies.get(from)).add(new FXEdge(currencies.get(to), rate));

        fxGraph.get(currencies.get(to)).add(new FXEdge(currencies.get(from), 1 / rate));
    }

    /**
     * Converts given amount into another currency.
     * @param from initial currency
     * @param to final currency
     * @param amount to be converted
     * @return converted amount
     */
    public double queryRate(final String from, final String to, final double amount) {
        if (from.equals(to)) {
            return amount;
        }
        return computeBellmanFord(currencies.get(from), currencies.get(to), amount);
    }

    private double computeBellmanFord(final FXNode from, final FXNode to, final double amount) {
        Queue<FXEdge> queue = new LinkedList<>();
        Map<FXNode, Double> distances = new HashMap<>();
        Map<FXNode, Boolean> visited = new HashMap<>();

        for (FXNode node : currencies.values()) {
            distances.put(node, Double.MAX_VALUE);
            visited.put(node, false);
        }
        distances.put(from, amount);
        FXEdge startingEdge = new FXEdge(from, amount);
        queue.add(startingEdge);

        while (!queue.isEmpty()) {
            FXEdge currEdge = queue.poll();
            if (visited.get(currEdge.getDestination())) {
                continue;
            }
            visited.put(currEdge.getDestination(), true);

            for (FXEdge edge: fxGraph.get(currEdge.getDestination())) {
                if (edge.getCost() * distances.get(currEdge.getDestination())
                        >= distances.get(edge.getDestination())) {
                    continue;
                }
                distances.put(edge.getDestination(), edge.getCost()
                        * distances.get(currEdge.getDestination()));
                queue.add(edge);
            }
        }
        return distances.get(to);
    }
}
