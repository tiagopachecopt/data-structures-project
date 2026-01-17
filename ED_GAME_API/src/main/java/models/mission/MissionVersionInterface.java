package models.mission;

import dataStructures.lists.DoubleOrderedLinkedList;
import models.world.GameNetwork;

/**
 * Represents the interface for a specific version of a mission scenario.
 *
 * This interface defines the essential attributes and behaviors for a mission version,
 * including its version number, building layout, and simulation results. Classes implementing
 * this interface must provide functionality for managing these attributes.
 */
public interface MissionVersionInterface extends Comparable<MissionVersion> {

    /**
     * Retrieves the version number of this mission scenario.
     *
     * @return the version number
     */
    int getVersion();

    /**
     * Retrieves the building layout (GameNetwork) associated with this mission version.
     *
     * @return the {@link GameNetwork} representing the building layout
     */
    GameNetwork getBuilding();

    /**
     * Sets the version number of this mission scenario.
     *
     * @param version the version number to set
     */
    void setVersion(int version);

    /**
     * Retrieves the simulation results associated with this mission version.
     *
     * @return a {@link DoubleOrderedLinkedList} containing the simulation results
     */
    DoubleOrderedLinkedList<SimulationResult> getSimulationResults();

    /**
     * Sets the simulation results for this mission version.
     *
     * @param simulationResults a {@link DoubleOrderedLinkedList} containing the simulation results to set
     */
    void setSimulationResults(DoubleOrderedLinkedList<SimulationResult> simulationResults);

    /**
     * Adds a simulation result to the list of results for this mission version.
     *
     * @param simulationResult the {@link SimulationResult} to add
     */
    void addSimulationResult(SimulationResult simulationResult);
}