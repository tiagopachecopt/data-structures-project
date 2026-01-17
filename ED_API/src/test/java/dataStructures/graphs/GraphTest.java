package dataStructures.graphs;

import dataStructures.exceptions.EmptyCollectionException;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @Test
    void testAddVertex() {
        Graph<String> graph = new Graph<>();

        graph.addVertex("A");
        assertEquals(1, graph.size());
        assertTrue(graph.contains("A"));

        graph.addVertex("B");
        assertEquals(2, graph.size());
        assertTrue(graph.contains("B"));
    }

    @Test
    void testAddEdge() {
        Graph<String> graph = new Graph<>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        assertTrue(graph.isAdjacent("A", "B"));
        assertTrue(graph.isAdjacent("B", "A"));
    }

    @Test
    void testRemoveVertex() {
        Graph<String> graph = new Graph<>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        graph.removeVertex("A");
        assertEquals(1, graph.size());
        assertFalse(graph.contains("A"));
        assertThrows(IllegalArgumentException.class, () -> graph.isAdjacent("A", "B"));
    }

    @Test
    void testRemoveEdge() {
        Graph<String> graph = new Graph<>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        graph.removeEdge("A", "B");
        assertFalse(graph.isAdjacent("A", "B"));
    }

    @Test
    void testIteratorBFS() throws EmptyCollectionException {
        Graph<String> graph = new Graph<>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        Iterator<String> bfsIterator = graph.iteratorBFS("A");

        assertEquals("A", bfsIterator.next());
        assertEquals("B", bfsIterator.next());
        assertEquals("C", bfsIterator.next());
        assertFalse(bfsIterator.hasNext());
    }

    @Test
    void testIteratorDFS() throws EmptyCollectionException {
        Graph<String> graph = new Graph<>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        Iterator<String> dfsIterator = graph.iteratorDFS("A");

        assertEquals("A", dfsIterator.next());
        assertEquals("B", dfsIterator.next());
        assertEquals("C", dfsIterator.next());
        assertFalse(dfsIterator.hasNext());
    }

    @Test
    void testIteratorShortestPath() throws EmptyCollectionException {
        Graph<String> graph = new Graph<>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");

        Iterator<String> shortestPathIterator = graph.iteratorShortestPath("A", "D");

        assertEquals("A", shortestPathIterator.next());
        assertEquals("B", shortestPathIterator.next());
        assertEquals("C", shortestPathIterator.next());
        assertEquals("D", shortestPathIterator.next());
        assertFalse(shortestPathIterator.hasNext());
    }

    @Test
    void testIsConnected() throws EmptyCollectionException {
        Graph<String> graph = new Graph<>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        assertTrue(graph.isConnected());

        graph.addVertex("C");
        assertFalse(graph.isConnected());
    }

    @Test
    void testGetAdjacentVertices() throws EmptyCollectionException {
        Graph<String> graph = new Graph<>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");

        var adjacent = graph.getAdjacentVertices("A");
        assertTrue(adjacent.contains("B"));
        assertTrue(adjacent.contains("C"));
        assertFalse(adjacent.contains("D"));
    }
}