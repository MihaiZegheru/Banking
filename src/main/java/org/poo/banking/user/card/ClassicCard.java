package org.poo.banking.user.card;

import org.poo.banking.BankingManager;
import org.poo.banking.currency.ForexGenie;
import org.poo.banking.user.account.Account;
import org.poo.banking.user.account.exception.FrozenCardException;
import org.poo.banking.user.account.exception.InsufficientFundsException;

import java.util.Objects;

public final class ClassicCard extends Card {
    public ClassicCard(final String cardNumber, final String status, final Account owner) {
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
        owner.getOwningUser().getServicePlan().collectCommission(amount, currency, this);
    }

    @Override
    public void payCommission(final double amount, final String currency) {
        ForexGenie forexGenie = BankingManager.getInstance().getForexGenie();
        double newAmount = forexGenie.queryRate(currency, owner.getCurrency(), amount);
        if (newAmount > owner.getBalance()) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        owner.setBalance(owner.getBalance() - newAmount);
    }

    @Override
    public void giveBack(final double amount, final String currency) {
    }

    @Override
    public Account getAccount() {
        return getOwner();
    }
}
