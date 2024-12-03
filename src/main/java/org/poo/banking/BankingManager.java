package org.poo.banking;

import org.poo.banking.seller.Seller;
import org.poo.banking.user.User;
import org.poo.banking.exception.ClientAlreadyExists;
import org.poo.banking.user.account.Account;

import java.util.*;

public class BankingManager implements BankingScottyFriend {
    private static BankingManager instance;

    private BankingScotty scotty = new BankingScotty();

    public static BankingManager getInstance() {
        if (instance == null) {
            instance = new BankingManager();
        }
        return instance;
    }

    /**
     * Nullifies the Singleton instance.
     * <p>
     * This function should only be used when finishing a mock test.
     */
    public void resetInstance() {
        instance = null;
    }

    @Override
    public void addUserByEmail(User user) {
        scotty.addUserByEmail(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return scotty.getUserByEmail(email);
    }

    @Override
    public void addAccountByIban(Account account) {
        scotty.addAccountByIban(account);
    }

    @Override
    public Optional<Account> getAccountByIban(String iban) {
        return scotty.getAccountByIban(iban);
    }

    public Collection<User> getUsers() {
        return scotty.getUsers();
    }
}
