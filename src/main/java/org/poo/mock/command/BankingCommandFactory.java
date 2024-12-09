package org.poo.mock.command;

import org.poo.mock.command.exception.BankingCommandNotImplemented;
import org.poo.fileio.CommandInput;

public class BankingCommandFactory {
    public static BankingCommand createBankingCommand(CommandInput commandInput)
            throws BankingCommandNotImplemented{
        switch (commandInput.getCommand()) {
            case "addUser" -> {
                return new AddUserCommand(commandInput.getCommand(),
                        commandInput.getFirstName(),
                        commandInput.getLastName(),
                        commandInput.getEmail());
            }
            case "printUsers" -> {
                return new PrintUsersCommand(commandInput.getCommand(),
                        commandInput.getTimestamp());
            }
            case "addAccount" -> {
                return new AddAccountCommand(commandInput.getCommand(),
                        commandInput.getAccountType(),
                        commandInput.getCurrency(),
                        commandInput.getEmail(),
                        commandInput.getInterestRate(),
                        commandInput.getTimestamp());
            }
            case "addFunds" -> {
                return new AddFundsCommand(commandInput.getCommand(),
                        commandInput.getAmount(),
                        commandInput.getAccount(),
                        commandInput.getTimestamp());
            }
            case "createCard" -> {
                return new CreateCardCommand(commandInput.getCommand(),
                        commandInput.getAccount(),
                        commandInput.getEmail(),
                        commandInput.getTimestamp());
            }
            case "createOneTimeCard" -> {
                return new CreateOneTimeCardCommand(commandInput.getCommand(),
                        commandInput.getAccount(),
                        commandInput.getEmail(),
                        commandInput.getTimestamp());
            }
            case "deleteAccount" -> {
                return new DeleteAccountCommand(commandInput.getCommand(),
                        commandInput.getAccount(),
                        commandInput.getEmail(),
                        commandInput.getTimestamp());
            }
            case "deleteCard" -> {
                return new DeleteCardCommand(commandInput.getCommand(),
                        commandInput.getCardNumber(),
                        commandInput.getTimestamp());
            }
            case "payOnline" -> {
                return new PayOnlineCommand(commandInput.getCommand(),
                        commandInput.getCardNumber(),
                        commandInput.getAmount(),
                        commandInput.getCurrency(),
                        commandInput.getDescription(),
                        commandInput.getCommerciant(),
                        commandInput.getEmail(),
                        commandInput.getTimestamp());
            }
            case "addCurrency" -> {
                return new AddCurrencyCommand(commandInput.getCommand(),
                        commandInput.getFromCurrency(),
                        commandInput.getToCurrency(),
                        commandInput.getAmount());
            }
            default -> throw new BankingCommandNotImplemented(
                    "BankingCommand "
                    + commandInput.getCommand()
                    + " has no implementation.");
        }
    }
}
