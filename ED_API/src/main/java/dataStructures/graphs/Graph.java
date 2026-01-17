package dataStructures.graphs;

import dataStructures.exceptions.EmptyCollectionException;
import dataStructures.lists.DoubleUnorderedLinkedList;
import dataStructures.queues.LinkedQueue;
import dataStructures.stacks.LinkedStack;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Graph represents an adjacency matrix implementation of a graph.
 *
 * @param <T> the type of elements stored in the vertices of the graph
 */
public class Graph<T> implements GraphADT<T> {
    protected static final int DEFAULT_CAPACITY = 10;
    private static final int DEFAULT_MULTIPLIER = 2;
    protected int numVertices;
    protected boolean[][] adjMatrix;
    protected T[] vertices;

    /**
     * Creates an empty graph with a specified initial capacity.
     *
     * @param num the initial capacity of the graph
     */
    public Graph(int num) {
        numVertices = 0;
        this.adjMatrix = new boolean[num][num];
        this.vertices = (T[]) (new Object[num]);

    }

    /**
     * Creates an empty graph.
     */
    public Graph() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Inserts an edge between two vertices of the graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @throws IllegalArgumentException if either vertex is invalid
     */
    public void addEdge(T vertex1, T vertex2) {
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);

        if (!indexIsValid(index1) || !indexIsValid(index2)) {
            throw new IllegalArgumentException("Invalid vertex: " + vertex1 + " or " + vertex2);
        }

        adjMatrix[index1][index2] = true;
        adjMatrix[index2][index1] = true;
    }

    /**
     * Checks if there is an edge between two vertices.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if there is an edge, false otherwise
     * @throws IllegalArgumentException if either vertex is invalid
     */
    public boolean isAdjacent(T vertex1, T vertex2) {
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);

        if (!indexIsValid(index1) || !indexIsValid(index2)) {
            throw new IllegalArgumentException("Invalid vertex: " + vertex1 + " or " + vertex2);
        }

        return adjMatrix[index1][index2];
    }


    /**
     * Adds a vertex to the graph, expanding the capacity of the graph if necessary.
     * It also associates an object with the vertex.
     *
     * @param vertex the vertex to add to the graph
     * @throws IllegalArgumentException if the vertex already exists in the graph
     */
    public void addVertex(T vertex) {

        if (contains(vertex)) {
            throw new IllegalArgumentException("Vertex already exists in the graph: " + vertex);
        }

        if (numVertices == vertices.length) {
            expandCapacity();
        }

        vertices[numVertices] = vertex;

        for (int i = 0; i <= numVertices; i++) {
            adjMatrix[numVertices][i] = false;
            adjMatrix[i][numVertices] = false;
        }

        numVertices++;
    }

    /**
     * Removes a vertex from the graph.
     *
     * @param vertex the vertex to remove
     */
    @Override
    public void removeVertex(T vertex) {
        int index = getIndex(vertex);

        if (indexIsValid(index)) {
            for (int i = index; i < numVertices - 1; i++) {
                vertices[i] = vertices[i + 1];
            }

            for (int i = index; i < numVertices - 1; i++) {
                for (int j = 0; j < numVertices; j++) {
                    adjMatrix[i][j] = adjMatrix[i + 1][j];
                }
            }

            for (int i = 0; i < numVertices; i++) {
                for (int j = index; j < numVertices - 1; j++) {
                    adjMatrix[i][j] = adjMatrix[i][j + 1];
                }
            }

            for (int i = 0; i < numVertices; i++) {
                adjMatrix[i][numVertices - 1] = false;
                adjMatrix[numVertices - 1][i] = false;
            }

            numVertices--;
        }
    }

    /**
     * Removes an edge between two vertices of the graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @throws IllegalArgumentException if either vertex is invalid
     */
    @Override
    public void removeEdge(T vertex1, T vertex2) {
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);

        if (!indexIsValid(index1) || !indexIsValid(index2)) {
            throw new IllegalArgumentException("Invalid vertex for edge removal");
        }

        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = false;
        }
    }

    /**
     * Returns an iterator for a breadth-first traversal starting from the specified vertex.
     *
     * @param startVertex the starting vertex for the traversal
     * @return an iterator for breadth-first traversal
     * @throws IllegalArgumentException if the start vertex is not found in the graph
     */
    @Override
    public Iterator<T> iteratorBFS(T startVertex) throws EmptyCollectionException {
        if (!contains(startVertex)) {
            throw new IllegalArgumentException("Start vertex not found in the graph");
        }

        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        DoubleUnorderedLinkedList<T> resultList = new DoubleUnorderedLinkedList<>();
        boolean[] visited = new boolean[numVertices];

        int startIndex = getIndex(startVertex);
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;

        while (!traversalQueue.isEmpty()) {
            int x = traversalQueue.dequeue();
            resultList.addToRear(vertices[x]);

            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[x][i] && !visited[i]) {
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }

        return resultList.iterator();
    }


    /**
     * Returns an iterator for a depth-first traversal starting from the specified vertex.
     *
     * @param startVertex the starting vertex for the traversal
     * @return an iterator for depth-first traversal
     * @throws IllegalArgumentException if the start vertex is not found in the graph
     */
    @Override
    public Iterator<T> iteratorDFS(T startVertex) throws EmptyCollectionException {
        if (!contains(startVertex)) {
            throw new IllegalArgumentException("Start vertex not found in the graph");
        }

        LinkedStack<Integer> traversalStack = new LinkedStack<>();
        DoubleUnorderedLinkedList<T> resultList = new DoubleUnorderedLinkedList<>();
        boolean[] visited = new boolean[numVertices];

        int startIndex = getIndex(startVertex);
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalStack.push(startIndex);
        resultList.addToRear(vertices[startIndex]);
        visited[startIndex] = true;

        while (!traversalStack.isEmpty()) {
            int x = traversalStack.peek();
            boolean found = false;

            for (int i = 0; i < numVertices && !found; i++) {
                if (adjMatrix[x][i] && !visited[i]) {
                    traversalStack.push(i);
                    resultList.addToRear(vertices[i]);
                    visited[i] = true;
                    found = true;
                }
            }

            if (!found && !traversalStack.isEmpty()) {
                traversalStack.pop();
            }
        }

        return resultList.iterator();
    }

    /**
     * Returns an iterator for the shortest path between two vertices.
     *
     * @param startVertex  the starting vertex
     * @param targetVertex the target vertex
     * @return an iterator for the shortest path
     * @throws IllegalArgumentException if either start or target vertex is invalid
     */
    @Override
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) throws EmptyCollectionException {
        int startIndex = getIndex(startVertex);
        int targetIndex = getIndex(targetVertex);

        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            throw new IllegalArgumentException("Invalid start or target vertex");
        }

        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        DoubleUnorderedLinkedList<T> resultList = new DoubleUnorderedLinkedList<>();
        boolean[] visited = new boolean[numVertices];
        int[] previousVertices = new int[numVertices];

        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
            previousVertices[i] = -1;
        }

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;

        while (!traversalQueue.isEmpty()) {
            int currentVertex = traversalQueue.dequeue();

            if (currentVertex == targetIndex) {
                break;
            }

            for (int adjacentVertex = 0; adjacentVertex < numVertices; adjacentVertex++) {
                if (adjMatrix[currentVertex][adjacentVertex] && !visited[adjacentVertex]) {
                    traversalQueue.enqueue(adjacentVertex);
                    visited[adjacentVertex] = true;
                    previousVertices[adjacentVertex] = currentVertex;
                }
            }
        }

        if (visited[targetIndex]) {
            int backtrackVertex = targetIndex;
            while (backtrackVertex != -1) {
                resultList.addToFront(vertices[backtrackVertex]);
                backtrackVertex = previousVertices[backtrackVertex];
            }
        }

        return resultList.iterator();
    }

    /**
     * Checks if the graph is empty.
     *
     * @return true if the graph is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return numVertices == 0;
    }

    /**
     * Checks if the graph is connected.
     *
     * @return true if the graph is connected, false otherwise
     */
    public boolean isConnected() throws EmptyCollectionException {
        boolean result = true;

        if (numVertices <= 1) {
            result = true;
        } else {
            boolean[] visited = new boolean[numVertices];
            LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();

            traversalQueue.enqueue(0);
            visited[0] = true;

            while (!traversalQueue.isEmpty()) {
                int currentVertex = traversalQueue.dequeue();

                for (int j = 0; j < numVertices; j++) {
                    if (adjMatrix[currentVertex][j] && !visited[j]) {
                        traversalQueue.enqueue(j);
                        visited[j] = true;
                    }
                }
            }

            for (boolean vertexVisited : visited) {
                if (!vertexVisited) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }


    /**
     * Returns the number of vertices in the graph.
     *
     * @return the number of vertices in the graph
     */
    @Override
    public int size() {
        return numVertices;
    }

    /**
     * Expands the capacity of the graph by doubling the size of the adjacency matrix
     * and the vertices array.
     */
    private void expandCapacity() {
        int newCapacity = vertices.length * DEFAULT_MULTIPLIER;

        T[] newVertices = (T[]) new Object[newCapacity];
        System.arraycopy(vertices, 0, newVertices, 0, vertices.length);
        vertices = newVertices;

        boolean[][] newAdjMatrix = new boolean[newCapacity][newCapacity];
        for (int i = 0; i < numVertices; i++) {
            System.arraycopy(adjMatrix[i], 0, newAdjMatrix[i], 0, numVertices);
        }
        adjMatrix = newAdjMatrix;
    }


    /**
     * Returns the index of a given vertex in the vertices array.
     *
     * @param vertex the vertex to find the index of
     * @return the index of the vertex
     * @throws NoSuchElementException if the vertex is not found
     */
    public int getIndex(T vertex) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].equals(vertex)) {
                return i;
            }
        }

        throw new NoSuchElementException("Vertex not found: " + vertex);
    }

    /**
     * Returns the vertex based on the index
     *
     * @param index the index of the vertex to find
     * @return the vertex
     * @throws IndexOutOfBoundsException if the vertex is out of bounds
     */
    public T getVertex(int index) {
        if (indexIsValid(index)) {
            return vertices[index];
        } else {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    /**
     * Returns a double unordered linked list with all the vertices
     *
     * @return a double unordered linked list with all the vertices
     */
    public DoubleUnorderedLinkedList<T> getVertices() {
        DoubleUnorderedLinkedList<T> verticesList = new DoubleUnorderedLinkedList<>();
        for (int i = 0; i < numVertices; i++) {
            verticesList.addToRear(vertices[i]);
        }
        return verticesList;
    }

    /**
     * Returns a double unordered linked list with the adjacent vertices
     *
     * @return a double unordered linked list with all the adjacent vertices
     */
    public DoubleUnorderedLinkedList<T> getAdjacentVertices(T vertex) throws EmptyCollectionException {
        int index = getIndex(vertex);
        if (!indexIsValid(index)) {
            throw new IllegalArgumentException("Vertex not found in the graph: " + vertex);
        }

        DoubleUnorderedLinkedList<T> adjacentVertices = new DoubleUnorderedLinkedList<>();
        for (int i = 0; i < numVertices; i++) {
            if (adjMatrix[index][i]) {
                adjacentVertices.addToRear(vertices[i]);
            }
        }
        return adjacentVertices;
    }

    /**
     * Checks if the given index is valid for the vertices array.
     *
     * @param index the index to check
     * @return true if the index is valid, false otherwise
     */
    protected boolean indexIsValid(int index) {
        return index >= 0 && index < numVertices;
    }

    /**
     * Checks whether the graph contains the specified vertex.
     *
     * @param vertex the vertex to check for existence in the graph
     * @return true if the graph contains the vertex, false otherwise
     */
    protected boolean contains(T vertex) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i] != null && vertices[i].equals(vertex)) {
                return true;
            }
        }
        return false;
    }

}