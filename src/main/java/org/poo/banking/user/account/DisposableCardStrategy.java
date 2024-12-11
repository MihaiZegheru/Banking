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

        Optional<Card> cardResult = owner.getOwner().removeCardByCardNumber(cardNumber);
        if (cardResult.isEmpty()) {
            // TODO: Decide if should report issue.
            return;
        }
        BankingManager.getInstance().removeFeature(cardNumber);

        BankingQuerent bankingQuerent = new BankingQuerent();
        CommandInput command = new CommandInput();
        command.setCommand("createOneTimeCard");
        command.setAccount(getOwner().iban);
        command.setEmail(getOwner().getOwner().getEmail());
        command.setTimestamp(-1);

        BankingCommand bankingCommand;
        try {
            bankingCommand = BankingCommandFactory.createBankingCommand(command);
        } catch (BankingCommandNotImplemented e) {
            System.out.println(e.getMessage());
            return;
        }
        bankingQuerent.query(bankingCommand);
    }
}
