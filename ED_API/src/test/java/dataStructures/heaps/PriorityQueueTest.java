package dataStructures.heaps;

import dataStructures.exceptions.EmptyCollectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriorityQueueTest {

    private PriorityQueue<Integer> emptyQueue;
    private PriorityQueue<Integer> singleElementQueue;
    private PriorityQueue<Integer> multiElementQueue;

    @BeforeEach
    void setUp() {
        // Initialize an empty priority queue
        emptyQueue = new PriorityQueue<>();

        // Initialize a priority queue with a single element
        singleElementQueue = new PriorityQueue<>();
        singleElementQueue.addElement(10, 1); // Element 10 with priority 1

        // Initialize a priority queue with multiple elements
        multiElementQueue = new PriorityQueue<>();
        /*
            PriorityQueue Elements:
            Element 1 with priority 3
            Element 2 with priority 1
            Element 3 with priority 2
            Element 4 with priority 1
            Element 5 with priority 4
        */
        multiElementQueue.addElement(1, 3);
        multiElementQueue.addElement(2, 1);
        multiElementQueue.addElement(3, 2);
        multiElementQueue.addElement(4, 1);
        multiElementQueue.addElement(5, 4);
    }

    /**
     * Test creating an empty priority queue.
     */
    @Test
    void testEmptyQueue() {
        assertTrue(emptyQueue.isEmpty(), "Queue should be empty");
        assertEquals(0, emptyQueue.size(), "Size of empty queue should be 0");
        assertThrows(EmptyCollectionException.class, emptyQueue::removeNext,
                "Removing from empty queue should throw EmptyCollectionException");
    }

    /**
     * Test creating a priority queue with a single element.
     */
    @Test
    void testSingleElementQueue() throws EmptyCollectionException {
        assertFalse(singleElementQueue.isEmpty(), "Queue should not be empty");
        assertEquals(1, singleElementQueue.size(), "Size should be 1");
        assertEquals(10, singleElementQueue.removeNext(), "Should remove and return the only element (10)");
        assertTrue(singleElementQueue.isEmpty(), "Queue should be empty after removal");
    }

    /**
     * Test adding elements to the priority queue.
     */
    @Test
    void testAddElements() throws EmptyCollectionException {
        PriorityQueue<String> stringQueue = new PriorityQueue<>();
        assertTrue(stringQueue.isEmpty(), "String queue should be empty initially");

        stringQueue.addElement("apple", 2);
        assertFalse(stringQueue.isEmpty(), "String queue should not be empty after adding an element");
        assertEquals(1, stringQueue.size(), "Size should be 1 after adding one element");

        stringQueue.addElement("banana", 1);
        stringQueue.addElement("cherry", 3);
        assertEquals(3, stringQueue.size(), "Size should be 3 after adding three elements");

        // Verify the element with highest priority is dequeued first
        assertEquals("banana", stringQueue.removeNext(), "Should remove 'banana' with priority 1");
        assertEquals("apple", stringQueue.removeNext(), "Should remove 'apple' with priority 2");
        assertEquals("cherry", stringQueue.removeNext(), "Should remove 'cherry' with priority 3");
        assertTrue(stringQueue.isEmpty(), "String queue should be empty after removals");
    }

    /**
     * Test removing elements from a multi-element priority queue.
     */
    @Test
    void testRemoveElements() throws EmptyCollectionException {
        // Expected removal order based on priority:
        // Elements with priority 1: 2, 4
        // Element with priority 2: 3
        // Element with priority 3: 1
        // Element with priority 4: 5

        assertEquals(2, multiElementQueue.removeNext(), "Should remove element 2 with priority 1");
        assertEquals(4, multiElementQueue.removeNext(), "Should remove element 4 with priority 1");
        assertEquals(3, multiElementQueue.removeNext(), "Should remove element 3 with priority 2");
        assertEquals(1, multiElementQueue.removeNext(), "Should remove element 1 with priority 3");
        assertEquals(5, multiElementQueue.removeNext(), "Should remove element 5 with priority 4");
        assertTrue(multiElementQueue.isEmpty(), "Queue should be empty after removing all elements");
    }

    /**
     * Test removing elements from the queue with duplicate priorities.
     */
    @Test
    void testRemoveWithDuplicatePriorities() throws EmptyCollectionException {
        // Adding elements with duplicate priorities
        PriorityQueue<String> stringQueue = new PriorityQueue<>();
        stringQueue.addElement("apple", 2);
        stringQueue.addElement("banana", 1);
        stringQueue.addElement("cherry", 1);
        stringQueue.addElement("date", 3);

        // Expected removal order:
        // "banana" and "cherry" both have priority 1. Their removal order depends on the heap implementation.
        // Assuming it's a min-heap and elements are ordered by insertion for equal priorities.
        assertEquals("banana", stringQueue.removeNext(), "Should remove 'banana' with priority 1");
        assertEquals("cherry", stringQueue.removeNext(), "Should remove 'cherry' with priority 1");
        assertEquals("apple", stringQueue.removeNext(), "Should remove 'apple' with priority 2");
        assertEquals("date", stringQueue.removeNext(), "Should remove 'date' with priority 3");
        assertTrue(stringQueue.isEmpty(), "String queue should be empty after removals");
    }

    /**
     * Test the size of the priority queue after various operations.
     */
    @Test
    void testSize() throws EmptyCollectionException {
        PriorityQueue<String> stringQueue = new PriorityQueue<>();
        assertEquals(0, stringQueue.size(), "Initial size should be 0");

        stringQueue.addElement("apple", 2);
        assertEquals(1, stringQueue.size(), "Size should be 1 after adding one element");

        stringQueue.addElement("banana", 1);
        stringQueue.addElement("cherry", 3);
        assertEquals(3, stringQueue.size(), "Size should be 3 after adding three elements");

        stringQueue.removeNext();
        assertEquals(2, stringQueue.size(), "Size should be 2 after removing one element");

        stringQueue.removeNext();
        stringQueue.removeNext();
        assertEquals(0, stringQueue.size(), "Size should be 0 after removing all elements");
    }

    /**
     * Test exception when removing from an empty queue.
     */
    @Test
    void testRemoveFromEmptyQueue() {
        assertThrows(EmptyCollectionException.class, emptyQueue::removeNext,
                "Removing from empty queue should throw EmptyCollectionException");
    }

    /**
     * Test that the queue maintains correct ordering after multiple additions and removals.
     */
    @Test
    void testMultipleAdditionsAndRemovals() throws EmptyCollectionException {
        PriorityQueue<Integer> queue = new PriorityQueue<>();

        queue.addElement(5, 5);
        queue.addElement(3, 3);
        queue.addElement(4, 4);
        queue.addElement(1, 1);
        queue.addElement(2, 2);

        assertEquals(1, queue.removeNext(), "Should remove element 1 with priority 1");
        queue.addElement(6, 1);
        assertEquals(6, queue.removeNext(), "Should remove element 6 with priority 1");
        assertEquals(2, queue.removeNext(), "Should remove element 2 with priority 2");
        assertEquals(3, queue.removeNext(), "Should remove element 3 with priority 3");
        assertEquals(4, queue.removeNext(), "Should remove element 4 with priority 4");
        assertEquals(5, queue.removeNext(), "Should remove element 5 with priority 5");
        assertTrue(queue.isEmpty(), "Queue should be empty after all removals");
    }

    /**
     * Test adding elements with the same priority.
     */
    @Test
    void testAddElementsWithSamePriority() throws EmptyCollectionException {
        PriorityQueue<String> stringQueue = new PriorityQueue<>();
        stringQueue.addElement("apple", 2);
        stringQueue.addElement("banana", 2);
        stringQueue.addElement("cherry", 2);
        stringQueue.addElement("date", 2);

        assertEquals(4, stringQueue.size(), "Size should be 4 after adding four elements with same priority");

        // Removal order may depend on heap implementation; assuming insertion order is preserved for equal priorities
        assertEquals("apple", stringQueue.removeNext(), "Should remove 'apple' with priority 2");
        assertEquals("banana", stringQueue.removeNext(), "Should remove 'banana' with priority 2");
        assertEquals("cherry", stringQueue.removeNext(), "Should remove 'cherry' with priority 2");
        assertEquals("date", stringQueue.removeNext(), "Should remove 'date' with priority 2");
        assertTrue(stringQueue.isEmpty(), "Queue should be empty after all removals");
    }

    /**
     * Test adding elements beyond the initial capacity to ensure dynamic resizing.
     */
    @Test
    void testDynamicResizing() throws EmptyCollectionException {
        PriorityQueue<Integer> queue = new PriorityQueue<>();

        // Assuming DEFAULT_CAPACITY is 10, add more than 10 elements
        for (int i = 1; i <= 15; i++) {
            queue.addElement(i, i);
        }

        assertEquals(15, queue.size(), "Size should be 15 after adding 15 elements");

        // Remove all elements and verify correct order
        for (int i = 1; i <= 15; i++) {
            assertEquals(i, queue.removeNext(), "Should remove element " + i + " with priority " + i);
        }

        assertTrue(queue.isEmpty(), "Queue should be empty after removing all elements");
    }

    /**
     * Test that the highest priority element is always removed first.
     */
    @Test
    void testHighestPriorityFirst() throws EmptyCollectionException {
        PriorityQueue<String> stringQueue = new PriorityQueue<>();
        stringQueue.addElement("low", 5);
        stringQueue.addElement("medium", 3);
        stringQueue.addElement("high", 1);
        stringQueue.addElement("very high", 0);

        assertEquals("very high", stringQueue.removeNext(), "Should remove 'very high' with priority 0");
        assertEquals("high", stringQueue.removeNext(), "Should remove 'high' with priority 1");
        assertEquals("medium", stringQueue.removeNext(), "Should remove 'medium' with priority 3");
        assertEquals("low", stringQueue.removeNext(), "Should remove 'low' with priority 5");
        assertTrue(stringQueue.isEmpty(), "Queue should be empty after all removals");
    }
}
