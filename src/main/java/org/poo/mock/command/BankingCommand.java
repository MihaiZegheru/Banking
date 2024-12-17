package org.poo.mock.command;

/**
 * Defined a base for banking commands. All banking commands should inherit from this class.
 */
public abstract class BankingCommand implements Command {
    protected String command;

    public BankingCommand(final String command) {
        this.command = command;
    }
}
