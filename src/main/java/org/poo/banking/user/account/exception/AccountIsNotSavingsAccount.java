package org.poo.banking.user.account.exception;

public class AccountIsNotSavingsAccount extends RuntimeException {
    public AccountIsNotSavingsAccount(final String message) {
        super(message);
    }
}
