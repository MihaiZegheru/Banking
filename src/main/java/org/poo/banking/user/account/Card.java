package org.poo.banking.user.account;

import lombok.Getter;

public abstract class Card {
    @Getter
    protected final String cardNumber;
    @Getter
    protected String status;

    protected Card(String cardNumber, String status) {
        this.cardNumber = cardNumber;
        this.status = status;
    }
}
