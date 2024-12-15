package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.*;

public class BankingQuerent {
    private LinkedList<BankingCommand> history = new LinkedList<>();
    private Queue<BankingCommand> queuedCommands = new LinkedList<>();

    public List<Optional<ObjectNode>> query(BankingCommand command) {
        List<Optional<ObjectNode>> outputs = new ArrayList<>();
        history.push(command);
        outputs.add(command.execute());
        while (!queuedCommands.isEmpty()) {
            BankingCommand queuedCommand = queuedCommands.remove();
            history.push(queuedCommand);
            outputs.add(queuedCommand.execute());
        }
        return outputs;
    }

    public void queue(BankingCommand command) {
        queuedCommands.add(command);
    }
}
