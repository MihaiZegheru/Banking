package org.poo.fileio;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public final class CommandInput {
    private String command;
    private String name;
    private int id;
    private String cashbackStrategy;
    private String firstName;
    private String lastName;
    private String email;
    private String birthDate;
    private String occupation;
    private String account;
    private String newPlanType;
    private String role;
    private String currency;
    private String target;
    private String description;
    private String cardNumber;
    private String commerciant;
    private String receiver;
    private String alias;
    private String accountType;
    private String splitPaymentType;
    private String type;
    private String location;
    private int timestamp;
    private int startTimestamp;
    private int endTimestamp;
    private double interestRate;
    private double spendingLimit;
    private double depositLimit;
    private double amount;
    private double minBalance;
    private String fromCurrency;
    private String toCurrency;
    private List<String> accounts;
    private List<Double> amountForUsers;
}
