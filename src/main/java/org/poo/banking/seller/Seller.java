package org.poo.banking.seller;

import lombok.Getter;

public class Seller {
    @Getter
    private final String name;
    @Getter
    private final int id;
    @Getter
    private final String iban;
    @Getter
    private final String type;
    @Getter
    private final String cashbackStrategy;

    public Seller(final String name, final int id, final String iban, final String type,
                  final String cashbackStrategy) {
        this.name = name;
        this.id = id;
        this.iban = iban;
        this.type = type;
        this.cashbackStrategy = cashbackStrategy;
    }
}
