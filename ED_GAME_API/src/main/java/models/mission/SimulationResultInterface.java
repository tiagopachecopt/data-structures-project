package models.mission;

import dataStructures.lists.DoubleUnorderedLinkedList;

/**
 * Interface representing the result of a mission simulation.
 *
 * This interface defines the core attributes and behaviors of a simulation result,
 * including details about the mission, the player, their health after the simulation,
 * and the path taken during the mission. Classes implementing this interface must
 * provide functionality to manage these attributes and generate a readable path string.
 */
public interface SimulationResultInterface extends Comparable<SimulationResult> {

    /**
     * Retrieves the name of the mission associated with the simulation result.
     *
     * @return the name of the mission
     */
    String getMissionName();

    /**
     * Sets the name of the mission associated with the simulation result.
     *
     * @param missionName the mission name to set
     */
    void setMissionName(String missionName);

    /**
     * Retrieves the version of the mission associated with the simulation result.
     *
     * @return the mission version
     */
    int getMissionVersion();

    /**
     * Sets the version of the mission associated with the simulation result.
     *
     * @param missionVersion the mission version to set
     */
    void setMissionVersion(int missionVersion);

    /**
     * Retrieves the player's name.
     *
     * @return the name of the player
     */
    String getPlayerName();

    /**
     * Sets the player's name.
     *
     * @param playerName the player's name to set
     */
    void setPlayerName(String playerName);

    /**
     * Retrieves the player's health after the simulation.
     *
     * @return the player's health
     */
    int getPlayerHealth();

    /**
     * Sets the player's health after the simulation.
     *
     * @param playerHealth the player's health to set
     */
    void setPlayerHealth(int playerHealth);

    /**
     * Retrieves the path taken by the player during the mission.
     *
     * @return the path taken as a {@link DoubleUnorderedLinkedList} of strings
     */
    DoubleUnorderedLinkedList<String> getPathTaken();

    /**
     * Sets the path taken by the player during the mission.
     *
     * @param pathTaken the path to set
     */
    void setPathTaken(DoubleUnorderedLinkedList<String> pathTaken);

    /**
     * Converts the path taken into a readable string format.
     *
     * @return a string representation of the path taken, with steps separated by arrows
     */
    String getPathAsString();
}