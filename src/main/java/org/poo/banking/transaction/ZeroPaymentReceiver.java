package org.poo.banking.transaction;

/**
 * In case you would like to make a void payment. (Tests only)
 */
public class ZeroPaymentReceiver implements PaymentReceiver {
    @Override
    public void receive(final double amount, final String currency) { }
}
