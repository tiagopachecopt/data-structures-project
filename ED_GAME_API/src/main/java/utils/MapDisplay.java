package utils;

import dataStructures.exceptions.EmptyCollectionException;
import dataStructures.lists.DoubleUnorderedLinkedList;
import models.items.Item;
import models.world.GameNetwork;
import models.world.Room;

/**
 * The MapDisplay class provides functionality to display an ASCII representation
 * of the game map. It performs a depth-first traversal of the game network,
 * printing each room and its connections in a tree-like structure with appropriate
 * formatting and indentation.
 */
public class MapDisplay {

    /**
     * Displays an ASCII map of the building using a custom depth-first traversal.
     * It starts from entry rooms and recursively visits connected rooms, printing
     * them with indentation and symbols to represent the map'Simulation.s structure.
     *
     * @param building the {@link GameNetwork} representing the building.
     * @throws EmptyCollectionException if an operation on an empty collection is attempted.
     */
    public static void displayAsciiMap(GameNetwork building) throws EmptyCollectionException {
        DoubleUnorderedLinkedList<Room> entryRooms = new DoubleUnorderedLinkedList<>();
        for (Room room : building.getVertices()) {
            if (room.isExistEntry()) {
                entryRooms.addToRear(room);
            }
        }

        if (entryRooms.isEmpty()) {
            System.out.println("No entry/exit rooms found in the map.");
            return;
        }

        System.out.println("=== Mission Map");
        DoubleUnorderedLinkedList<Room> visited = new DoubleUnorderedLinkedList<>();

        for (Room entryRoom : entryRooms) {
            if (!visited.contains(entryRoom)) {
                displayRoomDFS(entryRoom, building, visited, "", true);
            }
        }
    }


    /**
     * Recursively performs a depth-first traversal to display each room and its connections
     * with proper indentation and formatting. It includes details such as room name,
     * whether it'Simulation.s a target or entry/exit room, enemies present, and items available.
     *
     * @param room     the current {@link Room} being traversed.
     * @param building the {@link GameNetwork} containing the rooms.
     * @param visited  a list of rooms that have already been visited to prevent cycles.
     * @param prefix   the string prefix used for indentation in the display.
     * @param isLast   indicates if the current room is the last sibling in its level.
     * @throws EmptyCollectionException if an operation on an empty collection is attempted.
     */
    private static void displayRoomDFS(Room room, GameNetwork building, DoubleUnorderedLinkedList<Room> visited, String prefix, boolean isLast) throws EmptyCollectionException {
        visited.addToRear(room);

        System.out.print(prefix);

        if (isLast) {
            System.out.print("└── ");
            prefix += "    ";
        } else {
            System.out.print("├── ");
            prefix += "│   ";
        }

        System.out.print("[" + room.getName() + "]");
        if (room.isTarget()) {
            System.out.print(" (Target)");
        }
        if (room.isExistEntry()) {
            System.out.print(" (Entry/Exit)");
        }
        if (!room.getEnemies().isEmpty()) {
            System.out.print(" (Enemies: " + room.getEnemies().size() + ")");
        }
        if (!room.getItems().isEmpty()) {
            System.out.print(" (Items: ");
            for (Item item : room.getItems()) {
                System.out.print(item.getName() + " ");
            }
            System.out.print(")");
        }
        System.out.println();

        DoubleUnorderedLinkedList<Room> connectedRooms = building.getAdjacentVertices(room);

        DoubleUnorderedLinkedList<Room> unvisitedNeighbors = new DoubleUnorderedLinkedList<>();
        for (Room neighbor : connectedRooms) {
            if (!visited.contains(neighbor)) {
                unvisitedNeighbors.addToRear(neighbor);
            }
        }

        int size = unvisitedNeighbors.size();
        int index = 0;

        for (Room neighbor : unvisitedNeighbors) {
            index++;
            boolean neighborIsLast = (index == size);

            displayRoomDFS(neighbor, building, visited, prefix, neighborIsLast);
        }
    }
}
