package models.world;

import dataStructures.exceptions.EmptyCollectionException;
import dataStructures.lists.DoubleUnorderedLinkedList;
import models.entities.Enemy;
import models.entities.Player;
import models.items.Item;
import models.items.Kevlar;
import models.items.MedKit;

/**
 * Represents a room within the game world.
 *
 * This class encapsulates the properties and behaviors of a room, including its name,
 * contained items, present enemies, and status flags indicating whether it is a target
 * room or has an existing entry.
 */
public class Room {
    private String name;
    private DoubleUnorderedLinkedList<Item> items;
    private DoubleUnorderedLinkedList<Enemy> enemies;
    private boolean target;
    private boolean isExistEntry;

    /**
     * Constructs a Room with the specified name.
     * Initializes empty lists for items and enemies.
     *
     * @param name the name of the room
     */
    public Room(String name) {
        this.name = name;
        this.items = new DoubleUnorderedLinkedList<>();
        this.enemies = new DoubleUnorderedLinkedList<>();
        this.target = false;
        this.isExistEntry = false;
    }

    /**
     * Constructs a Room with the specified name and entry status.
     * Initializes empty lists for items and enemies.
     *
     * @param name         the name of the room
     * @param exitEntry    indicates whether the room has an existing entry
     */
    public Room(String name, boolean exitEntry) {
        this.name = name;
        this.items = new DoubleUnorderedLinkedList<>();
        this.enemies = new DoubleUnorderedLinkedList<>();
        this.isExistEntry = exitEntry;
    }

    /**
     * Checks if the room has an existing entry.
     *
     * @return {@code true} if the room has an existing entry, {@code false} otherwise
     */
    public boolean isExistEntry() {
        return isExistEntry;
    }

    /**
     * Sets the entry status of the room.
     *
     * @param existEntry {@code true} to indicate the room has an existing entry, {@code false} otherwise
     */
    public void setExistEntry(boolean existEntry) {
        isExistEntry = existEntry;
    }

    /**
     * Retrieves the name of the room.
     *
     * @return the room'Simulation.s name
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the list of items present in the room.
     *
     * @return the items list
     */
    public DoubleUnorderedLinkedList<Item> getItems() {
        return items;
    }

    /**
     * Adds an item to the room'Simulation.s inventory.
     *
     * @param item the item to be added
     */
    public void addItem(Item item) {
        items.addToRear(item);
    }

    /**
     * Retrieves the list of enemies present in the room.
     *
     * @return the enemies list
     */
    public DoubleUnorderedLinkedList<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Adds an enemy to the room.
     *
     * @param enemy the enemy to be added
     */
    public void addEnemy(Enemy enemy) {
        enemies.addToRear(enemy);
    }

    /**
     * Calculates the total Kevlar points available in the room.
     *
     * @return the sum of extra Kevlar points from all Kevlar items in the room
     */
    public int getTotalKevlarPoints() {
        int totalKevlar = 0;
        for (Item item : items) {
            if (item instanceof Kevlar) {
                totalKevlar += ((Kevlar) item).getExtraPoints();
            }
        }
        return totalKevlar;
    }

    /**
     * Checks if the room is designated as a target.
     *
     * @return {@code true} if the room is a target, {@code false} otherwise
     */
    public boolean isTarget() {
        return target;
    }

    /**
     * Sets the target status of the room.
     *
     * @param target {@code true} to designate the room as a target, {@code false} otherwise
     */
    public void setTarget(boolean target) {
        this.target = target;
    }

    /**
     * Checks if the room contains any med kits.
     *
     * @return {@code true} if at least one med kit is present, {@code false} otherwise
     */
    public boolean hasMedKit() {
        for (Item item : items) {
            if (item instanceof MedKit) {
                return true;
            }
        }
        return false;
    }

    /**
     * Applies the effects of items in the room to the player and removes them from the room.
     *
     * This method creates a temporary list to safely store items to be removed from the room during
     * iteration. Direct modification of the items list during iteration can lead to skipped
     * elements or invalid iterator states.
     *
     * @param player the player receiving the effects of the room Simulation items
     * @throws EmptyCollectionException if an operation on an empty collection fails
     */
    public void applyRoomEffects(Player player) throws EmptyCollectionException {
        DoubleUnorderedLinkedList<Item> itemsToRemove = new DoubleUnorderedLinkedList<>();

        for (Item item : items) {
            if (item instanceof Kevlar) {
                player.applyDamage(-((Kevlar) item).getExtraPoints());
                itemsToRemove.addToRear(item);
            } else if (item instanceof MedKit) {
                if(player.getMedKits().size() < player.getMaxMedKits()) {
                    player.pickUpMedKit((MedKit) item);
                    itemsToRemove.addToRear(item);
                }else{
                    System.out.println("Max medkits reached");
                }
            }
        }

        for (Item item : itemsToRemove) {
            items.remove(item);
        }
    }
}
