package org.poo.banking.user.account;

import org.poo.banking.BankingManager;
import org.poo.banking.currency.ForexGenie;
import org.poo.banking.user.account.exception.FrozenCardException;
import org.poo.banking.user.account.exception.InsufficientFundsException;
import org.poo.fileio.CommandInput;
import org.poo.mock.command.BankingCommand;
import org.poo.mock.command.BankingCommandFactory;
import org.poo.mock.command.BankingQuerent;
import org.poo.mock.command.exception.BankingCommandNotImplemented;

import java.util.Objects;
import java.util.Optional;

public class DisposableCardStrategy extends Card {
    public DisposableCardStrategy(String cardNumber, String status, Account owner) {
        super(cardNumber, status, owner);
    }

    @Override
    public void ask(double amount, String currency) throws InsufficientFundsException {
        ForexGenie forexGenie = BankingManager.getInstance().getForexGenie();
        amount = forexGenie.queryRate(currency, owner.getCurrency(), amount);
        if (Objects.equals(status, "frozen")) {
            throw new FrozenCardException("The card is frozen");
        }
        if (amount > owner.getBalance()) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        owner.setBalance(owner.getBalance() - amount);

        BankingQuerent bankingQuerent = BankingManager.getInstance().getQuerent();

        CommandInput removeCommandInput = new CommandInput();
        removeCommandInput.setCommand("deleteCard");
        removeCommandInput.setCardNumber(cardNumber);
        removeCommandInput.setTimestamp(BankingManager.getInstance().getTime());
        BankingCommand removeCommand;
        try {
            removeCommand = BankingCommandFactory.createBankingCommand(removeCommandInput);
        } catch (BankingCommandNotImplemented e) {
            System.out.println(e.getMessage());
            return;
        }
        bankingQuerent.queue(removeCommand);

        CommandInput addCommandInput = new CommandInput();
        addCommandInput.setCommand("createOneTimeCard");
        addCommandInput.setAccount(getOwner().iban);
        addCommandInput.setEmail(getOwner().getOwner().getEmail());
        addCommandInput.setTimestamp(BankingManager.getInstance().getTime());
        BankingCommand addCommand;
        try {
            addCommand = BankingCommandFactory.createBankingCommand(addCommandInput);
        } catch (BankingCommandNotImplemented e) {
            System.out.println(e.getMessage());
            return;
        }
        bankingQuerent.queue(addCommand);
    }

    @Override
    public void giveBack(double amount, String currency) {
    }
}
