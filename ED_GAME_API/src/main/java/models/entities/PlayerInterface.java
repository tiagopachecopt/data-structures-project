package models.entities;

import dataStructures.exceptions.EmptyCollectionException;
import models.items.MedKit;

/**
 * Represents the interface for a player entity within the game.
 *
 * This interface defines the basic functionalities and attributes of a player,
 * such as retrieving their name, firepower, and health status, as well as
 * managing their interactions with medkits and game events.
 */
public interface PlayerInterface {

    /**
     * Retrieves the name of the player.
     *
     * @return the player's name
     */
    String getName();

    /**
     * Retrieves the firepower level of the player.
     *
     * @return the player's firepower
     */
    int getFirePower();

    /**
     * Retrieves the current health of the player.
     *
     * @return the player's health
     */
    int getHealth();

    /**
     * Determines if the player has been defeated (i.e., their health is zero or less).
     *
     * @return {@code true} if the player is defeated, {@code false} otherwise
     */
    boolean isDefeated();

    /**
     * Checks whether the player has visited the target room in the game.
     *
     * @return {@code true} if the player has visited the target room, {@code false} otherwise
     */
    boolean isTargetRoomVisited();

    /**
     * Applies damage to the player, reducing their health by the specified amount.
     * Ensures that the player's health does not drop below zero.
     *
     * @param damage the amount of damage to inflict on the player
     */
    void applyDamage(int damage);

    /**
     * Uses a medkit to restore the player's health.
     * This method removes a medkit from the player's inventory and applies its healing effects.
     *
     * @throws EmptyCollectionException if no medkits are available in the player's inventory
     */
    void useMedKit() throws EmptyCollectionException;

    /**
     * Adds a medkit to the player's inventory.
     *
     * @param medKit the medkit to add to the player's inventory
     */
    void pickUpMedKit(MedKit medKit);
}