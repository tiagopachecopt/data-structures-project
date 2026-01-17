package dataStructures.queues;

import dataStructures.exceptions.EmptyCollectionException;
import dataStructures.nodes.Node;

/**
 * LinkedQueue represents a linked implementation of a queue.
 *
 * @param <T> the type of elements stored in the queue
 */
public class LinkedQueue<T> implements QueueADT<T> {

    private final String EMPTY_ERROR = "This queue is empty";

    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * Creates an empty linked queue.
     */
    public LinkedQueue() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Adds the specified element to the rear of the queue.
     *
     * @param element the element to be added to the queue
     */
    @Override
    public void enqueue(T element) {

        Node<T> newNode = new Node<>(element);

        if (isEmpty()) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }

        tail = newNode;
        size++;
    }

    /**
     * Removes and returns the element at the front of the queue.
     *
     * @return the element removed from the front of the queue
     * @throws EmptyCollectionException if the queue is empty
     */
    @Override
    public T dequeue() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException(EMPTY_ERROR);
        }

        T removed = head.getData();
        head = head.getNext();
        size--;

        return removed;
    }

    /**
     * Returns the element at the front of the queue without removing it.
     *
     * @return the element at the front of the queue
     * @throws EmptyCollectionException if the queue is empty
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException(EMPTY_ERROR);
        }

        return head.getData();
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
     * Returns a string representation of the linked queue.
     *
     * @return a string representation of the linked queue
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LinkedQueue{");
        sb.append("EMPTY_ERROR='").append(EMPTY_ERROR).append('\'');
        sb.append(", head=").append(head);
        sb.append(", tail=").append(tail);
        sb.append(", size=").append(size);
        sb.append('}');
        return sb.toString();
    }
}