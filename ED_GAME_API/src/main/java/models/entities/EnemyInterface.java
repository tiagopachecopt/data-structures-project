package models.entities;

import models.world.Room;

/**
 * Represents the interface for an enemy entity within the game.
 *
 * This interface defines the basic functionalities of an enemy, such as retrieving
 * and updating its name, firepower, and starting room. It also includes a method
 * for handling damage to the enemy.
 */
public interface EnemyInterface {

    /**
     * Retrieves the name of the enemy.
     *
     * @return the enemy's name
     */
    String getName();

    /**
     * Retrieves the firepower level of the enemy.
     *
     * @return the enemy's firepower
     */
    int getFirePower();

    /**
     * Retrieves the starting room of the enemy.
     *
     * @return the room where the enemy starts
     */
    Room getStartingRoom();

    /**
     * Reduces the enemy's firepower by a specified damage amount.
     * Ensures that the firepower does not drop below zero.
     *
     * @param damage the amount of damage to inflict on the enemy
     */
    void takeDamage(int damage);
}