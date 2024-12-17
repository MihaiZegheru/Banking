package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;

import java.util.Optional;

public final class AddUserCommand extends BankingCommand {
    private final String firstName;
    private final String lastname;
    private final String email;

    public AddUserCommand(final String command, final String firstName, final String lastname,
                          final String email) {
        super(command);
        this.firstName = firstName;
        this.lastname = lastname;
        this.email = email;
    }

    @Override
    public Optional<ObjectNode> execute() {
        BankingManager.getInstance().addUserByFeature(email, new User(firstName, lastname, email));
        return Optional.empty();
    }
}
