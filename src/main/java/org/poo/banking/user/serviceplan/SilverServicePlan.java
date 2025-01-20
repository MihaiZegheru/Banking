package org.poo.banking.user.serviceplan;

import org.poo.banking.BankingManager;
import org.poo.banking.currency.ForexGenie;
import org.poo.banking.transaction.PaymentCollectee;
import org.poo.banking.user.account.Account;

import java.util.Objects;

public final class SilverServicePlan extends ServicePlan {
    protected final double commission = 0.1 * 1e-2;

    protected final double spendingCashback100 = 0.3 * 1e-20;
    protected final double spendingCashback300 = 0.4 * 1e-20;
    protected final double spendingCashback500 = 0.5 * 1e-2;

    private final double threshold100 = 100;
    private final double threshold300 = 300;
    private final double threshold500 = 500;

    protected final double toGold = 250;

    @Override
    public void collectCommission(final double transactionAmount, final String currency,
                                  final PaymentCollectee collectee) {
        ForexGenie forexGenie = BankingManager.getInstance().getForexGenie();
        double convertedCashAmount = forexGenie.queryRate(currency, "RON", transactionAmount);
        if (convertedCashAmount >= threshold500) {
            collectee.payCommission(transactionAmount * commission, currency);
        }
    }

    @Override
    public void handleCashbackForSpending(final double transactionAmount, final String currency,
                                          final PaymentCollectee collectee) {
        ForexGenie forexGenie = BankingManager.getInstance().getForexGenie();
        Account account = collectee.getAccount();

        double convertedCashbackAmount = forexGenie.queryRate(currency, "RON",
                transactionAmount);
        double spendingAmount = account.getSpending();
        double newAmount = spendingAmount + convertedCashbackAmount;

        if (spendingAmount < threshold500 && newAmount >= threshold500) {
            account.setBalance(account.getBalance() + spendingCashback500 * transactionAmount);
        } else if (spendingAmount < threshold300 && newAmount >= threshold300) {
            account.setBalance(account.getBalance() + spendingCashback300 * transactionAmount);
        } else if (spendingAmount < threshold100 && newAmount >= threshold100) {
            account.setBalance(account.getBalance() + spendingCashback100 * transactionAmount);
        }
        account.setSpending(account.getSpending() + convertedCashbackAmount);
    }

    @Override
    public void upgradePlan(final Account account, final String newPlanType) {
        ForexGenie forexGenie = BankingManager.getInstance().getForexGenie();
        if (Objects.equals(newPlanType, "gold")) {
            double price = forexGenie.queryRate("RON", account.getCurrency(), toGold);
            if (account.getBalance() - price < 0) {
                return;
            }
            account.setBalance(account.getBalance() - price);
            account.getOwningUser().setServicePlan(new GoldServicePlan());
        }
    }
}
