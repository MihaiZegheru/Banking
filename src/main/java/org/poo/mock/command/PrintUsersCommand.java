package org.poo.mock.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;

import java.util.Optional;

public class PrintUsersCommand extends BankingCommand {
    private final int timestamp;

    public PrintUsersCommand(String command, int timestamp) {
        super(command);
        this.timestamp = timestamp;
    }

    @Override
    public Optional<ObjectNode> execute() {
        BankingManager.getInstance().setTime(timestamp);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command);
        objectNode.put("timestamp", timestamp);
        objectNode.put("output",
                objectMapper.valueToTree(BankingManager.getInstance().getUsers()));
        return Optional.of(objectNode);
    }
}
