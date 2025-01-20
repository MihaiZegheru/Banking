package org.poo.banking.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.poo.banking.user.account.Account;

/**
 * Should be implemented by entities that can be queried for payment.
 */
public interface PaymentCollectee {
    /**
     * Asks for amount from callee.
     */
    void ask(double amount, String currency);

    /**
     * Asks for amount from callee in form of a commission.
     */
    void payCommission(double amount, String currency);

    /**
     * Returns asked amount to callee.
     */
    void giveBack(double amount, String currency);

    /**
     * Returns some personal identification of implementation choice.
     */
    String resolveId();

    /**
     * Returns account.
     */
    @JsonIgnore
    Account getAccount();
}
