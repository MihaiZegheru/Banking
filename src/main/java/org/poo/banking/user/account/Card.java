package org.poo.banking.user.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

public abstract class Card implements Owned {
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

    public abstract void pay(double amount, String currency);

    @Override
    public void add() {
        owner.addCard(this);
    }

    @Override
    public void remove() {
        owner.removeCard(this);
    }
}
