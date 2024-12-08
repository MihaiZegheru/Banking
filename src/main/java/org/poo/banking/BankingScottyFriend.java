package org.poo.banking;

import org.poo.banking.exception.ClientAlreadyExists;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Account;
import org.poo.banking.user.account.Card;

import java.util.Collection;
import java.util.Optional;

public interface BankingScottyFriend {
    void addUserByEmail(User user);
    Optional<User> getUserByEmail(String email);
    void addAccountByIban(Account account);
    Optional<Account> removeAccountByIban(String iban);
    Optional<Account> getAccountByIban(String iban);
    void addCardByCardNumber(Card card);
    Optional<Card> removeCardByCardNumber(String cardNumber);
    Collection<User> getUsers();
}
