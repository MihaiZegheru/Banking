package org.poo.banking;

import lombok.Getter;
import lombok.Setter;
import org.poo.banking.currency.ForexGenie;
import org.poo.banking.user.User;
import org.poo.mock.command.BankingQuerent;

import java.util.*;

public class BankingManager implements BankingScottyFriend {
    private static BankingManager instance;

    private final BankingScotty scotty = new BankingScotty();
    @Getter
    private final ForexGenie forexGenie = new ForexGenie();
    @Getter
    private final BankingQuerent querent = new BankingQuerent();
    @Getter
    @Setter
    private int time = 0;

    public static BankingManager getInstance() {
        if (instance == null) {
            instance = new BankingManager();
        }
        return instance;
    }

    /**
     * Nullifies the Singleton instance.
     * <p>
     * This function should only be used when finishing a mock test.
     */
    public void resetInstance() {
        instance = null;
    }

    @Override
    public void addUserByFeature(String feature, User user) {
        scotty.addUserByFeature(feature, user);
    }

    @Override
    public Optional<User> removeFeature(String feature) {
        return scotty.removeFeature(feature);
    }

    @Override
    public Optional<User> getUserByFeature(String feature) {
        return scotty.getUserByFeature(feature);
    }

    public Set<User> getUsers() {
        return scotty.getUsers();
    }
}
