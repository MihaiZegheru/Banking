package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;

import java.util.Optional;

public final class SetAliasCommand extends BankingCommand {
    private final String email;
    private final String alias;
    private final String iban;

    public SetAliasCommand(final String command, final String email, final String alias,
                           final String iban) {
        super(command);
        this.email = email;
        this.alias = alias;
        this.iban = iban;
    }

    @Override
    public Optional<ObjectNode> execute() {
        Optional<User> userResult = BankingManager.getInstance().getUserByFeature(email);
        if (userResult.isEmpty()) {
            return Optional.empty();
        }
        User user = userResult.get();
        user.addAlias(alias, iban);
        return Optional.empty();
    }
}
