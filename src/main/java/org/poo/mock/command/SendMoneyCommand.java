package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Account;

import java.util.Optional;

public class SendMoneyCommand extends BankingCommand {
    private final String iban;
    private final double amount;
    private final String receiver;
    private final String description;
    private final int timestamp;

    public SendMoneyCommand(String command, String iban, double amount, String receiver,
                            String description, int timestamp) {
        super(command);
        this.iban = iban;
        this.amount = amount;
        this.receiver = receiver;
        this.description = description;
        this.timestamp = timestamp;
    }


    @Override
    public Optional<ObjectNode> execute() {
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
        Account senderAccount = senderAccountResult.get();

        String receiverIban = receiver;
        if (senderUser.getAliases().containsKey(receiver)) {
            receiverIban = senderUser.getAlias(receiver);
        }
        Optional<User> receiverUserResult =
                BankingManager.getInstance().getUserByFeature(receiverIban);
        if (receiverUserResult.isEmpty()) {
            // TODO: Report issue
            return Optional.empty();
        }
        User receiverUser = receiverUserResult.get();
        Optional<Account> receiverAccountResult = receiverUser.getAccountByIban(receiverIban);
        if (receiverAccountResult.isEmpty()) {
            // TODO: Report issue
            return Optional.empty();
        }
        Account receiverAccount = receiverAccountResult.get();

        senderAccount.transfer(receiverAccount, amount);
        return Optional.empty();
    }
}
