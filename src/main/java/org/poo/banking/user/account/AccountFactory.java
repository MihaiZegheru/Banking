package org.poo.banking.user.account;

import org.poo.banking.user.User;
import org.poo.banking.user.serviceplan.ServicePlan;
import org.poo.banking.user.serviceplan.StandardServicePlan;
import org.poo.banking.user.serviceplan.StudentServicePlan;
import org.poo.utils.Utils;

import java.util.Objects;

public final class AccountFactory {
    public static Account createAccount(final String accountType, String currency,
                                        double interestRate, User user) {
        ServicePlan servicePlan;
        if (Objects.equals(user.getOccupation(), "student")) {
            servicePlan = new StudentServicePlan();
        } else {
            servicePlan = new StandardServicePlan();
        }

        switch (accountType) {
            case "classic" -> {
                return new ClassicAccount(accountType, Utils.generateIBAN(),
                        currency, user, servicePlan);
            }
            case "savings" -> {
                return new SavingsAccount(accountType, Utils.generateIBAN(),
                        currency, interestRate, user, servicePlan);
            }
            default -> {
                // TODO: Throw error
                return null;
            }
        }


    }
}
