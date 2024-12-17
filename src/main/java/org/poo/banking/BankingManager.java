package org.poo.banking;

import lombok.Getter;
import lombok.Setter;
import org.poo.banking.currency.ForexGenie;
import org.poo.banking.data.BankingScotty;
import org.poo.banking.data.BankingScottyFriend;
import org.poo.banking.user.User;
import org.poo.mock.command.BankingQuerent;

import java.util.Optional;
import java.util.Set;

public final class BankingManager implements BankingScottyFriend {
    @Getter
    private final ForexGenie forexGenie = new ForexGenie();

    @Getter
    private final BankingQuerent querent = new BankingQuerent();

    @Getter
    @Setter
    private int time = 0;

    private final BankingScotty scotty = new BankingScotty();

    private static BankingManager instance;

    /**
     * Singleton instance getter.
     * @return BankingManager
     */
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

    /**
     * Add a user to the database with a feature key.
     * @param feature user specific key
     * @param user user object
     */
    @Override
    public void addUserByFeature(final String feature, final User user) {
        scotty.addUserByFeature(feature, user);
    }

    /**
     * Returns the user from the database with the provided feature key.
     * @param feature user specific key
     * @return Optional<User>
     */
    @Override
    public Optional<User> getUserByFeature(final String feature) {
        return scotty.getUserByFeature(feature);
    }

    /**
     * Removes a feature from the database and its associated value. Returns the associated user.
     * @param feature user specific key
     * @return Optional<User>
     */
    @Override
    public Optional<User> removeFeature(final String feature) {
        return scotty.removeFeature(feature);
    }

    /**
     * Returns a container with the users.
     * @return Set<User>
     */
    public Set<User> getUsers() {
        return scotty.getUsers();
    }
}
