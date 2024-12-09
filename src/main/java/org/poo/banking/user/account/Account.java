package org.poo.banking.user.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.poo.banking.user.User;
import org.poo.banking.user.account.exception.BalanceNotZeroException;

import java.util.*;

public abstract class Account implements PaymentStrategy, SavingStrategy, Owned, Freezable {
    @Getter
    protected final String type;
    @Getter
    @JsonProperty("IBAN")
    protected final String iban;
    @Getter
    protected String currency;
    @Getter
    protected double balance;
    @Getter
    List<Card> cards = new ArrayList<>();

    @Setter
    @Getter
    @JsonIgnore
    protected double minBalance;

    @Setter
    protected boolean isFrozen = false;

    @Getter
    @JsonIgnore
    protected final User owner;

    protected Account(String type, String iban, String currency, User owner) {
        this.type = type;
        this.iban = iban;
        this.currency = currency;
        this.balance = 0;
        this.owner = owner;
    }

    public void addFunds(double amount) {
        balance += amount;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    @Override
    public void add() {

    }

    @Override
    public void remove() throws BalanceNotZeroException {
        if (balance > 0) {
            throw new BalanceNotZeroException("Account couldn't be deleted - see org.poo.transactions for details");
        }
        cards.clear();
    }

    @Override
    public void OnFrozen() {
        isFrozen = true;
        for (Card card : cards) {
            card.status = "frozen";
        }
    }

    public abstract double pay(Account receiver, double amount, String currency);
}
