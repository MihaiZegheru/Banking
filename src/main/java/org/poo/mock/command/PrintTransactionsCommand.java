package org.poo.mock.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;

import java.util.Optional;

public class PrintTransactionsCommand extends BankingCommand {
    private final String email;
    private final int timestamp;

    public PrintTransactionsCommand(String command, String email, int timestamp) {
        super(command);
        this.email = email;
        this.timestamp = timestamp;
    }

    @Override
    public Optional<ObjectNode> execute() {
        Optional<User> userResult = BankingManager.getInstance().getUserByFeature(email);
        if (userResult.isEmpty()) {
            // TODO: Report issue
            return Optional.empty();
        }
        User user = userResult.get();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command);
        objectNode.put("timestamp", timestamp);
        objectNode.put("output",
                objectMapper.valueToTree(user.getFlowTracker().getHistory()));
        return Optional.of(objectNode);
    }
}
