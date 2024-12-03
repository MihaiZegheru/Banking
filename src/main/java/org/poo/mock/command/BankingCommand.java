package org.poo.mock.command;

import java.util.List;

public abstract class BankingCommand implements Command {
    protected String command;

    public BankingCommand(String command) {
        this.command = command;
    }
}
