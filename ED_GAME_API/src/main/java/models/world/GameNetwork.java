package models.world;

import dataStructures.exceptions.EmptyCollectionException;
import dataStructures.graphs.Network;
import models.entities.Enemy;
import models.entities.Player;
import models.items.MedKit;

/**
 * Represents the game-specific network of rooms within the game world.
 *
 * This class extends the base Network class to incorporate game-specific functionalities
 * such as dynamically updating edge weights based on player and enemy interactions.
 */
public class GameNetwork extends Network<Room> implements GameNetworkInterface {

    public GameNetwork(int num) {
        super(num);
    }

    /**
     * Updates the weight of an edge between two rooms based on game-specific logic involving
     * the player Simulation interactions with enemies and available resources.
     *
     * @param from   the starting room
     * @param to     the destination room
     * @param player the player whose attributes influence the edge weight
     * @throws EmptyCollectionException if an operation on an empty collection fails
     */
    @Override
    public void updateEdgeWeight(Room from, Room to, Player player) throws EmptyCollectionException {
        int totalDamageCost = 0;

        for (Enemy enemy : to.getEnemies()) {
            if (player.getFirePower() >= enemy.getFirePower()) {
                continue;
            } else {
                int turns = (int) Math.ceil((double) enemy.getFirePower() / player.getFirePower());
                int damageTaken = (turns - 1) * enemy.getFirePower();
                totalDamageCost += damageTaken;
            }
        }

        int healingBenefit = 0;
        if (!player.getMedKits().isEmpty()) {
            MedKit medKit = player.getMedKits().peek();
            healingBenefit = Math.min(100 - player.getHealth(), medKit.getRecoveryPoints());
        }

        int kevlarBenefit = to.getTotalKevlarPoints();

        double newWeight = totalDamageCost - healingBenefit - kevlarBenefit;

        super.updateEdgeWeight(from, to, newWeight);
    }

    /**
     * Updates the weights of all edges in the network based on the current state of the player.
     *
     * @param player the player whose attributes influence all edge weights
     * @throws EmptyCollectionException if an operation on an empty collection fails
     */
    @Override
    public void updateAllEdgeWeights(Player player) throws EmptyCollectionException {
        for (Room room : getVertices()) {
            for (Room neighbor : getAdjacentVertices(room)) {
                updateEdgeWeight(room, neighbor, player);
            }
        }
    }
}
