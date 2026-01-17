package dataStructures.lists;

import dataStructures.exceptions.EmptyCollectionException;
import dataStructures.nodes.DoubleNode;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class DoubleLinkedListTest {

    @Test
    void testRemoveFirst() throws EmptyCollectionException {
        DoubleLinkedList<Integer> list = new DoubleLinkedList<>() {
        };

        list.setFront(new DoubleNode<>(1));
        list.setRear(new DoubleNode<>(2));
        list.getFront().setNext(list.getRear());
        list.getRear().setPrevious(list.getFront());
        list.setSize(2);

        assertEquals(1, list.removeFirst());
        assertEquals(1, list.size());
        assertEquals(2, list.removeFirst());
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());

        assertThrows(EmptyCollectionException.class, list::removeFirst);
    }

    @Test
    void testRemoveLast() throws EmptyCollectionException {
        DoubleLinkedList<Integer> list = new DoubleLinkedList<>() {
        };

        list.setFront(new DoubleNode<>(1));
        list.setRear(new DoubleNode<>(2));
        list.getFront().setNext(list.getRear());
        list.getRear().setPrevious(list.getFront());
        list.setSize(2);

        assertEquals(2, list.removeLast());
        assertEquals(1, list.size());
        assertEquals(1, list.removeLast());
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());

        assertThrows(EmptyCollectionException.class, list::removeLast);
    }

    @Test
    void testRemoveElement() throws EmptyCollectionException {
        DoubleLinkedList<String> list = new DoubleLinkedList<>() {
        };

        list.setFront(new DoubleNode<>("A"));
        DoubleNode<String> middle = new DoubleNode<>("B");
        list.setRear(new DoubleNode<>("C"));
        list.getFront().setNext(middle);
        middle.setPrevious(list.getFront());
        middle.setNext(list.getRear());
        list.getRear().setPrevious(middle);
        list.setSize(3);

        assertEquals("B", list.remove("B"));
        assertEquals(2, list.size());
        assertEquals("A", list.removeFirst());
        assertEquals("C", list.removeLast());
        assertTrue(list.isEmpty());

        assertThrows(NoSuchElementException.class, () -> list.remove("D"));
    }

    @Test
    void testFirstAndLast() throws EmptyCollectionException {
        DoubleLinkedList<Double> list = new DoubleLinkedList<>() {
        };

        list.setFront(new DoubleNode<>(1.1));
        list.setRear(new DoubleNode<>(2.2));
        list.getFront().setNext(list.getRear());
        list.getRear().setPrevious(list.getFront());
        list.setSize(2);

        assertEquals(1.1, list.first());
        assertEquals(2.2, list.last());

        list.removeFirst();
        list.removeLast();
        assertThrows(EmptyCollectionException.class, list::first);
        assertThrows(EmptyCollectionException.class, list::last);
    }

    @Test
    void testContains() {
        DoubleLinkedList<String> list = new DoubleLinkedList<>() {
        };

        list.setFront(new DoubleNode<>("A"));
        DoubleNode<String> middle = new DoubleNode<>("B");
        list.setRear(new DoubleNode<>("C"));
        list.getFront().setNext(middle);
        middle.setPrevious(list.getFront());
        middle.setNext(list.getRear());
        list.getRear().setPrevious(middle);
        list.setSize(3);

        assertTrue(list.contains("A"));
        assertTrue(list.contains("B"));
        assertTrue(list.contains("C"));
        assertFalse(list.contains("D"));
    }

    @Test
    void testIsEmpty() {
        DoubleLinkedList<Integer> list = new DoubleLinkedList<>() {
        };

        assertTrue(list.isEmpty());

        list.setFront(new DoubleNode<>(1));
        list.setRear(new DoubleNode<>(1));
        list.setSize(1);

        assertFalse(list.isEmpty());
    }

    @Test
    void testSize() {
        DoubleLinkedList<Character> list = new DoubleLinkedList<>() {
        };

        assertEquals(0, list.size());

        list.setFront(new DoubleNode<>('A'));
        list.setRear(new DoubleNode<>('B'));
        list.getFront().setNext(list.getRear());
        list.getRear().setPrevious(list.getFront());
        list.setSize(2);

        assertEquals(2, list.size());
    }

    @Test
    void testIterator() {
        DoubleLinkedList<Integer> list = new DoubleLinkedList<>() {
        };

        list.setFront(new DoubleNode<>(1));
        DoubleNode<Integer> middle = new DoubleNode<>(2);
        list.setRear(new DoubleNode<>(3));
        list.getFront().setNext(middle);
        middle.setPrevious(list.getFront());
        middle.setNext(list.getRear());
        list.getRear().setPrevious(middle);
        list.setSize(3);

        Iterator<Integer> iterator = list.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(2, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next());
        assertFalse(iterator.hasNext());

        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void testConcurrentModification() {
        DoubleLinkedList<String> list = new DoubleLinkedList<>() {
        };

        list.setFront(new DoubleNode<>("A"));
        list.setRear(new DoubleNode<>("B"));
        list.setSize(2);

        Iterator<String> iterator = list.iterator();

        list.setModCount(list.getModCount() + 1);

        assertThrows(ConcurrentModificationException.class, iterator::next);
    }
}