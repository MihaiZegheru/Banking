package org.poo.mock.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Account;
import org.poo.banking.user.account.exception.AccountIsNotSavingsAccount;
import org.poo.banking.user.tracking.TrackingNode;

import java.util.Optional;

public final class AddInterestCommand extends BankingCommand {
    private final String iban;
    private final int timestamp;

    public AddInterestCommand(final String command, final String iban, final int timestamp) {
        super(command);
        this.iban = iban;
        this.timestamp = timestamp;
    }

    @Override
    public Optional<ObjectNode> execute() {
        BankingManager.getInstance().setTime(timestamp);
        Optional<User> userResult = BankingManager.getInstance().getUserByFeature(iban);
        if (userResult.isEmpty()) {
            return Optional.empty();
        }
        User user = userResult.get();

        Optional<Account> accountResult = user.getAccountByIban(iban);
        if (accountResult.isEmpty()) {
            return Optional.empty();
        }
        Account account = accountResult.get();

        TrackingNode.TrackingNodeBuilder trackingBuilder =
                new TrackingNode.TrackingNodeBuilder()
                        .setTimestamp(timestamp)
                        .setProducer(account);
        try {
            double amount = account.collect();
            user.getUserTracker().onTransaction(trackingBuilder
                    .setAmount(amount)
                    .setCurrency(account.getCurrency())
                    .setDescription("Interest rate income")
                    .build());

        } catch (AccountIsNotSavingsAccount e) {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode objectNode = objectMapper.createObjectNode();
            ObjectNode outputNode = objectMapper.createObjectNode();
            objectNode.put("command", command);
            objectNode.put("timestamp", timestamp);
            outputNode.put("description", e.getMessage());
            outputNode.put("timestamp", timestamp);
            objectNode.put("output", outputNode);
            return Optional.of(objectNode);
        }
        return Optional.empty();
    }
}
