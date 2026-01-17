package dataStructures.graphs;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class NetworkTest {

    @Test
    void testAddVertex() {
        Network<String> network = new Network<>();

        network.addVertex("A");
        assertEquals(1, network.size());
        assertTrue(network.contains("A"));

        network.addVertex("B");
        assertEquals(2, network.size());
        assertTrue(network.contains("B"));
    }

    @Test
    void testAddEdge() {
        Network<String> network = new Network<>();

        network.addVertex("A");
        network.addVertex("B");
        network.addEdge("A", "B", 5.0);

        assertTrue(network.isAdjacent("A", "B"));
        assertEquals(5.0, network.getWeightMatrixValue(network.getIndex("A"), network.getIndex("B")));
    }

    @Test
    void testShortestPathWeight() {
        Network<String> network = new Network<>();

        network.addVertex("A");
        network.addVertex("B");
        network.addVertex("C");
        network.addEdge("A", "B", 2.0);
        network.addEdge("B", "C", 3.0);
        network.addEdge("A", "C", 10.0);

        assertEquals(5.0, network.shortestPathWeight("A", "C"));
    }

    @Test
    void testIteratorShortestPath() {
        Network<String> network = new Network<>();

        network.addVertex("A");
        network.addVertex("B");
        network.addVertex("C");
        network.addVertex("D");
        network.addEdge("A", "B", 1.0);
        network.addEdge("B", "C", 1.0);
        network.addEdge("C", "D", 1.0);
        network.addEdge("A", "D", 10.0);

        Iterator<String> iterator = network.iteratorShortestPath("A", "D");

        assertEquals("A", iterator.next());
        assertEquals("B", iterator.next());
        assertEquals("C", iterator.next());
        assertEquals("D", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testAddEdgeInvalidVertices() {
        Network<String> network = new Network<>();

        network.addVertex("A");

        assertThrows(IllegalArgumentException.class, () -> network.addEdge("A", "B", 5.0));
    }

    @Test
    void testShortestPathWeightInvalidVertices() {
        Network<String> network = new Network<>();

        network.addVertex("A");
        network.addVertex("B");

        assertThrows(IllegalArgumentException.class, () -> network.shortestPathWeight("A", "C"));
    }
}
