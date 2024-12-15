package org.poo.banking.user.account;

import org.poo.banking.BankingManager;
import org.poo.banking.currency.ForexGenie;
import org.poo.banking.user.account.exception.FrozenCardException;
import org.poo.banking.user.account.exception.InsufficientFundsException;

import java.util.Objects;

public class ClassicCard extends Card {
    public ClassicCard(String cardNumber, String status, Account owner) {
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
    }

    @Override
    public void giveBack(double amount, String currency) {
    }
}
