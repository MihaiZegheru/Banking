package org.poo.banking.data;

import lombok.Getter;
import org.poo.banking.user.User;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Optional;

/**
 * This class should only be accessed by implementing BankingScottyFriend interface.
 */
public final class BankingScotty {
    @Getter
    private final Set<User> users = new LinkedHashSet<>();

    private final Map<String, User> featureToUser = new HashMap<>();

    /**
     * Add a user with a feature key.
     * @param feature user specific key
     * @param user user object
     */
    public void addUserByFeature(final String feature, final User user) {
        users.add(user);
        if (featureToUser.containsKey(feature)) {
            return;
        }
        featureToUser.put(feature, user);
    }

    /**
     * Returns the use with the provided feature key.
     * @param feature user specific key
     * @return Optional<User>
     */
    public Optional<User> getUserByFeature(final String feature) {
        if (!featureToUser.containsKey(feature)) {
            return Optional.empty();
        }
        return Optional.ofNullable(featureToUser.get(feature));
    }

    /**
     * Removes a feature and its associated value. Returns the associated user.
     * @param feature user specific key
     * @return Optional<User>
     */
    public Optional<User> removeFeature(final String feature) {
        return Optional.ofNullable(featureToUser.remove(feature));
    }
}
