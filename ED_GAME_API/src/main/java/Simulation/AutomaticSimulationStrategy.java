package Simulation;

import dataStructures.exceptions.EmptyCollectionException;
import dataStructures.lists.DoubleUnorderedLinkedList;
import models.entities.Enemy;
import models.entities.Player;
import models.mission.Mission;
import models.mission.MissionVersion;
import models.world.Room;

/**
 * Manages the automatic simulation of a player navigating through a mission.
 *
 * This class automates the player's progression through the mission, handling
 * room traversal, combat encounters, and room-specific effects. It leverages
 * controllers for managing combat and enemy movement and provides detailed
 * feedback on the player's performance throughout the simulation.
 */
public class AutomaticSimulationStrategy extends AbstractSimulationStrategy {

    /**
     * Constructs an instance of {@code AutomaticSimulationStrategy}.
     * Initializes the combat and enemy controllers via the superclass.
     */
    public AutomaticSimulationStrategy() {
        super();
    }

    /**
     * Executes the automatic simulation of a mission.
     *
     * The simulation progresses the player through the mission's rooms, resolving combat
     * encounters, applying room effects, and determining the optimal path to the target room.
     * Upon reaching the target room, the player navigates back to the starting room to
     * complete the mission.
     *
     * @param player         The player navigating the mission.
     * @param mission        The mission containing the target and building layout.
     * @param missionVersion The version of the mission being simulated.
     * @throws EmptyCollectionException If an operation on an empty collection is attempted.
     */
    @Override
    public void runSimulation(Player player, Mission mission, MissionVersion missionVersion) throws EmptyCollectionException {
        Room startingRoom = findBestEntryPoint(player, missionVersion);
        Room targetRoom = findTargetRoom(missionVersion);
        Room currentRoom = startingRoom;
        Room combatRoom = null;
        DoubleUnorderedLinkedList<String> pathTaken = new DoubleUnorderedLinkedList<>();

        boolean targetReached = false;
        boolean playerDefeated = false;

        pathTaken.addToRear(currentRoom.getName());

        while (!player.isDefeated()) {
            currentRoom.applyRoomEffects(player);

            if (!currentRoom.getEnemies().isEmpty()) {
                if (!simulateCombat(player, currentRoom, missionVersion)) {
                    playerDefeated = true;
                    break;
                }
            }

            Room nextRoom;
            if (!targetReached) {
                nextRoom = determineNextRoom(currentRoom, targetRoom, missionVersion.getBuilding(), player);
            } else {
                nextRoom = determineNextRoom(currentRoom, startingRoom, missionVersion.getBuilding(), player);
                if (nextRoom.equals(startingRoom)) {
                    break;
                }
            }

            currentRoom = nextRoom;
            pathTaken.addToRear(currentRoom.getName());

            if (currentRoom.equals(targetRoom) && !targetReached) {
                System.out.println("Target room reached! Now heading back to the start...");
                targetReached = true;
            }

            if (!currentRoom.getEnemies().isEmpty()) {
                System.out.println("You encounter enemies in " + currentRoom.getName() + "!");
                combatRoom = currentRoom;
                if (!simulateCombat(player, currentRoom, missionVersion)) {
                    playerDefeated = true;
                    break;
                }
                combatRoom = null;
            } else {
                currentRoom.applyRoomEffects(player);
            }

            boolean enemiesMovedIn = enemyManager.moveEnemies(missionVersion.getBuilding(), currentRoom, combatRoom);
            if (enemiesMovedIn) {
                System.out.println("Enemies rushed into your current room and strike first!");
                if (!handleEnemyFirstStrike(player, currentRoom)) {
                    playerDefeated = true;
                    break;
                }

                System.out.println("You strike back at the enemies!");
                combatRoom = currentRoom;
                if (!simulateCombat(player, currentRoom, missionVersion)) {
                    playerDefeated = true;
                    break;
                }
                combatRoom = null;
            }
        }

        if (playerDefeated || player.isDefeated()) {
            System.out.println("Player was defeated!");
        } else if (targetReached) {
            System.out.println("Mission completed successfully!");
        } else {
            System.out.println("Mission ended prematurely. No clear success or defeat.");
        }

        displayPathTaken(pathTaken);
    }

    /**
     * Simulates combat between the player and enemies in the current room.
     *
     * The combat logic is handled by the {@code CombatController}. The result determines
     * whether the player survives or is defeated during the combat.
     *
     * @param player         The player engaging in combat.
     * @param room           The current room containing enemies.
     * @param missionVersion The mission version being simulated.
     * @return {@code true} if the player survives the combat, {@code false} otherwise.
     * @throws EmptyCollectionException If an operation on an empty collection fails.
     */
    private boolean simulateCombat(Player player, Room room, MissionVersion missionVersion) throws EmptyCollectionException {
        return combatController.simulateCombat(player, room, missionVersion.getBuilding(), enemyManager);
    }

    /**
     * Handles the scenario where enemies move into the player's current room
     * and launch a first strike.
     *
     * The player takes damage from the enemies in the room. If the player's health
     * drops to zero or below, they are defeated.
     *
     * @param player       The player being attacked by enemies.
     * @param currentRoom  The room where the enemies have moved in.
     * @return {@code true} if the player survives the attack, {@code false} otherwise.
     */
    private boolean handleEnemyFirstStrike(Player player, Room currentRoom) {
        DoubleUnorderedLinkedList<Enemy> enemiesInRoom = currentRoom.getEnemies();

        for (Enemy enemy : enemiesInRoom) {
            player.applyDamage(enemy.getFirePower());
            System.out.println("You take " + enemy.getFirePower() + " damage! Current Health: " + player.getHealth());
            if (player.getHealth() <= 0) {
                return false;
            }
        }

        return !player.isDefeated();
    }

    /**
     * Displays the path taken by the player during the simulation.
     *
     * The path is presented in a horizontal format, with arrows indicating the sequence
     * of rooms visited by the player.
     *
     * @param pathTaken The list of room names visited during the simulation.
     */
    private void displayPathTaken(DoubleUnorderedLinkedList<String> pathTaken) {
        StringBuilder pathString = new StringBuilder();
        for (String roomName : pathTaken) {
            if (pathString.length() > 0) {
                pathString.append(" -> ");
            }
            pathString.append(roomName);
        }
        System.out.println("Path taken during the simulation: " + pathString.toString());
    }
}