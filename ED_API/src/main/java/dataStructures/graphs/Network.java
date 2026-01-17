package dataStructures.graphs;

import dataStructures.lists.DoubleUnorderedLinkedList;

import java.util.Iterator;

/**
 * Network represents an adjacency matrix implementation of a weighted graph.
 *
 * @param <T> the type of elements stored in the vertices of the network
 */
public class Network<T> extends Graph<T> implements NetworkADT<T> {
    private double[][] weightMatrix;

    /**
     * Creates an empty network.
     */
    public Network() {
        super();
        this.weightMatrix = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
    }

    /**
     * Creates an empty network with a specified initial capacity.
     *
     * @param num the initial capacity of the network
     */
    public Network(int num) {
        super(num);
        this.weightMatrix = new double[num][num];
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                weightMatrix[i][j] = Double.POSITIVE_INFINITY;
            }
        }
    }

    /**
     * Inserts an undirected edge with a weight between two vertices of the network.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @param weight  the weight of the edge
     */
    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);

        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = true;
            adjMatrix[index2][index1] = true;
            weightMatrix[index1][index2] = weight;
            weightMatrix[index2][index1] = weight;
        }
    }

    /**
     * Updates the weight of an edge.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @param newWeight the new weight for the edge
     */
    public void updateEdgeWeight(T vertex1, T vertex2, double newWeight) {
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);

        if (indexIsValid(index1) && indexIsValid(index2)) {
            weightMatrix[index1][index2] = newWeight;
            weightMatrix[index2][index1] = newWeight;
        }
    }

    /**
     * Returns the weight of the edge between two vertices.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return the weight of the edge
     */
    public double getEdgeWeight(T vertex1, T vertex2) {
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);

        if (indexIsValid(index1) && indexIsValid(index2)) {
            return weightMatrix[index1][index2];
        }
        return Double.POSITIVE_INFINITY;
    }

    /**
     * Calculates the dijkstra algorithm.
     *
     * @param startIndex the start index
     * @param previousVertices the array of values of previous vertices.
     * @return and array of double with the distances.
     */
    protected double[] dijkstra(int startIndex, int[] previousVertices) {
        double[] distances = new double[numVertices];
        boolean[] tight = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            distances[i] = Double.POSITIVE_INFINITY;
            tight[i] = false;
            previousVertices[i] = -1;
        }
        distances[startIndex] = 0;

        while (true) {
            int u = -1;
            double minDistance = Double.POSITIVE_INFINITY;

            for (int i = 0; i < numVertices; i++) {
                if (!tight[i] && distances[i] < minDistance) {
                    u = i;
                    minDistance = distances[i];
                }
            }

            if (u == -1) break;

            tight[u] = true;

            for (int z = 0; z < numVertices; z++) {
                if (adjMatrix[u][z] && !tight[z]) {
                    double newDistance = distances[u] + weightMatrix[u][z];
                    if (newDistance < distances[z]) {
                        distances[z] = newDistance;
                        previousVertices[z] = u;
                    }
                }
            }
        }

        return distances;
    }


    /**
     * Returns the weight of the shortest path in this network.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return the weight of the shortest path in this network
     */
    @Override
    public double shortestPathWeight(T vertex1, T vertex2) {
        int startIndex = getIndex(vertex1);
        int targetIndex = getIndex(vertex2);

        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            throw new IllegalArgumentException("Invalid start or target vertex");
        }

        int[] previousVertices = new int[numVertices];
        double[] distances = dijkstra(startIndex, previousVertices);

        return distances[targetIndex];
    }


    /**
     * Returns the weight of the edge between two vertices, given their indices.
     *
     * @param index1 the index of the first vertex
     * @param index2 the index of the second vertex
     * @return the weight of the edge, or Double.POSITIVE_INFINITY if no edge exists
     */
    public double getWeightMatrixValue(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            return weightMatrix[index1][index2];
        }
        return Double.POSITIVE_INFINITY;
    }

    /**
     * Returns an iterator over the vertices in the shortest path from startVertex to targetVertex.
     *
     * @param startVertex  the starting vertex
     * @param targetVertex the target vertex
     * @return an iterator over the vertices in the shortest path
     */
    @Override
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) {
        int startIndex = getIndex(startVertex);
        int targetIndex = getIndex(targetVertex);

        int[] previousVertices = new int[numVertices];
        dijkstra(startIndex, previousVertices);

        DoubleUnorderedLinkedList<T> path = new DoubleUnorderedLinkedList<>();
        for (int at = targetIndex; at != -1; at = previousVertices[at]) {
            path.addToFront(vertices[at]);
        }

        return path.iterator();
    }
}
