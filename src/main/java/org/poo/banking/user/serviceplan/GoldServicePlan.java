package org.poo.banking.user.serviceplan;

import org.poo.banking.BankingManager;
import org.poo.banking.currency.ForexGenie;
import org.poo.banking.transaction.PaymentCollectee;
import org.poo.banking.user.account.Account;

public final class GoldServicePlan extends ServicePlan {
    private final double commission = 0;

    private final double spendingCashback100 = 0.5 * 1e-2;
    private final double spendingCashback300 = 0.55 * 1e-2;
    private final double spendingCashback500 = 0.7 * 1e-2;

    private final double threshold100 = 100;
    private final double threshold300 = 300;
    private final double threshold500 = 500;

    @Override
    public void collectCommission(final double transactionAmount, final String currency,
                                  final PaymentCollectee collectee) {
        collectee.payCommission(transactionAmount * commission, currency);
        System.out.println(collectee.getAccount().getOwningUser().getEmail());
        System.out.println(transactionAmount);
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

    }
}
