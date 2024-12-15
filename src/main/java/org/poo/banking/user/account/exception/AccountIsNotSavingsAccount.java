package org.poo.banking.user.account.exception;

public class AccountIsNotSavingsAccount extends RuntimeException {
    public AccountIsNotSavingsAccount(String message) {
        super(message);
    }
}
