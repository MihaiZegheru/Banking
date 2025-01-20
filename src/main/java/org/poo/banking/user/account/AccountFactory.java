package org.poo.banking.user.account;

import org.poo.banking.user.User;
import org.poo.utils.Utils;

public final class AccountFactory {
    public static Account createAccount(final String accountType, String currency,
                                        double interestRate, User user) {
        switch (accountType) {
            case "classic" -> {
                return new ClassicAccount(accountType, Utils.generateIBAN(),
                        currency, user);
            }
            case "savings" -> {
                return new SavingsAccount(accountType, Utils.generateIBAN(),
                        currency, interestRate, user);
            }
            default -> {
                // TODO: Throw error
                return null;
            }
        }


    }
}
