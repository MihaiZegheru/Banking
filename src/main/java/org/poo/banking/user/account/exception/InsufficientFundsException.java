package org.poo.banking.user.account.exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(final String message) {
        super(message);
    }
}
