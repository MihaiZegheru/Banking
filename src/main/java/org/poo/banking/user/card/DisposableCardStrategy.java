package org.poo.banking.user.card;

import org.poo.banking.BankingManager;
import org.poo.banking.currency.ForexGenie;
import org.poo.banking.user.account.Account;
import org.poo.banking.user.account.exception.FrozenCardException;
import org.poo.banking.user.account.exception.InsufficientFundsException;
import org.poo.fileio.CommandInput;
import org.poo.mock.command.BankingCommand;
import org.poo.mock.command.BankingCommandFactory;
import org.poo.mock.command.BankingQuerent;
import org.poo.mock.command.exception.BankingCommandNotImplemented;

import java.util.Objects;

/**
 * Card instance gets deleted once a payment is done. A new one is created immediately.
 */
public final class DisposableCardStrategy extends Card {
    public DisposableCardStrategy(final String cardNumber, final String status,
                                  final Account owner) {
        super(cardNumber, status, owner);
    }

    @Override
    public void ask(final double amount, final String currency) throws InsufficientFundsException {
        ForexGenie forexGenie = BankingManager.getInstance().getForexGenie();
        double newAmount = forexGenie.queryRate(currency, owner.getCurrency(), amount);
        if (Objects.equals(status, "frozen")) {
            throw new FrozenCardException("The card is frozen");
        }
        if (newAmount > owner.getBalance()) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        owner.setBalance(owner.getBalance() - newAmount);

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
        addCommandInput.setAccount(getOwner().getIban());
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
    public void giveBack(final double amount, final String currency) {
    }
}
