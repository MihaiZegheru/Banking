package org.poo.banking.user.serviceplan;

import org.poo.banking.transaction.PaymentCollectee;
import org.poo.banking.user.account.Account;

public abstract class ServicePlan {
    protected double commission = 0;

    public void CollectCommission(double transactionAmount, String currency,
                                  PaymentCollectee collectee) {
        collectee.payCommission(transactionAmount * commission, currency);
    }

    public abstract void HandleCashbackForSpending(double transactionAmount, String currency,
                                                   PaymentCollectee collectee);

    public abstract void UpgradePlan(Account account, String newPlanType);
}
