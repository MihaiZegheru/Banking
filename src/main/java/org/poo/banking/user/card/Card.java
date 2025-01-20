package org.poo.banking.user.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.poo.banking.transaction.PaymentCollectee;
import org.poo.banking.user.account.Account;

@Getter
public abstract class Card implements Freezable, PaymentCollectee {
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
    public final void freeze() {
        status = "frozen";
    }

    @Override
    public final String resolveId() {
        return owner.getIban();
    }
}
