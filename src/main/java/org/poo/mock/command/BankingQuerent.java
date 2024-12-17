package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.ArrayList;

public final class BankingQuerent {
    private final LinkedList<BankingCommand> history = new LinkedList<>();
    private final Queue<BankingCommand> queuedCommands = new LinkedList<>();

    /**
     * Query a command for execution. Once executed, the queue is emptied of scheduled commands.
     * @param command to be executed.
     */
    public List<Optional<ObjectNode>> query(final BankingCommand command) {
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

    /**
     * Queue a command to be executed immediately after the currently executing command.
     * Should only be called from the tracing stack of an ongoing command.
     * @param command to be queued
     */
    public void queue(final BankingCommand command) {
        queuedCommands.add(command);
    }
}
