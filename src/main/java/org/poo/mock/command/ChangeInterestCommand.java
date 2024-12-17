package org.poo.mock.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Account;
import org.poo.banking.user.account.exception.AccountIsNotSavingsAccount;
import org.poo.banking.user.tracking.TrackingNode;

import java.util.Optional;

public final class ChangeInterestCommand extends BankingCommand {
    private final String iban;
    private final double interestRate;
    private final int timestamp;

    public ChangeInterestCommand(final String command, final String iban, final double interestRate,
                                 final int timestamp) {
        super(command);
        this.iban = iban;
        this.interestRate = interestRate;
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

        try {
            account.setInterestRate(interestRate);
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
        TrackingNode.TrackingNodeBuilder trackingBuilder = new TrackingNode.TrackingNodeBuilder()
                .setTimestamp(timestamp)
                .setDescription("Interest rate of the account changed to " + interestRate)
                .setProducer(account);
        user.getUserTracker().onInterestRateChanged(trackingBuilder.build());
        return Optional.empty();
    }
}
