package org.poo.mock.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Account;

import java.util.Objects;
import java.util.Optional;

public class SpendingsReportCommand extends BankingCommand {
    private final int startTimestamp;
    private final int endTimestamp;
    private final String iban;
    private final int timestamp;

    public SpendingsReportCommand(String command, int startTimestamp, int endTimestamp, String iban,
                                  int timestamp) {
        super(command);
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.iban = iban;
        this.timestamp = timestamp;
    }

    @Override
    public Optional<ObjectNode> execute() {
        BankingManager.getInstance().setTime(timestamp);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command);
        objectNode.put("timestamp", timestamp);
        ObjectNode outputNode = objectMapper.createObjectNode();

        Optional<User> userResult = BankingManager.getInstance().getUserByFeature(iban);
        if (userResult.isEmpty()) {
            outputNode.put("description", "Account not found");
            outputNode.put("timestamp", timestamp);
            objectNode.put("output", outputNode);
            return Optional.of(objectNode);
        }
        User user = userResult.get();

        Optional<Account> accountResult = user.getAccountByIban(iban);
        if (accountResult.isEmpty()) {
            outputNode.put("description", "Account not found");
            outputNode.put("timestamp", timestamp);
            objectNode.put("output", outputNode);
            return Optional.of(objectNode);
        }
        Account account = accountResult.get();

        if (Objects.equals(account.getType(), "savings")) {
            outputNode.put("error", "This kind of report is not supported for a saving account");
        } else {
            outputNode.put("IBAN", iban);
            outputNode.put("balance", account.getBalance());
            outputNode.put("currency", account.getCurrency());
            outputNode.put("transactions", objectMapper.valueToTree(
                    user.getUserTracker().generateSpendingsReport(startTimestamp,
                            endTimestamp, account)));
            outputNode.put("commerciants", objectMapper.valueToTree(
                    user.getUserTracker().generateSellersReport(startTimestamp,
                            endTimestamp, account)));
        }
        objectNode.put("output", outputNode);
        return Optional.of(objectNode);
    }
}
