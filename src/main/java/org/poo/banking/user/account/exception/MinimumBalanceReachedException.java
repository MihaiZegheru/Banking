package org.poo.banking.user.account.exception;

public class MinimumBalanceReachedException extends RuntimeException {
    public MinimumBalanceReachedException(String message) {
        super(message);
    }
}
