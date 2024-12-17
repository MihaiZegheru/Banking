package org.poo.banking.user.account.exception;

public class MinimumBalanceReachedException extends RuntimeException {
    public MinimumBalanceReachedException(final String message) {
        super(message);
    }
}
