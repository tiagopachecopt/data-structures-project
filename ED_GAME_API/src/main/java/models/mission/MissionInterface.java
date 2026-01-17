package models.mission;

import models.world.GameNetwork;
import dataStructures.lists.DoubleOrderedLinkedList;

/**
 * Represents the interface for a mission within the game.
 *
 * This interface defines the basic attributes and functionalities of a mission,
 * including its code and target. Classes implementing this interface must provide
 * methods to retrieve and modify these attributes.
 */
public interface MissionInterface {

    /**
     * Retrieves the code of the mission.
     *
     * @return the mission code
     */
    String getCode();

    /**
     * Sets the code of the mission.
     *
     * @param code the mission code to set
     */
    void setCode(String code);

    /**
     * Retrieves the target type of the mission.
     *
     * @return the target type
     */
    String getTarget();

    /**
     * Sets the target type of the mission.
     *
     * @param target the target type to set
     */
    void setTarget(String target);
}