package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Optional;

/**
 * Should be implemented by all commands.
 */
public interface Command {
    /**
     * Executes Command.
     * @return Optional<ObjectNode>
     */
    Optional<ObjectNode> execute();
}
