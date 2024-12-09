package org.poo.banking.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.poo.banking.BankingManager;
import org.poo.banking.exception.ClientAlreadyExists;
import org.poo.banking.user.account.Account;
import org.poo.banking.user.account.Card;
import org.poo.banking.user.account.exception.BalanceNotZeroException;
import org.poo.banking.user.tracking.FlowTracker;

import java.util.*;

public class User {
    @Getter
    private String firstName;
    @Getter
    private String lastName;
    @Getter
    private String email;

    // Users should not have many accounts so it is reasonable to iterate through all when
    // performing operations.

    @JsonProperty("accounts")
    protected final Map<String, Account> ibanToAccount = new LinkedHashMap<>();
    protected final Map<String, Card> cardNumberToCard = new LinkedHashMap<>();
    @Getter
    @JsonIgnore
    protected final Map<String, String> aliases = new HashMap<>();
    @Getter
    @JsonIgnore
    protected final FlowTracker flowTracker = new FlowTracker();


    public User(String firstName, String lastName, String emailAddr) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = emailAddr;
    }

    public Collection<Account> getAccounts() {
        return ibanToAccount.values();
    }

    public void addAccountByIban(Account account) {
        if (ibanToAccount.containsKey(account.getIban())) {
            // TODO: Change exception.
            throw new ClientAlreadyExists();
        }
        ibanToAccount.put(account.getIban(), account);
        BankingManager.getInstance().addUserByFeature(account.getIban(), this);
    }

    /**
     * Removes account and associated cards.
     * @param iban
     */
    public Optional<Account> removeAccountByIban(String iban) throws BalanceNotZeroException {
        if (!ibanToAccount.containsKey(iban)) {
            return Optional.empty();
        }
        Account account = ibanToAccount.get(iban);
        account.remove();
        return Optional.ofNullable(ibanToAccount.remove(iban));
    }

    public Optional<Account> getAccountByIban(String iban) {
        if (!ibanToAccount.containsKey(iban)) {
            return Optional.empty();
        }
        return Optional.ofNullable(ibanToAccount.get(iban));
    }

    public void addCardByCardNumber(Card card) {
        if (cardNumberToCard.containsKey(card.getCardNumber())) {
            return;
        }
        cardNumberToCard.put(card.getCardNumber(), card);
        card.add();
        BankingManager.getInstance().addUserByFeature(card.getCardNumber(), this);
    }

    public Optional<Card> removeCardByCardNumber(String cardNumber) {
        if (!cardNumberToCard.containsKey(cardNumber)) {
            return Optional.empty();
        }
        Card card = cardNumberToCard.get(cardNumber);
        card.remove();
        return Optional.ofNullable(cardNumberToCard.remove(cardNumber));
    }

    public Optional<Card> getCardByCardNumber(String cardNumber) {
        if (!cardNumberToCard.containsKey(cardNumber)) {
            return Optional.empty();
        }
        return Optional.ofNullable(cardNumberToCard.get(cardNumber));
    }

    public void addAlias(String alias, String iban) {
        aliases.put(alias, iban);
    }

    public Optional<String> removeAlias(String alias) {
        return Optional.ofNullable(aliases.remove(alias));
    }

    public String getAlias(String alias) {
        return aliases.get(alias);
    }
}
