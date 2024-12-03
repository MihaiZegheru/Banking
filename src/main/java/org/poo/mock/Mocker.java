package org.poo.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;
import org.poo.fileio.CommandInput;
import org.poo.fileio.ObjectInput;
import org.poo.fileio.UserInput;
import org.poo.mock.command.BankingCommand;
import org.poo.mock.command.BankingCommandFactory;
import org.poo.mock.command.BankingQuerent;
import org.poo.mock.command.exception.BankingCommandNotImplemented;

import java.util.Optional;

public final class Mocker {
    public static ArrayNode mock(final ObjectInput testingInput) {
        BankingManager bankingManager = BankingManager.getInstance();

        // TODO: Add lombok getters everywhere.
        populateUsers(testingInput.getUsers());
        ArrayNode arrayNode = runCommands(testingInput.getCommands());

        bankingManager.resetInstance();
        return arrayNode;
    }

    private static void populateUsers(final UserInput[] testingUsers) {
        BankingQuerent bankingQuerent = new BankingQuerent();

        for (UserInput user : testingUsers) {
            CommandInput command = new CommandInput();
            command.setCommand("addUser");
            command.setFirstName(user.getFirstName());
            command.setLastName(user.getLastName());
            command.setEmail(user.getEmail());

            BankingCommand bankingCommand;
            try {
                bankingCommand = BankingCommandFactory.createBankingCommand(command);
            } catch (BankingCommandNotImplemented e) {
                System.out.println(e.getMessage());
                continue;
            }
            bankingQuerent.query(bankingCommand);
        }
    }

    private static ArrayNode runCommands(final CommandInput[] testingCommands) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNode = objectMapper.createArrayNode();

        BankingQuerent bankingQuerent = new BankingQuerent();

        for (CommandInput command : testingCommands) {
            BankingCommand bankingCommand;
            try {
                bankingCommand = BankingCommandFactory.createBankingCommand(command);
            } catch (BankingCommandNotImplemented e) {
                System.out.println(e.getMessage());
                continue;
            }
            Optional<ObjectNode> result = bankingQuerent.query(bankingCommand);
            if (result.isEmpty()) {
                continue;
            }
            arrayNode.add(result.get());

        }

        return  arrayNode;
    }
}
