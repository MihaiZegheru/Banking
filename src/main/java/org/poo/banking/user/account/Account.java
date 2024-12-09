package org.poo.banking.user.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.poo.banking.BankingManager;
import org.poo.banking.currency.ForexGenie;
import org.poo.banking.user.User;

import java.util.*;

public abstract class Account implements Owned {
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

    User owner;

    protected Account(String type, String iban, String currency, User owner) {
        this.type = type;
        this.iban = iban;
        this.currency = currency;
        this.balance = 0;
        this.owner = owner;
    }

    // Q: Can saving accounts transfer money?
    public void transfer(Account receiver, double amount) {
        if (amount > balance) {
            // TODO: report issue.
            return;
        }
        ForexGenie forexGenie = BankingManager.getInstance().getForexGenie();
        receiver.addFunds(forexGenie.queryRate(currency, receiver.currency, amount));
        balance -= amount;
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
    public void remove() {
        cards.clear();
    }

}
