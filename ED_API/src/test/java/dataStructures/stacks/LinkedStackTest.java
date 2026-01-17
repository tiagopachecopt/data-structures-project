package dataStructures.stacks;

import dataStructures.exceptions.EmptyCollectionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedStackTest {

    @Test
    void testPushAndPeek() throws EmptyCollectionException {
        LinkedStack<Integer> stack = new LinkedStack<>();

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());

        stack.push(1);
        assertFalse(stack.isEmpty());
        assertEquals(1, stack.size());
        assertEquals(1, stack.peek());

        stack.push(2);
        assertEquals(2, stack.size());
        assertEquals(2, stack.peek());

        stack.push(3);
        assertEquals(3, stack.size());
        assertEquals(3, stack.peek());
    }

    @Test
    void testPop() throws EmptyCollectionException {
        LinkedStack<String> stack = new LinkedStack<>();

        stack.push("A");
        stack.push("B");
        stack.push("C");

        assertEquals("C", stack.pop());
        assertEquals(2, stack.size());
        assertEquals("B", stack.pop());
        assertEquals(1, stack.size());
        assertEquals("A", stack.pop());
        assertTrue(stack.isEmpty());

        assertThrows(EmptyCollectionException.class, stack::pop);
    }

    @Test
    void testPeek() throws EmptyCollectionException {
        LinkedStack<Double> stack = new LinkedStack<>();

        stack.push(1.1);
        stack.push(2.2);

        assertEquals(2.2, stack.peek());
        assertEquals(2.2, stack.pop());
        assertEquals(1.1, stack.peek());

        stack.pop();
        assertThrows(EmptyCollectionException.class, stack::peek);
    }

    @Test
    void testIsEmpty() throws EmptyCollectionException {
        LinkedStack<Character> stack = new LinkedStack<>();

        assertTrue(stack.isEmpty());

        stack.push('X');
        assertFalse(stack.isEmpty());

        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @Test
    void testSize() throws EmptyCollectionException {
        LinkedStack<Integer> stack = new LinkedStack<>();

        assertEquals(0, stack.size());

        stack.push(100);
        stack.push(200);
        stack.push(300);

        assertEquals(3, stack.size());

        stack.pop();
        assertEquals(2, stack.size());
    }

    @Test
    void testToString() {
        LinkedStack<Integer> stack = new LinkedStack<>();

        stack.push(10);
        stack.push(20);

        String toStringOutput = stack.toString();
        assertTrue(toStringOutput.contains("top"));
        assertTrue(toStringOutput.contains("size=2"));
    }
}