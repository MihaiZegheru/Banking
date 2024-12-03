package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.LinkedList;
import java.util.Optional;

public class BankingQuerent {
    private LinkedList<BankingCommand> history = new LinkedList<>();

    public Optional<ObjectNode> query(BankingCommand command) {
        history.push(command);
        return command.execute();
    }
}
