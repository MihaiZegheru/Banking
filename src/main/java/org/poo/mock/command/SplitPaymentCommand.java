package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.transaction.PaymentCollectee;
import org.poo.banking.transaction.TransactionTable;
import org.poo.banking.transaction.ZeroPaymentReceiver;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Account;
import org.poo.banking.user.account.exception.InsufficientFundsException;
import org.poo.banking.user.tracking.TrackingNode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class SplitPaymentCommand extends BankingCommand {
    private final List<String> ibans;
    private final double amount;
    private final String currency;
    private final int timestamp;

    public SplitPaymentCommand(final String command, final List<String> ibans, final double amount,
                               final String currency, final int timestamp) {
        super(command);
        this.ibans = ibans;
        this.amount = amount;
        this.currency = currency;
        this.timestamp = timestamp;
    }

    @Override
    public Optional<ObjectNode> execute() {
        BankingManager.getInstance().setTime(timestamp);
        List<PaymentCollectee> collecteeAccounts = new ArrayList<>();
        List<Account> accounts = new ArrayList<>();
        for (String iban : ibans) {
            Optional<User> senderUserResult = BankingManager.getInstance().getUserByFeature(iban);
            if (senderUserResult.isEmpty()) {
                return Optional.empty();
            }
            User senderUser = senderUserResult.get();
            Optional<Account> senderAccountResult = senderUser.getAccountByIban(iban);
            if (senderAccountResult.isEmpty()) {
                return Optional.empty();
            }
            collecteeAccounts.add(senderAccountResult.get());
            accounts.add(senderAccountResult.get());
        }

        // TODO: Remove rounding
        DecimalFormat f = new DecimalFormat("##.00");
        TrackingNode.TrackingNodeBuilder trackingBuilder = new TrackingNode.TrackingNodeBuilder()
                .setTimestamp(timestamp).setAmount(amount / collecteeAccounts.size())
                .setCurrency(currency)
                .setInvolvedAccounts(ibans)
                .setDescription("Split payment of " + f.format(amount) + " " + currency);

        TransactionTable transaction = new TransactionTable(collecteeAccounts,
                new ZeroPaymentReceiver(), amount, currency);
        try {
            transaction.collect();
        } catch (InsufficientFundsException e) {
            trackingBuilder.setError(e.getMessage());
        } finally {
            for (Account account : accounts) {
                account.getOwningUser().getUserTracker().onTransaction(trackingBuilder
                        .setProducer(account)
                        .build());
            }
        }
        return Optional.empty();
    }
}
