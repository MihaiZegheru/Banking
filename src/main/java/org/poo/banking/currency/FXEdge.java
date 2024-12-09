package org.poo.banking.currency;

import lombok.Data;

@Data
public class FXEdge {
    private final FXNode destination;
    private double cost;

    public FXEdge(FXNode destination, double cost) {
        this.destination = destination;
        this.cost = cost;
    }
}
