package org.poo.mock.command.exception;

public class BankingCommandNotImplemented extends RuntimeException {
    public BankingCommandNotImplemented(String message) {
        super(message);
    }
}
