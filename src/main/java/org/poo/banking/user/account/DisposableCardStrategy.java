package org.poo.banking.user.account;

import org.poo.banking.BankingManager;
import org.poo.banking.user.account.exception.FrozenCardException;
import org.poo.banking.user.account.exception.InsufficientFundsException;
import org.poo.banking.user.account.exception.MinimumBalanceReachedException;
import org.poo.fileio.CommandInput;
import org.poo.mock.command.BankingCommand;
import org.poo.mock.command.BankingCommandFactory;
import org.poo.mock.command.BankingQuerent;
import org.poo.mock.command.exception.BankingCommandNotImplemented;

import java.util.Objects;

public class DisposableCardStrategy extends Card implements PaymentStrategy {
    public DisposableCardStrategy(String cardNumber, String status, Account owner) {
        super(cardNumber, status, owner);
    }

    @Override
    public double pay(Account receiver, double amount, String currency) throws InsufficientFundsException,
            MinimumBalanceReachedException, FrozenCardException {
        amount = BankingManager.getInstance().getForexGenie().queryRate(currency, owner.currency,
                amount);
        if (Objects.equals(status, "Frozen")) {
            throw new FrozenCardException("The card is frozen");
        }
        if (owner.balance - amount <= owner.minBalance) {
            throw new MinimumBalanceReachedException("You have reached the minimum amount of funds, the card will be frozen");
        }
        owner.balance -= amount;


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
            return amount;
        }
        bankingQuerent.query(bankingCommand);

        return amount;
    }
}
