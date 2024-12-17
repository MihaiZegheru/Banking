package org.poo.banking.transaction;

public class ZeroPaymentReceiver implements PaymentReceiver {
    @Override
    public void receive(final double amount, final String currency) { }
}
