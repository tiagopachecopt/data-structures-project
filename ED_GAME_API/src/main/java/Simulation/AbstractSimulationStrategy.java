package Simulation;

import models.mission.MissionVersion;
import utils.controlers.CombatController;
import utils.controlers.EnemyController;
import dataStructures.exceptions.EmptyCollectionException;
import models.entities.Player;
import models.world.GameNetwork;
import models.world.Room;

import java.util.Iterator;

/**
 * Provides shared functionality for different simulation strategies.
 *
 * This abstract class implements the Simulation Strategy interface
 * and serves as a base class for both automatic and manual simulation strategies.
 * It provides common methods and properties used to simulate the player's progress
 * through a mission, including pathfinding and combat management.
 */
public abstract class AbstractSimulationStrategy implements SimulationStrategy {

    /** Controller for managing combat-related logic during the simulation. */
    protected CombatController combatController;

    /** Controller for managing enemy interactions during the simulation. */
    protected EnemyController enemyManager;

    /**
     * Initializes the combat and enemy controllers used in simulations.
     */
    public AbstractSimulationStrategy() {
        this.combatController = new CombatController();
        this.enemyManager = new EnemyController();
    }

    /**
     * Finds the best entry point for the player based on the shortest path to the target room.
     *
     * The best entry point is the room with the lowest path weight to the target room,
     * among all rooms marked as entry points in the game network.
     *
     * @param player  The player attempting the mission.
     * @param mission The mission details, including the building layout.
     * @return The best entry room for the player to start.
     * @throws EmptyCollectionException If an operation on an empty collection fails.
     * @throws IllegalStateException If no entry point is found in the mission.
     */
    protected Room findBestEntryPoint(Player player, MissionVersion mission) throws EmptyCollectionException {
        GameNetwork building = mission.getBuilding();
        Room targetRoom = findTargetRoom(mission);

        building.updateAllEdgeWeights(player);

        double minPathWeight = Double.POSITIVE_INFINITY;
        Room bestEntryRoom = null;

        for (Room room : building.getVertices()) {
            if (room.isExistEntry()) {
                double pathWeight = building.shortestPathWeight(room, targetRoom);
                if (pathWeight < minPathWeight) {
                    minPathWeight = pathWeight;
                    bestEntryRoom = room;
                }
            }
        }

        if (bestEntryRoom == null) {
            throw new IllegalStateException("No entry point found in the mission!");
        }

        return bestEntryRoom;
    }

    /**
     * Finds the target room marked in the mission.
     *
     * The target room is identified as the room marked as a target in the mission's
     * building layout. If no room is marked as a target, an exception is thrown.
     *
     * @param mission The mission containing the building layout.
     * @return The target room in the mission.
     * @throws IllegalStateException If no target room is found in the mission.
     */
    protected Room findTargetRoom(MissionVersion mission) {
        for (Room room : mission.getBuilding().getVertices()) {
            if (room.isTarget()) {
                return room;
            }
        }
        throw new IllegalStateException("Target room not found in the mission!");
    }

    /**
     * Finds the target room in the specified building.
     *
     * This method is similar to {@link #findTargetRoom(MissionVersion)} but operates
     * directly on the game network. It identifies the room marked as the target.
     *
     * @param gameNetwork The building containing the room layout.
     * @return The target room in the building.
     * @throws IllegalStateException If no target room is found in the building.
     */
    protected Room findTargetRoom(GameNetwork gameNetwork) {
        for (Room room : gameNetwork.getVertices()) {
            if (room.isTarget()) {
                return room;
            }
        }
        throw new IllegalStateException("Target room not found in the mission!");
    }

    /**
     * Determines the next room for the player based on the shortest path and updated edge weights.
     *
     * The method calculates the shortest path from the player's current room to the target room,
     * considering the updated weights in the game network. It returns the next room in the path.
     *
     * @param currentRoom The player's current room.
     * @param targetRoom  The target room the player is navigating toward.
     * @param building    The game network representing the building layout.
     * @param player      The player navigating the mission.
     * @return The next room in the path toward the target.
     * @throws EmptyCollectionException If no valid path is found.
     * @throws IllegalStateException If no valid next room is found in the path.
     */
    protected Room determineNextRoom(Room currentRoom, Room targetRoom, GameNetwork building, Player player) throws EmptyCollectionException {
        for (Room neighbor : building.getAdjacentVertices(currentRoom)) {
            building.updateEdgeWeight(currentRoom, neighbor, player);
        }

        Iterator<Room> pathIterator = building.iteratorShortestPath(currentRoom, targetRoom);

        if (pathIterator.hasNext()) {
            pathIterator.next();
        }

        if (pathIterator.hasNext()) {
            return pathIterator.next();
        }

        throw new IllegalStateException("No valid next room found in the path.");
    }
}