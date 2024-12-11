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

public class SplitPaymentCommand extends BankingCommand {
    private final List<String> ibans;
    private final double amount;
    private final String currency;
    private final int timestamp;

    public SplitPaymentCommand(String command, List<String> ibans, double amount, String currency,
                               int timestamp) {
        super(command);
        this.ibans = ibans;
        this.amount = amount;
        this.currency = currency;
        this.timestamp = timestamp;
    }

    @Override
    public Optional<ObjectNode> execute() {
        List<User> users = new ArrayList<>();
        List<PaymentCollectee> collecteeAccounts = new ArrayList<>();
        for (String iban : ibans) {
            Optional<User> senderUserResult = BankingManager.getInstance().getUserByFeature(iban);
            if (senderUserResult.isEmpty()) {
                // TODO: Report issue
                return Optional.empty();
            }
            User senderUser = senderUserResult.get();
            Optional<Account> senderAccountResult = senderUser.getAccountByIban(iban);
            if (senderAccountResult.isEmpty()) {
                // TODO: Report issue
                return Optional.empty();
            }
            collecteeAccounts.add(senderAccountResult.get());
            users.add(senderUser);
        }

        TrackingNode.TrackingNodeBuilder trackingBuilder = new TrackingNode.TrackingNodeBuilder()
                .setTimestamp(timestamp);

        TransactionTable transaction = new TransactionTable(collecteeAccounts, new ZeroPaymentReceiver(),
                amount, currency);
        try {
            transaction.collect();
            // TODO: Remove rounding
            DecimalFormat f = new DecimalFormat("##.00");
            // TODO: Add tracking for account.
            trackingBuilder.setAmount(amount / collecteeAccounts.size())
                    .setCurrency(currency)
                    .setDescription("Split payment of " + f.format(amount) + " " + currency)
                    .setInvolvedAccounts(ibans);
        } catch (InsufficientFundsException e) {
            trackingBuilder.setDescription(e.getMessage());
        } finally {
            for (User user : users) {
                user.getUserTracker().OnTransaction(trackingBuilder.build());
            }
        }
        return Optional.empty();
    }
}
