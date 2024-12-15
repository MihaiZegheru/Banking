package org.poo.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;
import org.poo.fileio.CommandInput;
import org.poo.fileio.ExchangeInput;
import org.poo.fileio.ObjectInput;
import org.poo.fileio.UserInput;
import org.poo.mock.command.BankingCommand;
import org.poo.mock.command.BankingCommandFactory;
import org.poo.mock.command.BankingQuerent;
import org.poo.mock.command.exception.BankingCommandNotImplemented;

import java.util.List;
import java.util.Optional;

public final class Mocker {
    public static ArrayNode mock(final ObjectInput testingInput) {
        BankingManager bankingManager = BankingManager.getInstance();

        // TODO: Add lombok getters everywhere.
        // TODO: Separate Jackson logic from commands by returning an output object / exception.
        populateUsers(testingInput.getUsers());
        populateCurrencies(testingInput.getExchangeRates());
        ArrayNode arrayNode = runCommands(testingInput.getCommands());

        bankingManager.resetInstance();
        return arrayNode;
    }

    private static void populateCurrencies(final ExchangeInput[] testingFX) {
        BankingQuerent bankingQuerent = new BankingQuerent();

        for (ExchangeInput fx : testingFX) {
            CommandInput command = new CommandInput();
            command.setCommand("addCurrency");
            command.setFromCurrency(fx.getFrom());
            command.setToCurrency(fx.getTo());
            command.setAmount(fx.getRate());

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

        BankingQuerent bankingQuerent = BankingManager.getInstance().getQuerent();

        for (CommandInput command : testingCommands) {
            BankingCommand bankingCommand;
            try {
                bankingCommand = BankingCommandFactory.createBankingCommand(command);
            } catch (BankingCommandNotImplemented e) {
                System.out.println(e.getMessage());
                continue;
            }
            List<Optional<ObjectNode>> results = bankingQuerent.query(bankingCommand);
            for (Optional<ObjectNode> output : results) {
                if (output.isEmpty()) {
                    continue;
                }
                arrayNode.add(output.get());
            }
        }

        return  arrayNode;
    }
}
