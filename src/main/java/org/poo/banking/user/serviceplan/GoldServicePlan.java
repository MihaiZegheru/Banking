package org.poo.banking.user.serviceplan;

import org.poo.banking.BankingManager;
import org.poo.banking.currency.ForexGenie;
import org.poo.banking.transaction.PaymentCollectee;
import org.poo.banking.user.account.Account;

import java.util.Objects;

public class GoldServicePlan extends ServicePlan {
    protected final double commission = 0;

    protected final double spendingCashback100 = 0.5 * 1e-2;
    protected final double spendingCashback300 = 0.55 * 1e-2;
    protected final double spendingCashback500 = 0.7 * 1e-2;

    protected final double toSilver = 100;
    protected final double toGold = 250;

    @Override
    public void CollectCommission(double transactionAmount, String currency,
                                  PaymentCollectee collectee) {
        collectee.payCommission(transactionAmount * commission, currency);
        System.out.println(collectee.getAccount().getOwningUser().getEmail());
        System.out.println(transactionAmount);
    }

    @Override
    public void HandleCashbackForSpending(double transactionAmount, String currency,
                                          PaymentCollectee collectee) {
        ForexGenie forexGenie = BankingManager.getInstance().getForexGenie();
        Account account = collectee.getAccount();

        double convertedCashbackAmount = forexGenie.queryRate(currency, "RON",
                transactionAmount);
        double spendingAmount = account.getSpending();
        double newAmount = spendingAmount + convertedCashbackAmount;

        if (spendingAmount < 500 && newAmount >= 500) {
            account.setBalance(account.getBalance() + spendingCashback500 * transactionAmount);
        } else if (spendingAmount < 300 && newAmount >= 300) {
            account.setBalance(account.getBalance() + spendingCashback300 * transactionAmount);
        } else if (spendingAmount < 100 && newAmount >= 100) {
            account.setBalance(account.getBalance() + spendingCashback100 * transactionAmount);
        }
        account.setSpending(account.getSpending() + convertedCashbackAmount);
    }

    @Override
    public void UpgradePlan(Account account, String newPlanType) {

    }
}
