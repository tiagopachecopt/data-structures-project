package dataStructures.lists;

import dataStructures.exceptions.EmptyCollectionException;
import dataStructures.nodes.DoubleNode;

import java.util.NoSuchElementException;


/**
 * DoublyUnorderedLinkedList represents a doubly linked list that allows
 * unordered addition of elements.
 *
 * @param <T> the type of elements stored in the list
 */
public class DoubleUnorderedLinkedList<T> extends DoubleLinkedList<T> implements UnorderedListADT<T> {

    /**
     * Constructs an empty DoublyUnorderedLinkedList.
     */
    public DoubleUnorderedLinkedList() {
        super();
    }

    /**
     * Adds the specified element to the front of the list.
     *
     * @param element the element to be added to the front of the list
     */
    @Override
    public void addToFront(T element) {
        DoubleNode<T> novo = new DoubleNode<>(element);

        if (getSize() == 0) {
            setFront(novo);
            setRear(novo);
        } else {
            novo.setNext(getFront());
            getFront().setPrevious(novo);
            setFront(novo);
        }

        setSize(getSize() + 1);
        setModCount(getModCount() + 1);
    }

    /**
     * Adds the specified element to the rear of the list.
     *
     * @param element the element to be added to the rear of the list
     */
    @Override
    public void addToRear(T element) {
        DoubleNode<T> novo = new DoubleNode<>(element);

        if (getSize() == 0) {
            setFront(novo);
            setRear(novo);
        } else {
            getRear().setNext(novo);
            novo.setPrevious(getRear());
            setRear(novo);
        }

        setSize(getSize() + 1);
        setModCount(getModCount() + 1);
    }


    /**
     * Adds the specified element after the target element in the list.
     *
     * @param element the element to be added
     * @param target  the element after which the new element is added
     * @throws EmptyCollectionException if the target element is not found in the list
     */
    @Override
    public void addAfter(T element, T target) throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("A lista está vazia");
        }

        DoubleNode<T> current = getFront();
        DoubleNode<T> newNode = null;
        boolean targetFound = false;

        do {
            if (target.equals(current.getData())) {
                newNode = new DoubleNode<>(element);
                newNode.setPrevious(current);
                newNode.setNext(current.getNext());

                if (current.getNext() != null) {
                    current.getNext().setPrevious(newNode);
                }
                current.setNext(newNode);

                if (current == getRear()) {
                    setRear(newNode);
                }

                targetFound = true;
                break;
            }
            current = current.getNext();
        } while (current != getFront());

        if (!targetFound) {
            throw new NoSuchElementException("O elemento de destino não foi encontrado na lista");
        }

        setSize(getSize() + 1);
        setModCount(getModCount() + 1);
    }


}
