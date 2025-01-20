package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.transaction.TransactionTable;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Account;
import org.poo.banking.user.account.exception.InsufficientFundsException;
import org.poo.banking.user.tracking.TrackingNode;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public final class WithdrawSavingsCommand extends BankingCommand {
    private final double amount;
    private final String iban;
    private final String currency;
    private final int timestamp;

    private final int ageLimit = 21;

    public WithdrawSavingsCommand(final String command, final double amount, final String iban,
                                  final String currency, final int timestamp) {
        super(command);
        this.amount = amount;
        this.iban = iban;
        this.currency = currency;
        this.timestamp = timestamp;
    }

    @Override
    public Optional<ObjectNode> execute() {
        BankingManager.getInstance().setTime(timestamp);
        Optional<User> userResult = BankingManager.getInstance().getUserByFeature(iban);
        if (userResult.isEmpty()) {
            return Optional.empty();
        }
        User user = userResult.get();

        Optional<Account> savingsAccountResult = user.getAccountByIban(iban);
        if (savingsAccountResult.isEmpty()) {
            return Optional.empty();
        }
        Account savingsAccount = savingsAccountResult.get();

        TrackingNode.TrackingNodeBuilder trackingBuilder =
                new TrackingNode.TrackingNodeBuilder()
                        .setTimestamp(timestamp)
                        .setProducer(savingsAccount);

        Account destinationAccount = null;
        for (Account account : user.getAccounts()) {
            if (!Objects.equals(account.getCurrency(), currency)
                    || !Objects.equals(account.getType(), "classic")) {
                continue;
            }
            destinationAccount = account;
        }

        if (destinationAccount == null) {
            user.getUserTracker().onTransaction(trackingBuilder
                    .setDescription("You do not have a classic account.")
                    .build());
            return Optional.empty();
        }
        if (java.time.temporal.ChronoUnit.YEARS.between(LocalDate.now(),
                LocalDate.parse(user.getBirthDate())) < ageLimit) {
            user.getUserTracker().onTransaction(trackingBuilder
                    .setDescription("You don't have the minimum age required.")
                    .build());
            return Optional.empty();
        }

        TransactionTable transaction = new TransactionTable(savingsAccount, destinationAccount,
                amount, savingsAccount.getCurrency());
        try {
            transaction.collect();
        } catch (InsufficientFundsException e) {

        }
        return Optional.empty();
    }
}
