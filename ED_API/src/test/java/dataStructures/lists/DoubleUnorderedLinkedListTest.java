package dataStructures.lists;

import dataStructures.exceptions.EmptyCollectionException;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class DoubleUnorderedLinkedListTest {

    @Test
    void testAddToFront() throws EmptyCollectionException {
        DoubleUnorderedLinkedList<Integer> list = new DoubleUnorderedLinkedList<>();

        list.addToFront(10);
        assertEquals(1, list.size());
        assertEquals(10, list.first());
        assertEquals(10, list.last());

        list.addToFront(20);
        assertEquals(2, list.size());
        assertEquals(20, list.first());
        assertEquals(10, list.last());
    }

    @Test
    void testAddToRear() throws EmptyCollectionException {
        DoubleUnorderedLinkedList<Integer> list = new DoubleUnorderedLinkedList<>();

        list.addToRear(10);
        assertEquals(1, list.size());
        assertEquals(10, list.first());
        assertEquals(10, list.last());

        list.addToRear(20);
        assertEquals(2, list.size());
        assertEquals(10, list.first());
        assertEquals(20, list.last());
    }

    @Test
    void testAddAfter() throws EmptyCollectionException {
        DoubleUnorderedLinkedList<String> list = new DoubleUnorderedLinkedList<>();

        list.addToFront("A");
        list.addToRear("C");

        list.addAfter("B", "A");
        assertEquals(3, list.size());
        assertEquals("A", list.first());
        assertEquals("C", list.last());

        assertTrue(list.contains("B"));

        list.addAfter("D", "C");
        assertEquals(4, list.size());
        assertEquals("D", list.last());
    }

    @Test
    void testAddAfterThrowsException() {
        DoubleUnorderedLinkedList<String> list = new DoubleUnorderedLinkedList<>();

        list.addToFront("A");

        assertThrows(NoSuchElementException.class, () -> list.addAfter("B", "Z"));
    }

    @Test
    void testIsEmpty() {
        DoubleUnorderedLinkedList<Integer> list = new DoubleUnorderedLinkedList<>();

        assertTrue(list.isEmpty());

        list.addToFront(10);
        assertFalse(list.isEmpty());
    }

    @Test
    void testSize() throws EmptyCollectionException {
        DoubleUnorderedLinkedList<Integer> list = new DoubleUnorderedLinkedList<>();

        assertEquals(0, list.size());

        list.addToFront(10);
        list.addToRear(20);
        assertEquals(2, list.size());

        list.addAfter(15, 10);
        assertEquals(3, list.size());
    }

    @Test
    void testContains() {
        DoubleUnorderedLinkedList<String> list = new DoubleUnorderedLinkedList<>();

        list.addToFront("A");
        list.addToRear("B");

        assertTrue(list.contains("A"));
        assertTrue(list.contains("B"));
        assertFalse(list.contains("C"));
    }

    @Test
    void testToString() {
        DoubleUnorderedLinkedList<Integer> list = new DoubleUnorderedLinkedList<>();

        list.addToFront(10);
        list.addToRear(20);

        String toStringOutput = list.toString();
        assertTrue(toStringOutput.contains("front"));
        assertTrue(toStringOutput.contains("rear"));
        assertTrue(toStringOutput.contains("size=2"));
    }
}
