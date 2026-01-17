package models.items;

/**
 * Represents a MedKit item in the game.
 *
 * MedKits are used to restore a player's health during the game. This class extends
 * the {@link Item} class, inheriting its basic properties and adding functionality
 * specific to MedKits, such as recovery points.
 */
public class MedKit extends Item {
    private int recoveryPoints;

    /**
     * Constructs a MedKit item with the specified name and recovery points.
     *
     * @param name           the name of the MedKit item
     * @param recoveryPoints the number of health points restored by the MedKit
     */
    public MedKit(String name, int recoveryPoints) {
        super(name);
        this.recoveryPoints = recoveryPoints;
    }

    /**
     * Retrieves the number of health points restored by the MedKit.
     *
     * @return the recovery points provided by the MedKit
     */
    public int getRecoveryPoints() {
        return recoveryPoints;
    }
}