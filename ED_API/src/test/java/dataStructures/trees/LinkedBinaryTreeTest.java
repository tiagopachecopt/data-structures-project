package dataStructures.trees;

import dataStructures.exceptions.EmptyCollectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class LinkedBinaryTreeTest {

    private LinkedBinaryTree<Integer> emptyTree;
    private LinkedBinaryTree<Integer> singleElementTree;
    private LinkedBinaryTree<Integer> multiElementTree;

    @BeforeEach
    void setUp() {
        // Initialize an empty tree
        emptyTree = new LinkedBinaryTree<>();

        // Initialize a tree with a single root element
        singleElementTree = new LinkedBinaryTree<>(10);

        // Initialize a tree with multiple elements
        multiElementTree = new LinkedBinaryTree<>();
        // Manually constructing the tree
        /*
                 1
               /   \
              2     3
             / \   / \
            4   5 6   7
        */
        BinaryTreeNode<Integer> root = new BinaryTreeNode<>(1);
        BinaryTreeNode<Integer> node2 = new BinaryTreeNode<>(2);
        BinaryTreeNode<Integer> node3 = new BinaryTreeNode<>(3);
        BinaryTreeNode<Integer> node4 = new BinaryTreeNode<>(4);
        BinaryTreeNode<Integer> node5 = new BinaryTreeNode<>(5);
        BinaryTreeNode<Integer> node6 = new BinaryTreeNode<>(6);
        BinaryTreeNode<Integer> node7 = new BinaryTreeNode<>(7);

        // Setting up relationships
        root.left = node2;
        root.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;

        // Assigning root and count
        multiElementTree.root = root;
        multiElementTree.count = 7;
    }

    @Test
    void testEmptyTree() {
        assertTrue(emptyTree.isEmpty(), "Tree should be empty");
        assertEquals(0, emptyTree.size(), "Size of empty tree should be 0");
        assertThrows(NullPointerException.class, emptyTree::getRoot, "Getting root of empty tree should throw NullPointerException");
    }

    @Test
    void testSingleElementTree() {
        assertFalse(singleElementTree.isEmpty(), "Tree should not be empty");
        assertEquals(1, singleElementTree.size(), "Size should be 1");
        assertEquals(10, singleElementTree.getRoot(), "Root should be 10");
        assertTrue(singleElementTree.contains(10), "Tree should contain 10");
        assertFalse(singleElementTree.contains(5), "Tree should not contain 5");
    }

    @Test
    void testFind() {
        assertEquals(1, multiElementTree.find(1), "Find should return the root element");
        assertEquals(2, multiElementTree.find(2), "Find should return the left child of root");
        assertEquals(7, multiElementTree.find(7), "Find should return the rightmost leaf");
        assertThrows(NoSuchElementException.class, () -> multiElementTree.find(999), "Finding non-existent element should throw exception");
    }

    @Test
    void testContains() {
        assertTrue(multiElementTree.contains(3), "Tree should contain 3");
        assertFalse(multiElementTree.contains(8), "Tree should not contain 8");
    }

    @Test
    void testIndexOf() {
        // Note: The LinkedBinaryTree class does not have an indexOf method.
        // If you need to test indexOf, ensure that such a method exists.
        // For demonstration, assuming indexOf returns the depth-first index.

        // Implement indexOf in LinkedBinaryTree if not present
        // Example:
        // assertEquals(0, multiElementTree.indexOf(1), "Index of root should be 0");
        // assertEquals(1, multiElementTree.indexOf(2), "Index of element 2 should be 1");
        // assertThrows(NoSuchElementException.class, () -> multiElementTree.indexOf(100), "IndexOf non-existent element should throw exception");

        // Since indexOf is not implemented, this test is commented out.
    }

    @Test
    void testInOrderTraversal() {
        Iterator<Integer> iterator = multiElementTree.iteratorInOrder();
        int[] expectedOrder = {4, 2, 5, 1, 6, 3, 7};
        int index = 0;
        while (iterator.hasNext()) {
            assertEquals(expectedOrder[index++], iterator.next(), "In-order traversal mismatch");
        }
        assertEquals(expectedOrder.length, index, "In-order traversal length mismatch");
    }

    @Test
    void testPreOrderTraversal() {
        Iterator<Integer> iterator = multiElementTree.iteratorPreOrder();
        int[] expectedOrder = {1, 2, 4, 5, 3, 6, 7};
        int index = 0;
        while (iterator.hasNext()) {
            assertEquals(expectedOrder[index++], iterator.next(), "Pre-order traversal mismatch");
        }
        assertEquals(expectedOrder.length, index, "Pre-order traversal length mismatch");
    }

    @Test
    void testPostOrderTraversal() {
        Iterator<Integer> iterator = multiElementTree.iteratorPostOrder();
        int[] expectedOrder = {4, 5, 2, 6, 7, 3, 1};
        int index = 0;
        while (iterator.hasNext()) {
            assertEquals(expectedOrder[index++], iterator.next(), "Post-order traversal mismatch");
        }
        assertEquals(expectedOrder.length, index, "Post-order traversal length mismatch");
    }

    @Test
    void testLevelOrderTraversal() {
        try {
            Iterator<Integer> iterator = multiElementTree.iteratorLevelOrder();
            int[] expectedOrder = {1, 2, 3, 4, 5, 6, 7};
            int index = 0;
            while (iterator.hasNext()) {
                assertEquals(expectedOrder[index++], iterator.next(), "Level-order traversal mismatch");
            }
            assertEquals(expectedOrder.length, index, "Level-order traversal length mismatch");
        } catch (EmptyCollectionException e) {
            fail("Level-order traversal threw EmptyCollectionException unexpectedly");
        }
    }

    @Test
    void testGetRoot() {
        assertThrows(NullPointerException.class, emptyTree::getRoot, "Empty tree should throw NullPointerException when getting root");
        assertEquals(10, singleElementTree.getRoot(), "Single element tree should return correct root");
        assertEquals(1, multiElementTree.getRoot(), "Multi-element tree should return correct root");
    }

    @Test
    void testIsEmpty() {
        assertTrue(emptyTree.isEmpty(), "Empty tree should be empty");
        assertFalse(singleElementTree.isEmpty(), "Single element tree should not be empty");
        assertFalse(multiElementTree.isEmpty(), "Multi-element tree should not be empty");
    }

    @Test
    void testSize() {
        assertEquals(0, emptyTree.size(), "Empty tree size should be 0");
        assertEquals(1, singleElementTree.size(), "Single element tree size should be 1");
        assertEquals(7, multiElementTree.size(), "Multi-element tree size should be 7");
    }

    @Test
    void testContainsEmptyTree() {
        assertFalse(emptyTree.contains(1), "Empty tree should not contain any elements");
    }

    @Test
    void testFindEmptyTree() {
        assertThrows(NoSuchElementException.class, () -> emptyTree.find(1), "Finding any element in empty tree should throw exception");
    }

    @Test
    void testTraversalsOnSingleElementTree() {

        Iterator<Integer> inOrder = singleElementTree.iteratorInOrder();
        assertTrue(inOrder.hasNext(), "In-order iterator should have next");
        assertEquals(10, inOrder.next(), "In-order traversal should return the root element");
        assertFalse(inOrder.hasNext(), "In-order iterator should have no more elements");

        Iterator<Integer> preOrder = singleElementTree.iteratorPreOrder();
        assertTrue(preOrder.hasNext(), "Pre-order iterator should have next");
        assertEquals(10, preOrder.next(), "Pre-order traversal should return the root element");
        assertFalse(preOrder.hasNext(), "Pre-order iterator should have no more elements");

        Iterator<Integer> postOrder = singleElementTree.iteratorPostOrder();
        assertTrue(postOrder.hasNext(), "Post-order iterator should have next");
        assertEquals(10, postOrder.next(), "Post-order traversal should return the root element");
        assertFalse(postOrder.hasNext(), "Post-order iterator should have no more elements");

        try {
            Iterator<Integer> levelOrder = singleElementTree.iteratorLevelOrder();
            assertTrue(levelOrder.hasNext(), "Level-order iterator should have next");
            assertEquals(10, levelOrder.next(), "Level-order traversal should return the root element");
            assertFalse(levelOrder.hasNext(), "Level-order iterator should have no more elements");
        } catch (EmptyCollectionException e) {
            fail("Level-order traversal threw EmptyCollectionException unexpectedly");
        }
    }
}
