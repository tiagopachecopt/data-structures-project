package models.mission;

import dataStructures.lists.DoubleUnorderedLinkedList;

import java.util.Iterator;

/**
 * Represents the result of a mission simulation.
 *
 * This class encapsulates the details of a simulation result, including the player's name,
 * the mission details, the player's health after completing the mission, and the path taken during the mission.
 */
public class SimulationResult implements SimulationResultInterface {
    private String missionName;
    private int missionVersion;
    private String playerName;
    private int playerHealth;
    private DoubleUnorderedLinkedList<String> pathTaken;

    /**
     * Constructs a SimulationResult with the specified details.
     *
     * @param playerName     the name of the player
     * @param missionName    the name of the mission
     * @param missionVersion the version of the mission
     * @param playerHealth   the player's remaining health after the mission
     * @param pathTaken      the path taken by the player during the mission
     */
    public SimulationResult(String playerName, String missionName, int missionVersion, int playerHealth, DoubleUnorderedLinkedList<String> pathTaken) {
        this.playerName = playerName;
        this.missionName = missionName;
        this.missionVersion = missionVersion;
        this.playerHealth = playerHealth;
        this.pathTaken = pathTaken;
    }

    /**
     * Returns a string representation of the simulation result.
     *
     * @return a string detailing the simulation result
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SimulationResult{");
        sb.append("playerName='").append(playerName).append('\'');
        sb.append(", missionName='").append(missionName).append('\'');
        sb.append(", missionVersion=").append(missionVersion);
        sb.append(", playerHealth=").append(playerHealth);
        sb.append(", pathTaken=").append(getPathAsString());
        sb.append('}');
        return sb.toString();
    }

    /**
     * Converts the path taken into a readable string format.
     *
     * @return the path taken as a string, with arrows indicating the sequence
     */
    @Override
    public String getPathAsString() {
        StringBuilder pathSb = new StringBuilder();
        Iterator<String> iterator = pathTaken.iterator();
        while (iterator.hasNext()) {
            pathSb.append(iterator.next());
            if (iterator.hasNext()) {
                pathSb.append(" -> ");
            }
        }
        return pathSb.toString();
    }

    /**
     * Retrieves the name of the mission.
     *
     * @return the mission name
     */
    @Override
    public String getMissionName() {
        return missionName;
    }

    /**
     * Sets the name of the mission.
     *
     * @param missionName the mission name to set
     */
    @Override
    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    /**
     * Retrieves the version of the mission.
     *
     * @return the mission version
     */
    @Override
    public int getMissionVersion() {
        return missionVersion;
    }

    /**
     * Sets the version of the mission.
     *
     * @param missionVersion the mission version to set
     */
    @Override
    public void setMissionVersion(int missionVersion) {
        this.missionVersion = missionVersion;
    }

    /**
     * Retrieves the player's name.
     *
     * @return the player's name
     */
    @Override
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Sets the player's name.
     *
     * @param playerName the player's name to set
     */
    @Override
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Retrieves the player's remaining health after the mission.
     *
     * @return the player's health
     */
    @Override
    public int getPlayerHealth() {
        return playerHealth;
    }

    /**
     * Sets the player's health after the mission.
     *
     * @param playerHealth the player's health to set
     */
    @Override
    public void setPlayerHealth(int playerHealth) {
        this.playerHealth = playerHealth;
    }

    /**
     * Retrieves the path taken during the mission.
     *
     * @return the path taken as a {@link DoubleUnorderedLinkedList}
     */
    @Override
    public DoubleUnorderedLinkedList<String> getPathTaken() {
        return pathTaken;
    }

    /**
     * Sets the path taken during the mission.
     *
     * @param pathTaken the path to set
     */
    public void setPathTaken(DoubleUnorderedLinkedList<String> pathTaken) {
        this.pathTaken = pathTaken;
    }

    /**
     * Compares this simulation result to another based on the player's remaining health.
     *
     * @param o the other {@link SimulationResult} to compare
     * @return a negative integer, zero, or a positive integer as this result is less than,
     * equal to, or greater than the specified result
     */
    @Override
    public int compareTo(SimulationResult o) {
        return Integer.compare(o.playerHealth, this.playerHealth);
    }
}