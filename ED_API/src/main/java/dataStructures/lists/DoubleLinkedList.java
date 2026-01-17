package dataStructures.lists;

import dataStructures.exceptions.EmptyCollectionException;
import dataStructures.nodes.DoubleNode;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * The DoublyLinkedList class represents a doubly linked list data structure.
 * It supports various operations such as adding, removing, and checking the presence of elements.
 *
 * @param <T> the type of elements stored in the list
 */
public abstract class DoubleLinkedList<T> implements ListADT<T> {
    private DoubleNode<T> front;
    private DoubleNode<T> rear;
    private int size;
    private int modCount;

    /**
     * Constructs an empty DoublyLinkedList.
     */
    public DoubleLinkedList() {
        front = new DoubleNode<>(null);
        rear = new DoubleNode<>(null);
        size = 0;
        modCount = 0;
    }

    /**
     * Gets the modification count of the list.
     *
     * @return the modification count
     */
    public int getModCount() {
        return modCount;
    }

    /**
     * Sets the modification count of the list.
     *
     * @param modCount the new modification count
     */
    public void setModCount(int modCount) {
        this.modCount = modCount;
    }

    /**
     * Removes and returns the first element from the doubly linked list.
     *
     * @return the first element of the list
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("A lista está vazia");
        }

        T elemento = front.getData();
        if (size == 1) {
            front = null;
            rear = null;
        } else {
            front = front.getNext();
            front.setPrevious(null);
        }
        size--;
        modCount++;

        return elemento;
    }

    /**
     * Removes and returns the last element from the doubly linked list.
     *
     * @return the last element of the list
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("A lista está vazia");
        }

        T elemento = rear.getData();
        if (size == 1) {
            front = null;
            rear = null;
        } else {
            rear = rear.getPrevious();
            rear.setNext(null);
        }
        size--;
        modCount++;

        return elemento;
    }

    /**
     * Removes the specified element from the doubly linked list.
     *
     * @param element the element to be removed
     * @return the removed element
     * @throws IllegalStateException  if the list is empty
     * @throws NoSuchElementException if the specified element is not found in the list
     */
    @Override
    public T remove(T element) throws EmptyCollectionException {
        if (isEmpty()) {
            throw new IllegalStateException("A lista está vazia");
        }

        if (!contains(element)) {
            throw new EmptyCollectionException("A lista não contém esse elemento");
        }

        DoubleNode<T> current = front;
        T removedData = null;
        while (current != null && !current.getData().equals(element)) {
            current = current.getNext();
        }

        if (current != null) {
            if (current == front) {
                removedData = removeFirst();
            } else if (current == rear) {
                removedData = removeLast();
            } else {
                current.getPrevious().setNext(current.getNext());
                current.getNext().setPrevious(current.getPrevious());
                size--;
                modCount++;
                removedData = current.getData();
            }
        }

        return removedData;
    }

    /**
     * Returns the first element of the doubly linked list.
     *
     * @return the first element of the list
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("A lista está vazia");
        }

        return front.getData();
    }

    /**
     * Returns the last element of the doubly linked list.
     *
     * @return the last element of the list
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("A lista está vazia");
        }

        return rear.getData();
    }

    /**
     * Checks whether the doubly linked list contains the specified element.
     *
     * @param target the element to check for
     * @return true if the element is found, false otherwise
     */
    @Override
    public boolean contains(T target) {
        for (T current : this) {
            if (current == null && target == null) {
                return true;
            }
            if (current != null && current.equals(target)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the doubly linked list is empty.
     *
     * @return true if the list is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }

        DoubleNode<T> current = this.getFront();
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        return current.getData();
    }

    /**
     * Returns the size of the doubly linked list.
     *
     * @return the size of the list
     */
    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new OrderedListIterator();
    }

    /**
     * Returns a string representation of the DoublyLinkedList.
     *
     * @return a string representation of the list
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DoubleLinkedList{");
        sb.append("front=").append(front);
        sb.append(", rear=").append(rear);
        sb.append(", size=").append(size);
        sb.append(", modCount=").append(modCount);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Gets the front node of the list.
     *
     * @return the front node
     */
    DoubleNode<T> getFront() {
        return front;
    }

    /**
     * Sets the front node of the list.
     *
     * @param front the new front node
     */
    public void setFront(DoubleNode<T> front) {
        this.front = front;
    }

    /**
     * Gets the rear node of the list.
     *
     * @return the rear node
     */
    public DoubleNode<T> getRear() {
        return rear;
    }

    /**
     * Sets the rear node of the list.
     *
     * @param rear the new rear node
     */
    public void setRear(DoubleNode<T> rear) {
        this.rear = rear;
    }

    /**
     * Gets the size of the list.
     *
     * @return the size of the list
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the size of the list.
     *
     * @param size the new size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Iterator for the DoublyLinkedList that iterates through the list in order.
     */
    private class OrderedListIterator implements Iterator<T> {
        private DoubleNode<T> current = front;
        private int remainingElements = size;
        private int expectedModCount = modCount;

        /**
         * Checks if there is a next element in the iteration.
         *
         * @return true if there is a next element, false otherwise
         */
        @Override
        public boolean hasNext() {
            return (remainingElements > 0);
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element
         * @throws ConcurrentModificationException if the list is modified during iteration
         * @throws NoSuchElementException          if there are no more elements in the list
         */
        @Override
        public T next() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException("A lista foi modificada durante a iteração");
            }
            if (!hasNext()) {
                throw new NoSuchElementException("Não há mais elementos na lista");
            }

            T element = current.getData();
            current = current.getNext();
            if (current == null) {
                current = front;
            }
            remainingElements--;

            return element;
        }
    }
}
