package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;

import java.util.Optional;

public class AddUserCommand extends BankingCommand {
    private final String firstName;
    private final String lastname;
    private final String email;

    public AddUserCommand(String command, String firstName, String lastname, String email) {
        super(command);
        this.firstName = firstName;
        this.lastname = lastname;
        this.email = email;
    }

    @Override
    public Optional<ObjectNode> execute() {
        BankingManager.getInstance().addUserByEmail(new User(firstName, lastname, email));
        return Optional.empty();
    }
}
