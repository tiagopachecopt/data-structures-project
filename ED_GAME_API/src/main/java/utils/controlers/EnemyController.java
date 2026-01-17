package utils.controlers;

import dataStructures.exceptions.EmptyCollectionException;
import dataStructures.lists.DoubleUnorderedLinkedList;
import dataStructures.stacks.LinkedStack;
import models.entities.Enemy;
import models.world.GameNetwork;
import models.world.Room;

/**
 * Manages the movement of enemies within the game network.
 *
 * This class handles the logic for moving enemies from one room to another
 * based on the current game state. It ensures that enemies do not move
 * out of the combat room and randomly navigates other enemies within
 * specified constraints.
 */
public class EnemyController {

    /**
     * Moves enemies across the network based on the current game state.
     * Enemies in the combat room cannot move. All other enemies move randomly within constraints.
     *
     * @param building     The game network representing the building.
     * @param playerRoom   The room where the player currently is.
     * @param combatRoom   The room where combat is currently happening.
     * @return {@code true} if any enemies moved into the player'Simulation.s room, {@code false} otherwise.
     * @throws EmptyCollectionException If any collection operation fails.
     */
    public boolean moveEnemies(GameNetwork building, Room playerRoom, Room combatRoom) throws EmptyCollectionException {
        boolean enemiesMovedIntoPlayerRoom = false;
        DoubleUnorderedLinkedList<Room> rooms = building.getVertices();

        DoubleUnorderedLinkedList<Enemy> movedEnemies = new DoubleUnorderedLinkedList<>();

        for (Room room : rooms) {
            if (room == combatRoom) {
                continue;
            }

            DoubleUnorderedLinkedList<Enemy> enemiesToMove = new DoubleUnorderedLinkedList<>();

            for (Enemy enemy : room.getEnemies()) {
                if (!movedEnemies.contains(enemy)) {
                    enemiesToMove.addToFront(enemy);
                }
            }

            while (!enemiesToMove.isEmpty()) {
                Enemy enemy = enemiesToMove.removeFirst();
                room.getEnemies().remove(enemy);

                DoubleUnorderedLinkedList<Room> validAdjacentRooms = getValidAdjacentRooms(building, enemy.getStartingRoom(), room);

                if (!validAdjacentRooms.isEmpty()) {
                    int randomIndex = (int) (Math.random() * validAdjacentRooms.size());
                    Room targetRoom = getRoomByIndex(validAdjacentRooms, randomIndex);

                    targetRoom.addEnemy(enemy);
                    movedEnemies.addToFront(enemy);

                    System.out.println("Enemy moved: " + enemy.getName() + " from " + room.getName() + " to " + targetRoom.getName());

                    if (targetRoom == playerRoom) {
                        enemiesMovedIntoPlayerRoom = true;
                    }
                } else {
                    System.out.println("No valid adjacent rooms for enemy: " + enemy.getName());
                    room.addEnemy(enemy);
                }
            }
        }

        return enemiesMovedIntoPlayerRoom;
    }

    /**
     * Retrieves valid adjacent rooms for an enemy to move to, within a maximum radius from their starting room.
     *
     * @param building      The game network.
     * @param startingRoom  The enemy'Simulation.s starting room.
     * @param currentRoom   The enemy'Simulation.s current room.
     * @return A list of valid adjacent rooms.
     * @throws EmptyCollectionException If any collection operation fails.
     */
    private DoubleUnorderedLinkedList<Room> getValidAdjacentRooms(GameNetwork building, Room startingRoom, Room currentRoom) throws EmptyCollectionException {
        DoubleUnorderedLinkedList<Room> validRooms = new DoubleUnorderedLinkedList<>();
        DoubleUnorderedLinkedList<Room> adjacentRooms = building.getAdjacentVertices(currentRoom);

        for (Room adjacentRoom : adjacentRooms) {
            if (building.isAdjacent(currentRoom, adjacentRoom)) {
                validRooms.addToRear(adjacentRoom);
            }
        }
        return validRooms;
    }


    /**
     * Retrieves a room by its index in the list.
     *
     * @param rooms The list of rooms.
     * @param index The index of the room to retrieve.
     * @return The room at the specified index.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    private Room getRoomByIndex(DoubleUnorderedLinkedList<Room> rooms, int index) {
        int currentIndex = 0;
        for (Room room : rooms) {
            if (currentIndex == index) {
                return room;
            }
            currentIndex++;
        }
        throw new IndexOutOfBoundsException("Index out of bounds for the list.");
    }
}
