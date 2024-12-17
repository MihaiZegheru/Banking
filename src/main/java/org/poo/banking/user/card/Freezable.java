package org.poo.banking.user.card;

/**
 * Defines freezable entities.
 */
public interface Freezable {
    /**
     * Freezes the entity. Should stop being able to perform any banking operations.
     */
    void onFrozen();
}
