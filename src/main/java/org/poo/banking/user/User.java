package org.poo.banking.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.poo.banking.BankingManager;
import org.poo.banking.exception.ClientAlreadyExists;
import org.poo.banking.user.account.Account;
import org.poo.banking.user.card.Card;
import org.poo.banking.user.account.exception.BalanceNotZeroException;
import org.poo.banking.user.serviceplan.ServicePlan;
import org.poo.banking.user.tracking.FlowTracker;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public final class User {
    @Getter
    private final String firstName;
    @Getter
    private final String lastName;
    @Getter
    private final String email;
    @JsonIgnore
    @Getter
    private final String birthDate;
    @JsonIgnore
    @Getter
    private final String occupation;

    @JsonProperty("accounts")
    private final Map<String, Account> ibanToAccount = new LinkedHashMap<>();
    private final Map<String, Card> cardNumberToCard = new LinkedHashMap<>();

    @Getter
    @JsonIgnore
    private final Map<String, String> aliases = new HashMap<>();

    @Getter
    @JsonIgnore
    private final FlowTracker userTracker = new FlowTracker();

    @Getter
    @Setter
    @JsonIgnore
    protected ServicePlan servicePlan;

    public User(final String firstName, final String lastName, final String emailAddr,
                final String birthData, final String occupation, final ServicePlan servicePlan) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = emailAddr;
        this.birthDate = birthData;
        this.occupation = occupation;
        this.servicePlan = servicePlan;
    }

    public Collection<Account> getAccounts() {
        return ibanToAccount.values();
    }

    /**
     * Adds an account to the user.
     * @param account to be added
     */
    public void addAccountByIban(final Account account) {
        if (ibanToAccount.containsKey(account.getIban())) {
            // TODO: Change exception.
            throw new ClientAlreadyExists();
        }
        ibanToAccount.put(account.getIban(), account);
        BankingManager.getInstance().addUserByFeature(account.getIban(), this);
    }

    /**
     * Removes account with provided iban and associated cards.
     * @param iban to be queried and removed
     */
    public Optional<Account> removeAccountByIban(final String iban) throws BalanceNotZeroException {
        if (!ibanToAccount.containsKey(iban)) {
            return Optional.empty();
        }
        Account account = ibanToAccount.get(iban);
        if (account.getBalance() > 0) {
            throw new BalanceNotZeroException("Account couldn't be deleted - "
                    + "see org.poo.transactions for details");
        }
        return Optional.ofNullable(ibanToAccount.remove(iban));
    }

    /**
     * Returns the user's account with the provided iban.
     * @param iban to be queried
     * @return Optional<Account>
     */
    public Optional<Account> getAccountByIban(final String iban) {
        if (!ibanToAccount.containsKey(iban)) {
            return Optional.empty();
        }
        return Optional.ofNullable(ibanToAccount.get(iban));
    }

    /**
     * Adds a card to the user.
     * @param card to be added
     */
    public void addCardByCardNumber(final Card card) {
        if (cardNumberToCard.containsKey(card.getCardNumber())) {
            return;
        }
        cardNumberToCard.put(card.getCardNumber(), card);
        card.getOwner().addCard(card);
        BankingManager.getInstance().addUserByFeature(card.getCardNumber(), this);
    }

    /**
     * Removes card with provided cardNumber.
     * @param cardNumber to be queried and removed
     * @return Optional<Card>
     */
    public Optional<Card> removeCardByCardNumber(final String cardNumber) {
        if (!cardNumberToCard.containsKey(cardNumber)) {
            return Optional.empty();
        }
        Card card = cardNumberToCard.get(cardNumber);
        card.getOwner().removeCard(card);
        return Optional.ofNullable(cardNumberToCard.remove(cardNumber));
    }

    /**
     * Returns the user's card with the provided cardNumber.
     * @param cardNumber to be queried
     * @return Optional<Card>
     */
    public Optional<Card> getCardByCardNumber(final String cardNumber) {
        if (!cardNumberToCard.containsKey(cardNumber)) {
            return Optional.empty();
        }
        return Optional.ofNullable(cardNumberToCard.get(cardNumber));
    }

    /**
     * Associates an alias to an account.
     * @param alias to be associated
     * @param iban to be associated with alias
     */
    public void addAlias(final String alias, final String iban) {
        aliases.put(alias, iban);
    }

    /**
     * Returns the iban of the account associated with the alias.
     * @param alias to be queried
     * @return String
     */
    public String getAlias(final String alias) {
        return aliases.get(alias);
    }
}
