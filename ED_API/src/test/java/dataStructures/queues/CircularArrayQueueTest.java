package dataStructures.queues;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class CircularArrayQueueTest {

    @Test
    void testEnqueueAndFirst() {
        CircularArrayQueue<Integer> queue = new CircularArrayQueue<>();

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
    void testDequeue() {
        CircularArrayQueue<String> queue = new CircularArrayQueue<>();

        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        assertEquals("A", queue.dequeue());
        assertEquals(2, queue.size());
        assertEquals("B", queue.dequeue());
        assertEquals(1, queue.size());
        assertEquals("C", queue.dequeue());
        assertTrue(queue.isEmpty());

        assertThrows(NoSuchElementException.class, queue::dequeue);
    }

    @Test
    void testFirst() {
        CircularArrayQueue<Double> queue = new CircularArrayQueue<>();

        queue.enqueue(1.1);
        queue.enqueue(2.2);

        assertEquals(1.1, queue.first());
        queue.dequeue();
        assertEquals(2.2, queue.first());

        queue.dequeue();
        assertThrows(NoSuchElementException.class, queue::first);
    }

    @Test
    void testIsEmpty() {
        CircularArrayQueue<Character> queue = new CircularArrayQueue<>();

        assertTrue(queue.isEmpty());

        queue.enqueue('X');
        assertFalse(queue.isEmpty());

        queue.dequeue();
        assertTrue(queue.isEmpty());
    }

    @Test
    void testSize() {
        CircularArrayQueue<Integer> queue = new CircularArrayQueue<>();

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
        CircularArrayQueue<Integer> queue = new CircularArrayQueue<>();

        queue.enqueue(10);
        queue.enqueue(20);

        String toStringOutput = queue.toString();
        assertTrue(toStringOutput.contains("queue"));
        assertTrue(toStringOutput.contains("size=2"));
    }
}
