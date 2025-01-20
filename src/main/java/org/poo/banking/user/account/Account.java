package org.poo.banking.user.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.poo.banking.transaction.PaymentCollectee;
import org.poo.banking.transaction.PaymentReceiver;
import org.poo.banking.user.User;
import org.poo.banking.user.card.Card;
import org.poo.banking.user.serviceplan.ServicePlan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Account implements SavingsCollector, PaymentCollectee,
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

    @Getter
    @JsonIgnore
    protected final User owningUser;

    @Getter
    @Setter
    @JsonIgnore
    protected ServicePlan servicePlan;

    @Getter
    @Setter
    @JsonIgnore
    protected double spending;

    protected Account(final String type, final String iban, final String currency,
                      final User owner, final ServicePlan servicePlan) {
        this.type = type;
        this.iban = iban;
        this.currency = currency;
        this.balance = 0;
        this.owningUser = owner;
        this.servicePlan = servicePlan;
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
    public final String resolveId() {
        return iban;
    }

    @Override
    public Account getAccount() {
        return this;
    }
}
