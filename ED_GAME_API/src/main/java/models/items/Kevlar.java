package models.items;

/**
 * Represents a Kevlar item in the game.
 *
 * Kevlar provides additional protection points to the player. This class extends
 * the {@link Item} class, inheriting its basic properties and adding functionality
 * specific to Kevlar, such as extra protection points.
 */
public class Kevlar extends Item {
    private int extraPoints;

    /**
     * Constructs a Kevlar item with the specified name and extra protection points.
     *
     * @param name        the name of the Kevlar item
     * @param extraPoints the additional protection points provided by the Kevlar
     */
    public Kevlar(String name, int extraPoints) {
        super(name);
        this.extraPoints = extraPoints;
    }

    /**
     * Retrieves the extra protection points provided by the Kevlar.
     *
     * @return the extra protection points
     */
    public int getExtraPoints() {
        return extraPoints;
    }
}