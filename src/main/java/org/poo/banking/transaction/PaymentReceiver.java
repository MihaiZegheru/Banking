package org.poo.banking.transaction;

public interface PaymentReceiver {
    void receive(double amount, String currency);
}
