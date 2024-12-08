package org.poo.mock.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Account;
import org.poo.banking.user.account.ClassicAccountStrategy;
import org.poo.banking.user.account.SavingsAccountStrategy;
import org.poo.utils.Utils;

import java.util.Optional;

public class DeleteAccountCommand extends BankingCommand {
    private final String iban;
    private final String email;
    private final int timestamp;

    public DeleteAccountCommand(String command, String iban, String email, int timestamp) {
        super(command);
        this.iban = iban;
        this.email = email;
        this.timestamp = timestamp;
    }

    @Override
    public Optional<ObjectNode> execute() {
        Optional<User> userResult = BankingManager.getInstance().getUserByEmail(email);
        if (userResult.isEmpty()) {
            // TODO: Report issue.
            return Optional.empty();
        }
        User user = userResult.get();
        Optional<Account> accountResult = BankingManager.getInstance().removeAccountByIban(iban);
        if (accountResult.isEmpty()) {
            // TODO: Report issue.
            return Optional.empty();
        }
        user.removeAccount(accountResult.get());

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        ObjectNode outputNode = objectMapper.createObjectNode();
        outputNode.put("success", "Account deleted");
        outputNode.put("timestamp", timestamp);

        objectNode.put("command", command);
        objectNode.put("timestamp", timestamp);
        objectNode.put("output", outputNode);

        return Optional.of(objectNode);
    }
}
