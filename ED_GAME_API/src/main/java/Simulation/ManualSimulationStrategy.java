package Simulation;

import dataStructures.exceptions.EmptyCollectionException;
import dataStructures.lists.DoubleUnorderedLinkedList;
import models.entities.Enemy;
import models.entities.Player;
import models.mission.Mission;
import models.mission.MissionVersion;
import models.mission.SimulationResult;
import models.world.GameNetwork;
import models.world.Room;
import utils.MapDisplay;
import utils.jsonHandlers.SimulationResultManager;

import java.util.Iterator;
import java.util.Scanner;

/**
 * Manages the manual simulation of a player navigating through a mission.
 *
 * This class handles user-driven navigation, combat encounters, and item usage
 * as the player progresses through different rooms in a mission. It leverages
 * controllers to manage combat logic and enemy movements.
 */
public class ManualSimulationStrategy extends AbstractSimulationStrategy {

    private Scanner scanner;

    /**
     * Constructs a ManualSimulationStrategy with initialized scanner, combat, and enemy controllers.
     */
    public ManualSimulationStrategy() {
        super();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Runs the manual simulation for the player navigating through the mission.
     *
     * @param player  The player attempting the mission.
     * @param mission The mission details, including the building layout.
     * @throws EmptyCollectionException If an operation on an empty collection fails.
     */
    @Override
    public void runSimulation(Player player, Mission mission, MissionVersion missionVersion) throws EmptyCollectionException {
        GameNetwork building = missionVersion.getBuilding();

        Room entryRoom = promptForEntryPoint(building);
        Room currentRoom = entryRoom;
        boolean targetVisited = false;
        Room combatRoom = null;

        DoubleUnorderedLinkedList<String> pathTaken = new DoubleUnorderedLinkedList<>();
        pathTaken.addToRear(currentRoom.getName());

        System.out.println("=== Manual Simulation Started ===");
        MapDisplay.displayAsciiMap(missionVersion.getBuilding());

        while (!player.isDefeated()) {
            currentRoom.applyRoomEffects(player);
            if(!currentRoom.getEnemies().isEmpty()) {
                boolean survived = combatController.simulateCombat(player, currentRoom, building, enemyManager);
                if (!survived) {
                    System.out.println("Player was defeated!");
                    break;
                }
            }
            displayRoomDetails(currentRoom, player, building, targetVisited, entryRoom);

            if (targetVisited && currentRoom.equals(entryRoom)) {
                System.out.println("Mission completed successfully!");
                break;
            }

            System.out.println("\nChoose an action:");
            System.out.println("1. Move to another room");
            System.out.println("2. Use a recovery item");
            System.out.println("3. Wait");
            System.out.print("Enter your choice: ");
            int actionChoice = scanner.nextInt();

            switch (actionChoice) {
                case 1:
                    DoubleUnorderedLinkedList<Room> connectedRooms = building.getAdjacentVertices(currentRoom);
                    Room nextRoom = promptForRoomChoice(player, currentRoom, connectedRooms);

                    if (nextRoom == null) {
                        System.out.println("Invalid choice. Please try again.");
                        continue;
                    }

                    currentRoom = nextRoom;
                    pathTaken.addToRear(currentRoom.getName());

                    if (currentRoom.isTarget() && !targetVisited) {
                        System.out.println("Target room reached!");
                        targetVisited = true;
                    }

                    if (currentRoom.getEnemies().isEmpty()) {
                        currentRoom.applyRoomEffects(player);
                        combatRoom = null;
                    } else {
                        System.out.println("Combat with enemies!");
                        combatRoom = currentRoom;
                        boolean survived = combatController.simulateCombat(player, currentRoom, building, enemyManager);
                        combatRoom = null;
                        if (!survived) {
                            System.out.println("Player was defeated!");
                            break;
                        }
                    }
                    break;

                case 2:
                    if (!player.getMedKits().isEmpty()) {
                        if (player.getHealth() < 100) {
                            player.useMedKit();
                            System.out.println("Used a recovery item. Health is now: " + player.getHealth());
                        } else {
                            System.out.println("Can't use recovery item. Health is too high.");
                        }
                    } else {
                        System.out.println("No recovery items available.");
                    }
                    combatRoom = null;
                    break;

                case 3:
                    System.out.println("You chose to wait.");
                    combatRoom = null;
                    break;

                default:
                    System.out.println("Invalid action. Please try again.");
                    continue;
            }

            boolean enemiesMovedIntoPlayerRoom = enemyManager.moveEnemies(building, currentRoom, combatRoom);

            if (enemiesMovedIntoPlayerRoom) {
                System.out.println("Enemies have entered your room and attack first!");

                DoubleUnorderedLinkedList<Enemy> enemiesInRoom = currentRoom.getEnemies();

                for (Enemy enemy : enemiesInRoom) {
                    player.applyDamage(enemy.getFirePower());
                    System.out.println("Player takes " + enemy.getFirePower() + " damage from enemy!");
                    if (player.getHealth() <= 0) {
                        System.out.println("Player was defeated!");
                        break;
                    }
                }

                if (player.isDefeated()) {
                    break;
                }

                System.out.println("Player attacks back!");

                combatRoom = currentRoom;
                boolean survived = combatController.simulateCombat(player, currentRoom, building, enemyManager);
                combatRoom = null;
                if (!survived) {
                    System.out.println("Player was defeated!");
                    break;
                }
            }

            System.out.println("\n--- End of Turn ---\n");
        }

        SimulationResult result = new SimulationResult(player.getName(), mission.getCode(), missionVersion.getVersion(), player.getHealth(), pathTaken);
        SimulationResultManager.saveResult(mission.getCode(),missionVersion.getVersion(),result);

        System.out.println("=== Simulation Ended ===");
    }

    /**
     * Displays the details of the current room, including items, enemies, connected rooms,
     * best paths, and player status.
     *
     * @param currentRoom   The current room the player is in.
     * @param player        The player navigating the mission.
     * @param building      The game network representing the building layout.
     * @param targetVisited Indicates if the target room has been visited.
     * @param entryRoom     The entry room of the mission.
     * @throws EmptyCollectionException If an operation on an empty collection fails.
     */
    private void displayRoomDetails(Room currentRoom, Player player, GameNetwork building, boolean targetVisited, Room entryRoom) throws EmptyCollectionException {
        System.out.println("Current Room: " + currentRoom.getName());
        System.out.println("- Items in Room: " + currentRoom.getItems().size());
        System.out.println("- Enemies in Room: " + currentRoom.getEnemies().size());
        System.out.println("- Connected Rooms: ");

        for (Room neighbor : building.getAdjacentVertices(currentRoom)) {
            System.out.println("  - " + neighbor.getName());
        }

        if (!targetVisited) {
            Room targetRoom = findTargetRoom(building);
            System.out.println("\n== Best Path to Target Room ==");
            displayBestPath("Path to Target Room", currentRoom, targetRoom, player, building);
        } else {
            System.out.println("\n== Best Path to Entry/Exit Room ==");
            displayBestPath("Path to Entry/Exit Room", currentRoom, entryRoom, player, building);
        }

        Room closestMedKit = findClosestRoomWithMedkit(currentRoom, player, building);
        if (closestMedKit != null) {
            System.out.println("\n== Closest MedKit Room ==");
            System.out.println("Closest MedKit is in: " + closestMedKit.getName());
            displayBestPath("Path to Closest MedKit", currentRoom, closestMedKit, player, building);
        } else {
            System.out.println("No MedKits available in the building.");
        }

        System.out.println("Player Health: " + player.getHealth());
        System.out.println("Number of MedKits: " + player.getMedKits().size());
    }

    /**
     * Displays the best path from the current room to the specified target room.
     *
     * @param label       A descriptive label for the path being displayed.
     * @param currentRoom The player's current room.
     * @param targetRoom  The target room to navigate to.
     * @param player      The player navigating the mission.
     * @param building    The game network representing the building layout.
     * @throws EmptyCollectionException If an operation on an empty collection fails.
     */
    private void displayBestPath(String label, Room currentRoom, Room targetRoom, Player player, GameNetwork building) throws EmptyCollectionException {
        building.updateAllEdgeWeights(player);
        Iterator<Room> pathIterator = building.iteratorShortestPath(currentRoom, targetRoom);
        System.out.print(label + ": ");
        while (pathIterator.hasNext()) {
            System.out.print(pathIterator.next().getName());
            if (pathIterator.hasNext()) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    /**
     * Finds the closest room with a medkit by checking all rooms and computing the shortest path to each.
     *
     * @param startRoom The starting room.
     * @param player    The player (used for pathfinding).
     * @param building  The game network representing the building layout.
     * @return The closest room with a medkit or null if none is found.
     * @throws EmptyCollectionException If an operation on an empty collection fails.
     */
    private Room findClosestRoomWithMedkit(Room startRoom, Player player, GameNetwork building) throws EmptyCollectionException {
        building.updateAllEdgeWeights(player);
        Room closestRoom = null;
        double shortestDistance = Double.POSITIVE_INFINITY;

        for (Room room : building.getVertices()) {
            if (room.hasMedKit()) {
                double distance = building.shortestPathWeight(startRoom, room);
                if (distance < shortestDistance) {
                    closestRoom = room;
                    shortestDistance = distance;
                }
            }
        }

        return closestRoom;
    }

    /**
     * Prompts the user to select an entry/exit room from the available options.
     *
     * @param building The game network representing the building layout.
     * @return The chosen entry/exit room.
     * @throws EmptyCollectionException If an operation on an empty collection fails.
     */
    private Room promptForEntryPoint(GameNetwork building) throws EmptyCollectionException {
        DoubleUnorderedLinkedList<Room> rooms = building.getVertices();
        System.out.println("Choose an entry/exit room:");
        int index = 0;

        DoubleUnorderedLinkedList<Room> entryRooms = new DoubleUnorderedLinkedList<>();

        for (Room room : rooms) {
            if (room.isExistEntry() && !room.isTarget()) {
                System.out.println(index + ": " + room.getName());
                entryRooms.addToRear(room);
                index++;
            }
        }

        if (entryRooms.isEmpty()) {
            throw new IllegalStateException("No entry/exit rooms available in the mission!");
        }

        System.out.print("Enter the number of the entry/exit room: ");
        int choice = scanner.nextInt();

        if (choice < 0 || choice >= index) {
            throw new IllegalArgumentException("Invalid room choice.");
        }

        int currentIndex = 0;
        for (Room room : entryRooms) {
            if (currentIndex == choice) {
                return room;
            }
            currentIndex++;
        }

        throw new IllegalStateException("Unexpected error retrieving the chosen room.");
    }

    /**
     * Prompts the user to select the next room to move to from the available connected rooms.
     *
     * @param player         The player navigating the mission.
     * @param currentRoom    The player's current room.
     * @param connectedRooms The list of rooms connected to the current room.
     * @return The chosen next room or null if the choice is invalid.
     * @throws EmptyCollectionException If an operation on an empty collection fails.
     */
    private Room promptForRoomChoice(Player player, Room currentRoom, DoubleUnorderedLinkedList<Room> connectedRooms) throws EmptyCollectionException {
        System.out.println("Player: " + player.getName());
        System.out.println("Current Room: " + currentRoom.getName());
        System.out.println("Available Rooms:");

        int index = 0;
        for (Room room : connectedRooms) {
            System.out.println(index + ": " + room.getName());
            index++;
        }

        System.out.print("Enter the number of the room to move to: ");
        int choice = scanner.nextInt();

        if (choice < 0 || choice >= connectedRooms.size()) {
            return null;
        }

        int currentIndex = 0;
        for (Room room : connectedRooms) {
            if (currentIndex == choice) {
                return room;
            }
            currentIndex++;
        }

        return null;
    }

}
