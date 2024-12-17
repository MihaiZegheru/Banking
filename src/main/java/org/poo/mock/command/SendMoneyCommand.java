package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.currency.ForexGenie;
import org.poo.banking.transaction.TransactionTable;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Account;
import org.poo.banking.user.account.exception.InsufficientFundsException;
import org.poo.banking.user.tracking.TrackingNode;

import java.util.Optional;

public final class SendMoneyCommand extends BankingCommand {
    private final String iban;
    private final double amount;
    private final String receiver;
    private final String description;
    private final int timestamp;

    public SendMoneyCommand(final String command, final String iban, final double amount,
                            final String receiver, final String description, final int timestamp) {
        super(command);
        this.iban = iban;
        this.amount = amount;
        this.receiver = receiver;
        this.description = description;
        this.timestamp = timestamp;
    }

    @Override
    public Optional<ObjectNode> execute() {
        BankingManager.getInstance().setTime(timestamp);
        Optional<User> senderUserResult = BankingManager.getInstance().getUserByFeature(iban);
        if (senderUserResult.isEmpty()) {
            return Optional.empty();
        }
        User senderUser = senderUserResult.get();
        Optional<Account> senderAccountResult = senderUser.getAccountByIban(iban);
        if (senderAccountResult.isEmpty()) {
            return Optional.empty();
        }
        Account senderAccount = senderAccountResult.get();

        String receiverIban = receiver;
        if (senderUser.getAliases().containsKey(receiver)) {
            receiverIban = senderUser.getAlias(receiver);
        }
        Optional<User> receiverUserResult =
                BankingManager.getInstance().getUserByFeature(receiverIban);
        if (receiverUserResult.isEmpty()) {
            return Optional.empty();
        }
        User receiverUser = receiverUserResult.get();
        Optional<Account> receiverAccountResult = receiverUser.getAccountByIban(receiverIban);
        if (receiverAccountResult.isEmpty()) {
            return Optional.empty();
        }
        Account receiverAccount = receiverAccountResult.get();

        TrackingNode.TrackingNodeBuilder senderTrackingBuilder =
                new TrackingNode.TrackingNodeBuilder()
                        .setTimestamp(timestamp)
                        .setProducer(senderAccount);

        TrackingNode.TrackingNodeBuilder receiverTrackingBuilder =
                new TrackingNode.TrackingNodeBuilder()
                        .setTimestamp(timestamp)
                        .setProducer(receiverAccount);

        TransactionTable transaction = new TransactionTable(senderAccount, receiverAccount, amount,
                senderAccount.getCurrency());
        try {
            transaction.collect();
            senderUser.getUserTracker().onTransaction(senderTrackingBuilder
                    .setAmountLiteral(amount + " " + senderAccount.getCurrency())
                    .setDescription(description)
                    .setSenderIban(iban)
                    .setReceiverIban(receiverIban)
                    .setTransferType("sent")
                    .setProducer(senderAccount)
                    .build());

            ForexGenie genie = BankingManager.getInstance().getForexGenie();
            receiverUser.getUserTracker().onTransaction(receiverTrackingBuilder
                    .setAmountLiteral(
                            genie.queryRate(senderAccount.getCurrency(),
                                    receiverAccount.getCurrency(), amount) + " "
                                    + receiverAccount.getCurrency())
                    .setDescription(description)
                    .setSenderIban(iban)
                    .setReceiverIban(receiverIban)
                    .setTransferType("received")
                    .setProducer(receiverAccount)
                    .build());
        } catch (InsufficientFundsException e) {
            senderTrackingBuilder.setDescription(e.getMessage());
            senderUser.getUserTracker().onTransaction(senderTrackingBuilder.build());
        }
        return Optional.empty();
    }
}
