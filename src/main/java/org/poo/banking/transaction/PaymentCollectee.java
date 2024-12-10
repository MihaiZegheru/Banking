package org.poo.banking.transaction;

public interface PaymentCollectee {
    void ask(double amount, String currency);
}
