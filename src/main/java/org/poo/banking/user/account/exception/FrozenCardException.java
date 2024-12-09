package org.poo.banking.user.account.exception;

public class FrozenCardException extends RuntimeException {
    public FrozenCardException(String message) {
        super(message);
    }
}
