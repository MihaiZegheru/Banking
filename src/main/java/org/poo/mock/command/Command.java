package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Optional;

public interface Command {
    Optional<ObjectNode> execute();
}
