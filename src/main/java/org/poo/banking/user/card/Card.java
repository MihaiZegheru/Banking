package org.poo.banking.user.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.poo.banking.transaction.PaymentCollectee;
import org.poo.banking.user.account.Account;
import org.poo.banking.user.account.Addable;

@Getter
public abstract class Card implements Addable, Freezable, PaymentCollectee {
    protected final String cardNumber;
    @Setter
    protected String status;
    @JsonIgnore
    protected final Account owner;

    protected Card(final String cardNumber, final String status, final Account owner) {
        this.cardNumber = cardNumber;
        this.status = status;
        this.owner = owner;
    }

    @Override
    public final void add() {
        owner.addCard(this);
    }

    @Override
    public final void remove() {
        owner.removeCard(this);
    }

    @Override
    public final void onFrozen() {
        status = "frozen";
    }

    @Override
    public final String resolveId() {
        return owner.getIban();
    }
}
