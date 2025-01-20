package org.poo.banking.data;

import org.poo.banking.seller.Seller;
import org.poo.banking.user.User;

import java.util.Collection;
import java.util.Optional;

/**
 * This interface should be implemented for using BankingScotty.
 */
public interface BankingScottyFriend {
    void addUserByFeature(String feature, User user);
    Optional<User> removeFeature(String feature);
    Optional<User> getUserByFeature(String feature);
    Collection<User> getUsers();

    void addSellerByFeature(String feature, Seller seller);
    public Optional<Seller> getSellerByFeature(final String feature);
}
