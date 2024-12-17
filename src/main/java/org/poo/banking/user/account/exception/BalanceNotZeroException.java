package org.poo.banking.user.account.exception;

public class BalanceNotZeroException extends RuntimeException {
    public BalanceNotZeroException(final String message) {
        super(message);
    }
}
