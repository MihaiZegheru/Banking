package org.poo.banking.user.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

public abstract class Card {
    @Getter
    protected final String cardNumber;
    @Getter
    protected String status;
    @Getter
    @JsonIgnore
    protected final Account owner;

    protected Card(String cardNumber, String status, Account owner) {
        this.cardNumber = cardNumber;
        this.status = status;
        this.owner = owner;
    }
}
