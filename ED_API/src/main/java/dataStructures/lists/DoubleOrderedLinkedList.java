package dataStructures.lists;

import dataStructures.exceptions.NonComparableElementException;
import dataStructures.nodes.DoubleNode;

/**
 * DoublyOrderedLinkedList represents a doubly linked list that maintains order
 * based on the natural ordering of elements (implements Comparable).
 *
 * @param <T> the type of elements stored in the list, must implement Comparable
 */
public class DoubleOrderedLinkedList<T> extends DoubleLinkedList<T> implements OrderedListADT<T> {

    /**
     * Constructs an empty DoublyOrderedLinkedList.
     */
    public DoubleOrderedLinkedList() {
        super();
    }

    /**
     * Adds the specified element to the doubly ordered linked list in a sorted manner.
     * The list maintains order based on the natural ordering of elements (implements Comparable).
     *
     * @param element the element to be added to the list
     * @throws NonComparableElementException if the element does not implement Comparable
     */
    @Override
    public void add(T element) {
        if (!(element instanceof Comparable)) {
            throw new NonComparableElementException("Element must implement Comparable");
        }

        DoubleNode<T> newNode = new DoubleNode<>(element);
        boolean added = false;

        if (isEmpty()) {
            setFront(newNode);
            setRear(newNode);
            added = true;
        } else if (((Comparable<T>) element).compareTo(getFront().getData()) <= 0) {
            newNode.setNext(getFront());
            getFront().setPrevious(newNode);
            setFront(newNode);
            added = true;
        } else if (((Comparable<T>) element).compareTo(getRear().getData()) >= 0) {
            newNode.setPrevious(getRear());
            getRear().setNext(newNode);
            setRear(newNode);
            added = true;
        }

        if (!added) {
            DoubleNode<T> current = getFront();
            while (current != null && ((Comparable<T>) element).compareTo(current.getData()) > 0) {
                current = current.getNext();
            }
            newNode.setPrevious(current.getPrevious());
            newNode.setNext(current);
            current.getPrevious().setNext(newNode);
            current.setPrevious(newNode);
        }

        setSize(getSize() + 1);
        setModCount(getModCount() + 1);
    }


}
