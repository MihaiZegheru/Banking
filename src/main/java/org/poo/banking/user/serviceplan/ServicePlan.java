package org.poo.banking.user.serviceplan;

import org.poo.banking.transaction.PaymentCollectee;
import org.poo.banking.user.account.Account;

public abstract class ServicePlan {
    /**
     * Implements commission collection logic.
     * @param transactionAmount amount of transaction
     * @param currency currency of transaction
     * @param collectee collectee
     */
    public abstract void collectCommission(double transactionAmount, String currency,
                                           PaymentCollectee collectee);

    /**
     * Implements cashback logic.
     * @param transactionAmount amount of transaction
     * @param currency currency of transaction
     * @param collectee collectee
     */
    public abstract void handleCashbackForSpending(double transactionAmount, String currency,
                                                   PaymentCollectee collectee);

    /**
     * Changes plan to requested.
     * @param account
     * @param newPlanType
     */
    public abstract void upgradePlan(Account account, String newPlanType);
}
