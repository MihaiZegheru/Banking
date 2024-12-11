package org.poo.mock.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Account;

import java.util.Optional;

public class ReportCommand extends BankingCommand {
    private final int startTimestamp;
    private final int endTimestamp;
    private final String iban;
    private final int timestamp;

    public ReportCommand(String command, int startTimestamp, int endTimestamp, String iban,
                         int timestamp) {
        super(command);
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.iban = iban;
        this.timestamp = timestamp;
    }

    @Override
    public Optional<ObjectNode> execute() {
        Optional<User> userResult = BankingManager.getInstance().getUserByFeature(iban);
        if (userResult.isEmpty()) {
            // TODO: Report issue
            return Optional.empty();
        }
        User user = userResult.get();

        Optional<Account> accountResult = user.getAccountByIban(iban);
        if (accountResult.isEmpty()) {
            // TODO: Report issue
            return Optional.empty();
        }
        Account account = accountResult.get();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command);
        objectNode.put("timestamp", timestamp);

        ObjectNode outputNode = objectMapper.createObjectNode();
        outputNode.put("IBAN", iban);
        outputNode.put("balance", account.getBalance());
        outputNode.put("currency", account.getCurrency());
        outputNode.put("transactions",
                objectMapper.valueToTree(user.getUserTracker().generateReport(startTimestamp,
                        endTimestamp, account)));
        objectNode.put("output", outputNode);
        return Optional.of(objectNode);
    }
}
