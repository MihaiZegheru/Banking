package org.poo.banking.user.serviceplan;

import org.poo.banking.transaction.PaymentCollectee;
import org.poo.banking.user.account.Account;

public abstract class ServicePlan {

    public abstract void CollectCommission(double transactionAmount, String currency,
                                  PaymentCollectee collectee);

    public abstract void HandleCashbackForSpending(double transactionAmount, String currency,
                                                   PaymentCollectee collectee);

    public abstract void UpgradePlan(Account account, String newPlanType);
}
