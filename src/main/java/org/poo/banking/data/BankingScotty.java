package org.poo.banking.data;

import lombok.Getter;
import org.poo.banking.seller.Seller;
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

    private final Set<Seller> sellers = new LinkedHashSet<>();
    private final Map<String, Seller> featureToSeller = new HashMap<>();

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

    /**
     * Adds a seller with a feature key.
     * @param feature seller specific key
     * @param seller seller object
     */
    public void addSellerByFeature(final String feature, final Seller seller) {
        sellers.add(seller);
        if (featureToSeller.containsKey(feature)) {
            return;
        }
        featureToSeller.put(feature, seller);
    }

    /**
     * Returns the seller from the database with the provided feature key.
     * @param feature seller specific key
     * @return Optional<Seller>
     */
    public Optional<Seller> getSellerByFeature(final String feature) {
        if (!featureToSeller.containsKey(feature)) {
            return Optional.empty();
        }
        return Optional.ofNullable(featureToSeller.get(feature));
    }
}
