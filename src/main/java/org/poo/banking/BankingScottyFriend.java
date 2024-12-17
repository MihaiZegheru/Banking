package org.poo.banking;

import org.poo.banking.user.User;

import java.util.Collection;
import java.util.Optional;

/**
 * This interface should be implemented for using BankingScotty.
 */
interface BankingScottyFriend {
    void addUserByFeature(String feature, User user);
    Optional<User> removeFeature(String feature);
    Optional<User> getUserByFeature(String feature);
    Collection<User> getUsers();
}
