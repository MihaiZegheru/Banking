package org.poo.banking.user.account;

import org.poo.banking.user.User;
import org.poo.utils.Utils;

public final class AccountFactory {
    /**
     * Create an account.
     * @param accountType
     * @param currency
     * @param interestRate
     * @param user
     * @return account
     */
    public static Account createAccount(final String accountType, final String currency,
                                        final double interestRate, final User user) {
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
                return null;
            }
        }
    }

    private AccountFactory() {

    }
}
