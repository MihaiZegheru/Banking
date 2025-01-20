package org.poo.mock.command;

import org.poo.mock.command.exception.BankingCommandNotImplemented;
import org.poo.fileio.CommandInput;

public final class BankingCommandFactory {
    /**
     * Builds a BankingCommand based on provided CommandInput.
     * @param commandInput contains command parameters
     * @return BankingCommand
     * @throws BankingCommandNotImplemented command is not implemented
     */
    public static BankingCommand createBankingCommand(final CommandInput commandInput)
            throws BankingCommandNotImplemented {
        switch (commandInput.getCommand()) {
            case "addUser" -> {
                return new AddUserCommand(commandInput.getCommand(),
                        commandInput.getFirstName(),
                        commandInput.getLastName(),
                        commandInput.getEmail(),
                        commandInput.getBirthDate(),
                        commandInput.getOccupation());
            }
            case "addSeller" -> {
                return new AddSellerCommand(commandInput.getCommand(),
                        commandInput.getName(),
                        commandInput.getId(),
                        commandInput.getAccount(),
                        commandInput.getType(),
                        commandInput.getCashbackStrategy());
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
            case "setAlias" -> {
                return new SetAliasCommand(commandInput.getCommand(),
                        commandInput.getEmail(),
                        commandInput.getAlias(),
                        commandInput.getAccount());
            }
            case "sendMoney" -> {
                return new SendMoneyCommand(commandInput.getCommand(),
                        commandInput.getAccount(),
                        commandInput.getAmount(),
                        commandInput.getReceiver(),
                        commandInput.getDescription(),
                        commandInput.getTimestamp());
            }
            case "printTransactions" -> {
                return new PrintTransactionsCommand(commandInput.getCommand(),
                        commandInput.getEmail(),
                        commandInput.getTimestamp());
            }
            case "setMinimumBalance" -> {
                return new SetMinimumBalanceCommand(commandInput.getCommand(),
                        commandInput.getAmount(),
                        commandInput.getAccount(),
                        commandInput.getTimestamp());
            }
            case "checkCardStatus" -> {
                return new CheckCardStatusCommand(commandInput.getCommand(),
                        commandInput.getCardNumber(),
                        commandInput.getTimestamp());
            }
            case "splitPayment" -> {
                return new SplitPaymentCommand(commandInput.getCommand(),
                        commandInput.getAccounts(),
                        commandInput.getAmount(),
                        commandInput.getCurrency(),
                        commandInput.getTimestamp());
            }
            case "report" -> {
                return new ReportCommand(commandInput.getCommand(),
                        commandInput.getStartTimestamp(),
                        commandInput.getEndTimestamp(),
                        commandInput.getAccount(),
                        commandInput.getTimestamp());
            }
            case "spendingsReport" -> {
                return new SpendingsReportCommand(commandInput.getCommand(),
                        commandInput.getStartTimestamp(),
                        commandInput.getEndTimestamp(),
                        commandInput.getAccount(),
                        commandInput.getTimestamp());
            }
            case "changeInterestRate" -> {
                return new ChangeInterestCommand(commandInput.getCommand(),
                        commandInput.getAccount(),
                        commandInput.getInterestRate(),
                        commandInput.getTimestamp());
            }
            case "addInterest" -> {
                return new AddInterestCommand(commandInput.getCommand(),
                        commandInput.getAccount(),
                        commandInput.getTimestamp());
            }
            case "withdrawSavings" -> {
                return new WithdrawSavingsCommand(commandInput.getCommand(),
                        commandInput.getAmount(),
                        commandInput.getAccount(),
                        commandInput.getCurrency(),
                        commandInput.getTimestamp());
            }
            case "upgradePlan" -> {
                return new UpgradePlanCommand(commandInput.getCommand(),
                        commandInput.getNewPlanType(),
                        commandInput.getAccount(),
                        commandInput.getTimestamp());
            }
            case "cashWithdrawal" -> {
                return new CashWithdrawalCommand(commandInput.getCommand(),
                        commandInput.getCardNumber(),
                        commandInput.getAmount(),
                        commandInput.getEmail(),
                        commandInput.getLocation(),
                        commandInput.getTimestamp());
            }
            default -> throw new BankingCommandNotImplemented(
                    "BankingCommand "
                    + commandInput.getCommand()
                    + " has no implementation.");
        }
    }

    private BankingCommandFactory() {

    }
}
