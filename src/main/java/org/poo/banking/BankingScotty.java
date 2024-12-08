package org.poo.banking;

import lombok.Getter;
import org.poo.banking.user.User;

import java.util.*;

public class BankingScotty {
    @Getter
    Set<User> users = new LinkedHashSet<>();
    Map<String, User> featureToUser = new HashMap<>();

    public void addUserByFeature(String feature, User user) {
        users.add(user);
        if (featureToUser.containsKey(feature)) {
            // TODO: Change exception.
            return;
        }

        featureToUser.put(feature, user);
    }

    public Optional<User> removeFeature(String feature) {
        return Optional.ofNullable(featureToUser.remove(feature));
    }

    public Optional<User> getUserByFeature(String feature) {
        if (!featureToUser.containsKey(feature)) {
            return Optional.empty();
        }
        return Optional.ofNullable(featureToUser.get(feature));
    }

}
