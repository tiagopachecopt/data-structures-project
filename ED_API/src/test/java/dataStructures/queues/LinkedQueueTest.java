package dataStructures.queues;

import dataStructures.exceptions.EmptyCollectionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedQueueTest {

    @Test
    void testEnqueueAndFirst() throws EmptyCollectionException {
        LinkedQueue<Integer> queue = new LinkedQueue<>();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());

        queue.enqueue(1);
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());
        assertEquals(1, queue.first());

        queue.enqueue(2);
        assertEquals(2, queue.size());
        assertEquals(1, queue.first());
    }

    @Test
    void testDequeue() throws EmptyCollectionException {
        LinkedQueue<String> queue = new LinkedQueue<>();

        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        assertEquals("A", queue.dequeue());
        assertEquals(2, queue.size());
        assertEquals("B", queue.dequeue());
        assertEquals(1, queue.size());
        assertEquals("C", queue.dequeue());
        assertTrue(queue.isEmpty());

        assertThrows(EmptyCollectionException.class, queue::dequeue);
    }

    @Test
    void testFirst() throws EmptyCollectionException {
        LinkedQueue<Double> queue = new LinkedQueue<>();

        queue.enqueue(1.1);
        queue.enqueue(2.2);

        assertEquals(1.1, queue.first());
        queue.dequeue();
        assertEquals(2.2, queue.first());

        queue.dequeue();
        assertThrows(EmptyCollectionException.class, queue::first);
    }

    @Test
    void testIsEmpty() throws EmptyCollectionException {
        LinkedQueue<Character> queue = new LinkedQueue<>();

        assertTrue(queue.isEmpty());

        queue.enqueue('X');
        assertFalse(queue.isEmpty());

        queue.dequeue();
        assertTrue(queue.isEmpty());
    }

    @Test
    void testSize() throws EmptyCollectionException {
        LinkedQueue<Integer> queue = new LinkedQueue<>();

        assertEquals(0, queue.size());

        queue.enqueue(100);
        queue.enqueue(200);
        queue.enqueue(300);

        assertEquals(3, queue.size());

        queue.dequeue();
        assertEquals(2, queue.size());
    }

    @Test
    void testToString() {
        LinkedQueue<Integer> queue = new LinkedQueue<>();

        queue.enqueue(10);
        queue.enqueue(20);

        String toStringOutput = queue.toString();
        assertTrue(toStringOutput.contains("head"));
        assertTrue(toStringOutput.contains("tail"));
        assertTrue(toStringOutput.contains("size=2"));
    }
}
