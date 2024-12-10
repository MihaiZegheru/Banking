package org.poo.banking.transaction;

import org.poo.banking.user.account.exception.InsufficientFundsException;

import java.util.ArrayList;
import java.util.List;

public class Transaction implements PaymentCollector {
    private final List<PaymentCollectee> collectees;
    private final PaymentReceiver receiver;
    private final double amount;
    private final String currency;

    public Transaction(List<PaymentCollectee> collectees, PaymentReceiver receiver, double amount, String currency) {
        this.collectees = collectees;
        this.receiver = receiver;
        this.amount = amount;
        this.currency = currency;
    }

    public Transaction(PaymentCollectee collectee, PaymentReceiver receiver, double amount, String currency) {
        this.collectees = new ArrayList<>();
        collectees.add(collectee);
        this.receiver = receiver;
        this.amount = amount;
        this.currency = currency;
    }

    @Override
    public void collect() throws InsufficientFundsException {
        double singleAmount = amount / collectees.size();
        for (PaymentCollectee collectee : collectees) {
            collectee.ask(singleAmount, currency);
        }
        receiver.receive(amount, currency);
    }
}
