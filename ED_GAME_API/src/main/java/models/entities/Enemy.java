package models.entities;

import models.world.Room;

/**
 * Represents an enemy entity within the game.
 *
 * This class encapsulates the properties and behaviors of an enemy, including its name,
 * firepower, and the room it starts in. The chosen data structures are evaluated for their
 * suitability in representing these attributes.
 *
 */
public class Enemy implements EnemyInterface{
    private String name;
    private int firePower;
    private Room startingRoom;

    /**
     * Constructs an Enemy with a specified name, firepower, and starting room.
     *
     * @param name          the name of the enemy
     * @param firePower     the firepower level of the enemy
     * @param startingRoom  the room where the enemy starts
     */
    public Enemy(String name, int firePower, Room startingRoom) {
        this.name = name;
        this.firePower = firePower;
        this.startingRoom = startingRoom;
    }

    /**
     * Constructs an Enemy with a specified name and firepower.
     * The starting room is not set in this constructor and can be assigned later.
     *
     * @param name      the name of the enemy
     * @param firePower the firepower level of the enemy
     */
    public Enemy(String name, int firePower) {
        this.name = name;
        this.firePower = firePower;
    }

    /**
     * Retrieves the name of the enemy.
     *
     * @return the enemy'Simulation.s name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets or updates the name of the enemy.
     *
     * @param name the new name for the enemy
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the current firepower level of the enemy.
     *
     * @return the enemy'Simulation.s firepower
     */
    public int getFirePower() {
        return firePower;
    }

    /**
     * Sets or updates the firepower level of the enemy.
     *
     * @param firePower the new firepower value
     */
    public void setFirePower(int firePower) {
        this.firePower = firePower;
    }

    /**
     * Reduces the enemy'Simulation.s firepower by a specified damage amount.
     * Ensures that firepower does not drop below zero.
     *
     * @param damage the amount of damage to inflict
     */
    public void takeDamage(int damage) {
        this.firePower = Math.max(this.firePower - damage, 0);
    }

    /**
     * Assigns the starting room for the enemy.
     *
     * @param room the room where the enemy will start
     */
    public void setStartingRoom(Room room) {
        this.startingRoom = room;
    }

    /**
     * Retrieves the starting room of the enemy.
     *
     * @return the enemy'Simulation.s starting room
     */
    public Room getStartingRoom() {
        return startingRoom;
    }
}
