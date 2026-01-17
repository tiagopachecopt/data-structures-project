package dataStructures.queues;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * CircularArrayQueue represents a circular array implementation of a queue.
 *
 * @param <T> the type of elements stored in the queue
 */
public class CircularArrayQueue<T> implements QueueADT<T> {

    private static final int DEFAULT_CAPACITY = 10;
    private static final int DEFAULT_MULTIPLIER = 2;

    private final String EMPTY_ERROR = "This queue is empty";

    private T[] queue;
    private int front;
    private int rear;
    private int size;


    /**
     * Creates an empty circular array queue with the specified capacity.
     *
     * @param num the initial capacity of the queue
     */
    public CircularArrayQueue(int num) {
        queue = (T[]) new Object[num];
        front = 0;
        rear = 0;
        size = 0;
    }

    /**
     * Creates an empty circular array queue with the default capacity.
     */
    public CircularArrayQueue() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Expands the capacity of the circular array queue.
     */
    private void expandCapacity() {

        T[] newQueue = (T[]) new Object[queue.length * DEFAULT_MULTIPLIER];

        for (int i = 0; i < size; i++) {
            newQueue[i] = queue[(front + i) % queue.length];
        }

        queue = newQueue;
        front = 0;
        rear = size;
    }

    /**
     * Adds the specified element to the rear of the queue.
     *
     * @param element the element to be added to the queue
     */
    @Override
    public void enqueue(T element) {

        if (size == queue.length) {
            expandCapacity();
        }

        queue[rear] = element;
        rear = (rear + 1) % queue.length;
        size++;
    }

    /**
     * Removes and returns the element at the front of the queue.
     *
     * @return the element removed from the front of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException(EMPTY_ERROR);
        }

        T element = queue[front];

        queue[front] = null;
        front = (front + 1) % queue.length;
        size--;

        return element;
    }


    /**
     * Returns the element at the front of the queue without removing it.
     *
     * @return the element at the front of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    @Override
    public T first() {
        if (isEmpty()) {
            throw new NoSuchElementException(EMPTY_ERROR);
        }

        return queue[front];
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of elements in the queue.
     *
     * @return the number of elements in the queue
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns a string representation of the circular array queue.
     *
     * @return a string representation of the circular array queue
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CircularArrayQueue{");
        sb.append("queue=").append(Arrays.toString(queue));
        sb.append(", front=").append(front);
        sb.append(", rear=").append(rear);
        sb.append(", size=").append(size);
        sb.append(", DEFAULT_CAPACITY=").append(DEFAULT_CAPACITY);
        sb.append('}');
        return sb.toString();
    }
}
