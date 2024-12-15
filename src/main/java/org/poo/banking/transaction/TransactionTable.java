package org.poo.banking.transaction;

import org.poo.banking.user.account.exception.InsufficientFundsException;

import java.util.ArrayList;
import java.util.List;

public class TransactionTable implements PaymentCollector {
    private final List<PaymentCollectee> collectees;
    private final PaymentReceiver receiver;
    private final double amount;
    private final String currency;

    public TransactionTable(List<PaymentCollectee> collectees, PaymentReceiver receiver, double amount, String currency) {
        this.collectees = collectees;
        this.receiver = receiver;
        this.amount = amount;
        this.currency = currency;
    }

    public TransactionTable(PaymentCollectee collectee, PaymentReceiver receiver, double amount, String currency) {
        this.collectees = new ArrayList<>();
        collectees.add(collectee);
        this.receiver = receiver;
        this.amount = amount;
        this.currency = currency;
    }

    @Override
    public void collect() throws InsufficientFundsException {
        // TODO: Updated logic by introducing a promise object returned by account. This object
        // should provide an interface for taxing the user once the payment can surely be done.
        double singleAmount = amount / collectees.size();
        for (int i = collectees.size() - 1; i >= 0; i--) {
            PaymentCollectee collectee = collectees.get(i);
            try {
                collectee.ask(singleAmount, currency);
            } catch (InsufficientFundsException e) {
                if (collectees.size() == 1) {
                    throw e;
                }
                for (int j = i + 1; j < collectees.size(); j++) {
                    collectees.get(j).giveBack(singleAmount, currency);
                }
                throw new InsufficientFundsException("Account "
                        + collectee.resolveId()
                        + " has insufficient funds for a split payment.");
            }
        }
        receiver.receive(amount, currency);
    }
}
