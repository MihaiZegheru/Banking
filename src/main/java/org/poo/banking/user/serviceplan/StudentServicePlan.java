package org.poo.banking.user.serviceplan;

import org.poo.banking.BankingManager;
import org.poo.banking.currency.ForexGenie;
import org.poo.banking.transaction.PaymentCollectee;
import org.poo.banking.user.account.Account;

import java.util.Objects;

public class StudentServicePlan extends ServicePlan {
    protected final double commission = 0;

    protected final double spendingCashback100 = 0.1 * 1e-2;
    protected final double spendingCashback300 = 0.2 * 1e-2;
    protected final double spendingCashback500 = 0.25 * 1e-2;

    protected final double toSilver = 100;
    protected final double toGold = 350;

    @Override
    public void CollectCommission(double transactionAmount, String currency,
                                  PaymentCollectee collectee) {
        collectee.payCommission(transactionAmount * commission, currency);
    }

    @Override
    public void HandleCashbackForSpending(double transactionAmount, String currency, PaymentCollectee collectee) {
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
        ForexGenie forexGenie = BankingManager.getInstance().getForexGenie();
        if (Objects.equals(newPlanType, "silver")) {
            double price = forexGenie.queryRate("RON", account.getCurrency(), toSilver);
            if (account.getBalance() - price < 0) {
                return;
            }
            account.setBalance(account.getBalance() - price);
            account.getOwningUser().setServicePlan(new SilverServicePlan());
        } else if (Objects.equals(newPlanType, "gold")) {
            double price = forexGenie.queryRate("RON", account.getCurrency(), toGold);
            if (account.getBalance() - price < 0) {
                return;
            }
            account.setBalance(account.getBalance() - price);
            account.getOwningUser().setServicePlan(new GoldServicePlan());
        }
    }
}
