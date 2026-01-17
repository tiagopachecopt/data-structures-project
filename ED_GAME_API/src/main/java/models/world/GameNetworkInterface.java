package models.world;

import dataStructures.exceptions.EmptyCollectionException;
import models.entities.Player;

public interface GameNetworkInterface {

    void updateEdgeWeight(Room from, Room to, Player player) throws EmptyCollectionException;

    void updateAllEdgeWeights(Player player) throws EmptyCollectionException;
}