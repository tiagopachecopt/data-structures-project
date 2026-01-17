package dataStructures.heaps;

import dataStructures.exceptions.EmptyCollectionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedHeapTest {

    @Test
    void testAddElement() throws EmptyCollectionException {
        LinkedHeap<Integer> heap = new LinkedHeap<>();

        heap.addElement(10);
        assertEquals(10, heap.findMin());

        heap.addElement(5);
        assertEquals(5, heap.findMin());

        heap.addElement(15);
        assertEquals(5, heap.findMin());

        heap.addElement(2);
        assertEquals(2, heap.findMin());
    }

    @Test
    void testRemoveMin() throws EmptyCollectionException {
        LinkedHeap<Integer> heap = new LinkedHeap<>();

        heap.addElement(10);
        heap.addElement(5);
        heap.addElement(15);
        heap.addElement(2);

        assertEquals(2, heap.removeMin());
        assertEquals(5, heap.removeMin());
        assertEquals(10, heap.removeMin());
        assertEquals(15, heap.removeMin());

        assertThrows(EmptyCollectionException.class, heap::removeMin);
    }

    @Test
    void testFindMin() throws EmptyCollectionException {
        LinkedHeap<Integer> heap = new LinkedHeap<>();

        heap.addElement(20);
        heap.addElement(15);
        heap.addElement(30);

        assertEquals(15, heap.findMin());

        heap.removeMin();
        assertEquals(20, heap.findMin());

        heap.removeMin();
        assertEquals(30, heap.findMin());
    }

    @Test
    void testIsEmpty() {
        LinkedHeap<Integer> heap = new LinkedHeap<>();

        assertTrue(heap.isEmpty());

        heap.addElement(1);
        assertFalse(heap.isEmpty());

        try {
            heap.removeMin();
        } catch (EmptyCollectionException e) {
            fail("Unexpected EmptyCollectionException");
        }

        assertTrue(heap.isEmpty());
    }

    @Test
    void testHeapifyAdd() {
        LinkedHeap<Integer> heap = new LinkedHeap<>();

        heap.addElement(50);
        heap.addElement(30);
        heap.addElement(20);
        heap.addElement(10);

        assertDoesNotThrow(() -> {
            assertEquals(10, heap.findMin());
        });
    }

    @Test
    void testHeapifyRemove() throws EmptyCollectionException {
        LinkedHeap<Integer> heap = new LinkedHeap<>();

        heap.addElement(10);
        heap.addElement(20);
        heap.addElement(15);

        heap.removeMin();

        assertDoesNotThrow(() -> {
            assertEquals(15, heap.findMin());
        });
    }
}
