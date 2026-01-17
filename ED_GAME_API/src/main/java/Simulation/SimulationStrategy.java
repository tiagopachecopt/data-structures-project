package Simulation;

import dataStructures.exceptions.EmptyCollectionException;
import models.entities.Player;
import models.mission.Mission;
import models.mission.MissionVersion;

/**
 * Defines the contract for simulation strategies in the game.
 *
 * Implementing classes should provide specific simulation mechanisms,
 * whether automatic or manual, by overriding the runSimulation
 * method.
 */
public interface SimulationStrategy {

    /**
     * Runs the simulation for the player navigating through the mission.
     *
     * @param player  The player attempting the mission.
     * @param mission The mission details, including the building layout.
     *
     * @throws EmptyCollectionException If an operation on an empty collection fails.
     */
    void runSimulation(Player player, Mission mission, MissionVersion missionVersion) throws EmptyCollectionException;
}
