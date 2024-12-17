package org.poo.mock.command.exception;

public final class BankingCommandNotImplemented extends RuntimeException {
    public BankingCommandNotImplemented(final String message) {
        super(message);
    }
}
