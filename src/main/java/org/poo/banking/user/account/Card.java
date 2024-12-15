package org.poo.banking.user.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.poo.banking.transaction.PaymentCollectee;

public abstract class Card implements Owned, Freezable, PaymentCollectee {
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

//    public abstract double pay(Account receiver, double amount, String currency);

    @Override
    public void add() {
        owner.addCard(this);
    }

    @Override
    public void remove() {
        owner.removeCard(this);
    }

    @Override
    public void OnFrozen() {
        status = "frozen";
    }

    @Override
    public String resolveId() {
        return owner.getIban();
    }
}
