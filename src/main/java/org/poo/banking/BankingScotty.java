package org.poo.banking;

import org.poo.banking.exception.ClientAlreadyExists;
import org.poo.banking.seller.Seller;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Account;
import org.poo.banking.user.account.Card;

import java.util.*;

public class BankingScotty {
    Map<String, User> emailToUser = new HashMap<>();
    Map<String, Seller> nameToSeller = new HashMap<>();
    Map<String, Account> ibanToAccount = new HashMap<>();
    Map<String, Card> cardNumberToCard = new HashMap<>();

    public void addUserByEmail(User user) {
        if (emailToUser.containsKey(user.getEmail())) {
            throw new ClientAlreadyExists();
        }
        emailToUser.put(user.getEmail(), user);
    }

    public Optional<User> getUserByEmail(String email) {
        if (!emailToUser.containsKey(email)) {
            return Optional.empty();
        }
        return Optional.ofNullable(emailToUser.get(email));
    }

    public void addAccountByIban(Account account) {
        if (ibanToAccount.containsKey(account.getIban())) {
            // TODO: Change exception.
            throw new ClientAlreadyExists();
        }
        ibanToAccount.put(account.getIban(), account);
    }

    public Optional<Account> removeAccountByIban(String iban) {
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
            // TODO: Change exception.
            return;
        }
        cardNumberToCard.put(card.getCardNumber(), card);
    }

    public Optional<Card> removeCardByCardNumber(String cardNumber) {
        return Optional.ofNullable(cardNumberToCard.remove(cardNumber));
    }

    public Collection<User> getUsers() {
        return emailToUser.values();
    }
}
