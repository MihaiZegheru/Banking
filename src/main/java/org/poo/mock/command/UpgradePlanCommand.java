package org.poo.mock.command;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.BankingManager;
import org.poo.banking.user.User;
import org.poo.banking.user.account.Account;
import org.poo.banking.user.tracking.TrackingNode;

import java.util.Optional;

public final class UpgradePlanCommand extends BankingCommand {
    private final String newPlanType;
    private final String iban;
    private final int timestamp;

    public UpgradePlanCommand(final String command, final String newPlanType, final String iban,
                              final int timestamp) {
        super(command);
        this.newPlanType = newPlanType;
        this.iban = iban;
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
        Optional<Account> accountResult = user.getAccountByIban(iban);
        if (accountResult.isEmpty()) {
            return Optional.empty();
        }
        Account account = accountResult.get();

        account.getOwningUser().getServicePlan().upgradePlan(account, newPlanType);
        user.getUserTracker().onPlanUpgraded(new TrackingNode.TrackingNodeBuilder()
                .setDescription("Upgrade plan")
                .setAccountIban(iban)
                .setNewPlanType(newPlanType)
                .setTimestamp(timestamp)
                .build());
        return Optional.empty();
    }
}
