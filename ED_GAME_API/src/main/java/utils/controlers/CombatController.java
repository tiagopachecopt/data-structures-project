package utils.controlers;

import dataStructures.exceptions.EmptyCollectionException;
import dataStructures.lists.DoubleUnorderedLinkedList;
import models.entities.Enemy;
import models.entities.Player;
import models.world.GameNetwork;
import models.world.Room;

/**
 * Manages combat interactions between the player and enemies within a room.
 *
 * This class handles the simulation of combat scenarios, where the player engages
 * with enemies present in a room. It manages the attack sequences, enemy eliminations,
 * and updates to the player'Simulation.s health based on combat outcomes.
 */
public class CombatController {

    /**
     * Simulates combat between the player and enemies in the current room.
     * Enemies from adjacent rooms cannot move out but can join during combat.
     *
     * @param player        The player engaging in combat.
     * @param currentRoom   The room where combat occurs.
     * @param building      The game network representing the building.
     * @param enemyManager  The controller managing enemy movements.
     * @return {@code true} if the player survives the combat, {@code false} otherwise.
     * @throws EmptyCollectionException If an operation on an empty collection fails.
     */
    public boolean simulateCombat(Player player, Room currentRoom, GameNetwork building, EnemyController enemyManager) throws EmptyCollectionException {
        DoubleUnorderedLinkedList<Enemy> enemies = currentRoom.getEnemies();
        Room combatRoom = currentRoom;

        while (!enemies.isEmpty()) {
            System.out.println("Player attacks!");

            for (Enemy enemy : enemies) {
                enemy.takeDamage(player.getFirePower());
            }

            DoubleUnorderedLinkedList<Enemy> enemiesToRemove = new DoubleUnorderedLinkedList<>();
            for (Enemy enemy : enemies) {
                if (enemy.getFirePower() <= 0) {
                    enemiesToRemove.addToRear(enemy);
                }
            }
            for (Enemy enemy : enemiesToRemove) {
                enemies.remove(enemy);
                System.out.println("Enemy defeated!");
            }

            if (enemies.isEmpty()) {
                System.out.println("All enemies defeated in the room.");
                break;
            }

            System.out.println("Enemies attack!");

            for (Enemy enemy : enemies) {
                player.applyDamage(enemy.getFirePower());
                System.out.println("Player takes " + enemy.getFirePower() + " damage!");
                if (player.getHealth() <= 0) {
                    System.out.println("Player was defeated!");
                    return false;
                }
            }

            boolean enemiesMovedIntoPlayerRoom = enemyManager.moveEnemies(building, currentRoom, combatRoom);

            if (enemiesMovedIntoPlayerRoom) {
                System.out.println("Additional enemies have entered the room!");
            }
        }

        return true;
    }

}
