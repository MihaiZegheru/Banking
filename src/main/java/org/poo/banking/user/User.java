package org.poo.banking.user;

import org.poo.banking.user.account.Account;

import java.util.ArrayList;

public class User {
    private String firstName;
    private String lastName;
    private String email;

    // Users should not have many accounts so it is reasonable to iterate through all when
    // performing operations.
    private ArrayList<Account> accounts;

    public User(String firstName, String lastName, String emailAddr) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = emailAddr;
        accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    /**
     * Removes account and associated cards.
     * @param account
     */
    public void removeAccount(Account account) {
        // TODO: Add check if account has no funds left.
        account.getCards().clear();
        accounts.remove(account);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }
}
