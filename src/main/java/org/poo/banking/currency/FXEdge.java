package org.poo.banking.currency;

import lombok.Data;

@Data
public class FXEdge {
    private final FXNode destination;
    private double cost;

    public FXEdge(final FXNode destination, final double cost) {
        this.destination = destination;
        this.cost = cost;
    }
}
