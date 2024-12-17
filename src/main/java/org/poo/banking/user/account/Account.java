package org.poo.banking.user.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.poo.banking.transaction.PaymentCollectee;
import org.poo.banking.transaction.PaymentReceiver;
import org.poo.banking.user.User;
import org.poo.banking.user.account.exception.BalanceNotZeroException;
import org.poo.banking.user.card.Card;
import org.poo.banking.user.card.Freezable;

import java.util.ArrayList;
import java.util.List;

public abstract class Account implements SavingsCollector, Addable, Freezable, PaymentCollectee,
        PaymentReceiver {
    @Getter
    protected final String type;

    @Getter
    @JsonProperty("IBAN")
    protected final String iban;

    @Getter
    protected String currency;

    @Getter
    @Setter
    protected double balance;

    @Getter
    protected final List<Card> cards = new ArrayList<>();

    @Setter
    @Getter
    @JsonIgnore
    protected double minBalance;

    @Setter
    protected boolean isFrozen = false;

    @Getter
    @JsonIgnore
    protected final User owner;

    protected Account(final String type, final String iban, final String currency,
                      final User owner) {
        this.type = type;
        this.iban = iban;
        this.currency = currency;
        this.balance = 0;
        this.owner = owner;
    }

    /**
     * Adds a card to the account.
     */
    public final void addCard(final Card card) {
        cards.add(card);
    }

    /**
     * Removes a card from the account.
     */
    public final void removeCard(final Card card) {
        cards.remove(card);
    }

    @Override
    public final void remove() throws BalanceNotZeroException {
        if (balance > 0) {
            throw new BalanceNotZeroException("Account couldn't be deleted - "
                    + "see org.poo.transactions for details");
        }
        cards.clear();
    }

    @Override
    public final void onFrozen() {
        isFrozen = true;
        for (Card card : cards) {
            card.setStatus("frozen");
        }
    }

    @Override
    public final String resolveId() {
        return iban;
    }

    @Override
    public final void add() {

    }
}
