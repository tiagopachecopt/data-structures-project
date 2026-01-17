package models.items;

/**
 * Represents an abstract item within the game.
 *
 * This class serves as the base for all game items, encapsulating the shared
 * property of a name. Specific item types should extend this class and provide
 * additional functionality as needed.
 */
public abstract class Item {
    private String name;

    /**
     * Constructs an item with the specified name.
     *
     * @param name the name of the item
     */
    public Item(String name) {
        this.name = name;
    }

    /**
     * Retrieves the name of the item.
     *
     * @return the item's name
     */
    public String getName() {
        return name;
    }
}