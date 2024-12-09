package org.poo.banking.user.account;

import org.poo.banking.BankingManager;
import org.poo.banking.user.account.exception.FrozenCardException;
import org.poo.banking.user.account.exception.InsufficientFundsException;
import org.poo.banking.user.account.exception.MinimumBalanceReachedException;

import java.util.Objects;

public class ClassicCardStrategy extends Card implements PaymentStrategy {
    public ClassicCardStrategy(String cardNumber, String status, Account owner) {
        super(cardNumber, status, owner);
    }

    @Override
    public double pay(Account receiver, double amount, String currency) throws InsufficientFundsException,
            MinimumBalanceReachedException, FrozenCardException {
        amount = BankingManager.getInstance().getForexGenie().queryRate(currency, owner.currency,
                amount);
        if (Objects.equals(status, "frozen")) {
            throw new FrozenCardException("The card is frozen");
        }
        if (owner.balance < amount) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        owner.balance -= amount;
        return amount;
    }
}
