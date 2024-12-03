package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.account.Account;
import org.poo.banking.user.account.DisposableCardStrategy;
import org.poo.utils.Utils;

import java.util.Optional;

public class CreateOneTimeCardCommand extends BankingCommand {
    private final String iban;
    private final String email;
    private final int timestamp;

    public CreateOneTimeCardCommand(String command, String iban, String email, int timestamp) {
        super(command);
        this.iban = iban;
        this.email = email;
        this.timestamp = timestamp;
    }

    @Override
    public Optional<ObjectNode> execute() {
        Optional<Account> result = BankingManager.getInstance().getAccountByIban(iban);
        if (result.isEmpty()) {
            // TODO: Report issue
            return Optional.empty();
        }
        Account account = result.get();
        account.addCard(new DisposableCardStrategy(Utils.generateCardNumber(), "active"));
        return Optional.empty();
    }
}
