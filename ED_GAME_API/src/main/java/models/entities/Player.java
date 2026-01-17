package models.entities;

import dataStructures.exceptions.EmptyCollectionException;
import dataStructures.stacks.LinkedStack;
import models.items.MedKit;

import java.io.Serializable;

/**
 * Represents a player entity within the game.
 *
 * This class encapsulates the properties and behaviors of a player, including their name,
 * firepower, health, and inventory of med kits.
 *
 */
public class Player implements PlayerInterface {
    private String name;
    private int firePower;
    private int health;
    private LinkedStack<MedKit> medKits;
    private int maxMedKits;
    private boolean targetRoomVisited;

    /**
     * Constructs a Player with the specified name, firepower, and health.
     * Initializes the med kits inventory as an empty stack.
     *
     * @param name      the name of the player
     * @param firePower the firepower level of the player
     * @param health    the health points of the player
     */
    public Player(String name, int firePower, int health, int maxKits) {
        this.name = name;
        this.firePower = firePower;
        this.health = health;
        this.maxMedKits = maxKits;
        this.medKits = new LinkedStack<>();
    }

    /**
     * Retrieves the name of the player.
     *
     * @return the player'Simulation.s name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets or updates the name of the player.
     *
     * @param name the new name for the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the current firepower level of the player.
     *
     * @return the player'Simulation.s firepower
     */
    public int getFirePower() {
        return firePower;
    }

    /**
     * Sets or updates the firepower level of the player.
     *
     * @param firePower the new firepower value
     */
    public void setFirePower(int firePower) {
        this.firePower = firePower;
    }

    /**
     * Retrieves the current health points of the player.
     *
     * @return the player'Simulation.s health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets or updates the health points of the player.
     *
     * @param health the new health value
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Retrieves the stack of med kits the player possesses.
     *
     * @return the player'Simulation.s med kits stack
     */
    public LinkedStack<MedKit> getMedKits() {
        return medKits;
    }

    public void setMedKits(LinkedStack<MedKit> medKits) {
        this.medKits = medKits;
    }

    /**
     * Adds a med kit to the player'Simulation.s inventory.
     *
     * @param medKit the med kit to be added
     */
    public void pickUpMedKit(MedKit medKit) {
        medKits.push(medKit);
    }

    public int getMaxMedKits() {
        return maxMedKits;
    }

    public void setMaxMedKits(int maxMedKits) {
        this.maxMedKits = maxMedKits;
    }

    /**
     * Applies damage to the player by reducing their health points.
     *
     * @param damage the amount of damage to inflict
     */
    public void applyDamage(int damage) {
        this.health -= damage;
    }

    /**
     * Uses the most recently acquired med kit to restore health.
     * Ensures that health does not exceed the maximum limit of 100.
     *
     * @throws EmptyCollectionException if there are no med kits available
     */
    public void useMedKit() throws EmptyCollectionException {
        if (!medKits.isEmpty()) {
            MedKit medKit = medKits.pop();
            this.health = Math.min(100, this.health + medKit.getRecoveryPoints());
        }
    }

    /**
     * Checks if the player has been defeated (health is zero or below).
     *
     * @return {@code true} if the player is defeated, {@code false} otherwise
     */
    public boolean isDefeated() {
        return health <= 0;
    }

    /**
     * Checks if the target room has been visited by the player.
     *
     * @return {@code true} if the target room has been visited, {@code false} otherwise
     */
    public boolean isTargetRoomVisited() {
        return targetRoomVisited;
    }

    /**
     * Sets the status of whether the target room has been visited.
     *
     * @param targetRoomVisited {@code true} if the target room has been visited, {@code false} otherwise
     */
    public void setTargetRoomVisited(boolean targetRoomVisited) {
        this.targetRoomVisited = targetRoomVisited;
    }
}
