package models.mission;

import dataStructures.lists.DoubleOrderedLinkedList;
import models.world.GameNetwork;

/**
 * Represents a specific version of a mission scenario.
 *
 * Each version can have unique conditions, buildings, enemies layout, and simulation results.
 * This class encapsulates the properties and behaviors associated with a single mission version,
 * including its version number, building layout, and simulation results.
 */
public class MissionVersion implements MissionVersionInterface {
    private int version;
    private GameNetwork building;
    private DoubleOrderedLinkedList<SimulationResult> simulationResults;

    /**
     * Constructs a new MissionVersion with the specified version number and building.
     * Initializes an empty list for simulation results.
     *
     * @param version  the version number of the mission
     * @param building the {@link GameNetwork} representing the building layout for this mission version
     */
    public MissionVersion(int version, GameNetwork building) {
        this.version = version;
        this.building = building;
        this.simulationResults = new DoubleOrderedLinkedList<>();
    }

    /**
     * Retrieves the version number of this mission scenario.
     *
     * @return the version number
     */
    @Override
    public int getVersion() {
        return version;
    }

    /**
     * Sets the version number of this mission scenario.
     *
     * @param version the version number to set
     */
    @Override
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * Retrieves the building layout (GameNetwork) associated with this mission version.
     *
     * @return the {@link GameNetwork} building layout
     */
    @Override
    public GameNetwork getBuilding() {
        return building;
    }

    /**
     * Sets the building layout (GameNetwork) for this mission version.
     *
     * @param building the {@link GameNetwork} building layout to set
     */
    public void setBuilding(GameNetwork building) {
        this.building = building;
    }

    /**
     * Retrieves the simulation results associated with this mission version.
     *
     * @return a {@link DoubleOrderedLinkedList} containing the simulation results
     */
    @Override
    public DoubleOrderedLinkedList<SimulationResult> getSimulationResults() {
        return simulationResults;
    }

    /**
     * Sets the simulation results for this mission version.
     *
     * @param simulationResults a {@link DoubleOrderedLinkedList} containing the simulation results to set
     */
    @Override
    public void setSimulationResults(DoubleOrderedLinkedList<SimulationResult> simulationResults) {
        this.simulationResults = simulationResults;
    }

    /**
     * Adds a simulation result to the list of results for this mission version.
     *
     * @param simulationResult the {@link SimulationResult} to add
     */
    @Override
    public void addSimulationResult(SimulationResult simulationResult) {
        this.simulationResults.add(simulationResult);
    }

    /**
     * Compares this mission version to another based on their version numbers.
     *
     * @param o the {@link MissionVersion} to compare against
     * @return a negative integer, zero, or a positive integer as this version is less than,
     * equal to, or greater than the specified version
     */
    @Override
    public int compareTo(MissionVersion o) {
        return Integer.compare(this.version, o.version);
    }
}