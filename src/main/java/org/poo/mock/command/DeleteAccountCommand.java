package org.poo.mock.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;
import org.poo.banking.user.account.exception.BalanceNotZeroException;
import org.poo.banking.user.tracking.TrackingNode;

import java.util.Optional;

public final class DeleteAccountCommand extends BankingCommand {
    private final String iban;
    private final String email;
    private final int timestamp;

    public DeleteAccountCommand(final String command, final String iban, final String email,
                                final int timestamp) {
        super(command);
        this.iban = iban;
        this.email = email;
        this.timestamp = timestamp;
    }

    @Override
    public Optional<ObjectNode> execute() {
        BankingManager.getInstance().setTime(timestamp);
        Optional<User> userResult = BankingManager.getInstance().getUserByFeature(email);
        if (userResult.isEmpty()) {
            return Optional.empty();
        }
        User user = userResult.get();

        TrackingNode.TrackingNodeBuilder trackingBuilder = new TrackingNode.TrackingNodeBuilder()
                .setTimestamp(timestamp);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        ObjectNode outputNode = objectMapper.createObjectNode();
        objectNode.put("command", command);
        objectNode.put("timestamp", timestamp);
        try {
            user.removeAccountByIban(iban);
            outputNode.put("success", "Account deleted");
            outputNode.put("timestamp", timestamp);
        } catch (BalanceNotZeroException e) {
            outputNode.put("error", e.getMessage());
            outputNode.put("timestamp", timestamp);
            trackingBuilder.setDescription("Account couldn't be deleted - "
                    + "there are funds remaining");
        } finally {
            objectNode.put("output", outputNode);
        }
        user.getUserTracker().onAccountDeleted(trackingBuilder.build());
        return Optional.of(objectNode);
    }
}
