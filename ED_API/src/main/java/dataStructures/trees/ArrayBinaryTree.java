package dataStructures.trees;

import dataStructures.lists.DoubleUnorderedLinkedList;
import dataStructures.queues.CircularArrayQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ArrayBinaryTree represents a binary tree implemented using an array.
 *
 * @param <T> the type of elements stored in the tree
 */
public class ArrayBinaryTree<T> implements BinaryTreeADT<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final int DEFAULT_MULTIPLIER = 2;
    private static final String ELEMENT_ERROR = "No such element";

    protected int count;
    protected T[] tree;

    /**
     * Creates a binary tree with the specified element as its root.
     *
     * @param element the element which will become the root
     *                of the new tree
     */
    public ArrayBinaryTree(T element) {
        count = 1;
        tree = (T[]) new Object[DEFAULT_CAPACITY];
        tree[0] = element;
    }

    /**
     * Creates an empty binary tree.
     */
    public ArrayBinaryTree() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates an empty binary tree with the specified capacity.
     *
     * @param capacity the initial capacity of the tree array
     */
    public ArrayBinaryTree(int capacity) {
        count = 0;
        tree = (T[]) new Object[capacity];
    }

    /**
     * Expands the capacity of the tree when needed.
     */
    protected void expandCapacity() {
        T[] temp = (T[]) new Object[tree.length * DEFAULT_MULTIPLIER];

        for (int ct = 0; ct < tree.length; ct++) {
            temp[ct] = tree[ct];
        }

        tree = temp;
    }

    /**
     * Returns the root element of the tree.
     *
     * @return the root element
     * @throws NoSuchElementException if the tree is empty
     */
    @Override
    public T getRoot() {
        if (isEmpty()) {
            throw new NoSuchElementException(ELEMENT_ERROR);
        }
        return tree[0];
    }

    /**
     * Checks if the binary tree is empty.
     *
     * @return true if the tree is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return (count == 0);
    }

    /**
     * Returns the number of elements in the tree.
     *
     * @return the number of elements in the tree
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * Checks if the tree contains the specified element.
     *
     * @param elementoAlvo the element being sought in the tree
     * @return true if the element is in the tree, false otherwise
     */
    @Override
    public boolean contains(T elementoAlvo) {
        try {
            find(elementoAlvo);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Returns a reference to the specified target element if it is
     * found in this binary tree. Throws a NoSuchElementException if
     * the specified target element is not found in the binary tree.
     *
     * @param targetElement the element being sought in the tree
     * @return the target element if found
     * @throws NoSuchElementException if the element is not found
     */
    public T find(T targetElement) throws NoSuchElementException {
        T temp = null;
        boolean found = false;

        for (int ct = 0; ct < count && !found; ct++) {
            if (targetElement.equals(tree[ct])) {
                found = true;
                temp = tree[ct];
            }
        }

        if (!found) {
            throw new NoSuchElementException(ELEMENT_ERROR);
        }

        return temp;
    }

    /**
     * Performs an inorder traversal on this binary tree by
     * calling an overloaded, recursive inorder method
     * that starts with the root.
     *
     * @return an iterator over the binary tree
     */
    @Override
    public Iterator<T> iteratorInOrder() {
        DoubleUnorderedLinkedList<T> templist = new DoubleUnorderedLinkedList<>();
        inorder(0, templist);
        return templist.iterator();
    }

    /**
     * Performs a recursive inorder traversal.
     *
     * @param node     the node index used in the traversal
     * @param templist the temporary list used in the traversal
     */
    protected void inorder(int node, DoubleUnorderedLinkedList<T> templist) {
        if (node < count) {
            if (tree[node] != null) {
                inorder(node * 2 + 1, templist);
                templist.addToRear(tree[node]);
                inorder(node * 2 + 2, templist);
            }
        }
    }

    /**
     * Returns an iterator for a preorder traversal of the binary tree.
     *
     * @return Iterator over the binary tree.
     */
    @Override
    public Iterator<T> iteratorPreOrder() {
        DoubleUnorderedLinkedList<T> templist = new DoubleUnorderedLinkedList<>();
        preorder(0, templist);
        return templist.iterator();
    }

    /**
     * Performs a recursive preorder traversal.
     *
     * @param node     the node index used in the traversal
     * @param templist the temporary list used in the traversal
     */
    protected void preorder(int node, DoubleUnorderedLinkedList<T> templist) {
        if (node < count) {
            if (tree[node] != null) {
                templist.addToRear(tree[node]);
                preorder(node * 2 + 1, templist);
                preorder(node * 2 + 2, templist);
            }
        }
    }

    /**
     * Returns an iterator for a postorder traversal of the binary tree.
     *
     * @return Iterator over the binary tree.
     */
    @Override
    public Iterator<T> iteratorPostOrder() {
        DoubleUnorderedLinkedList<T> templist = new DoubleUnorderedLinkedList<>();
        postorder(0, templist);
        return templist.iterator();
    }

    /**
     * Performs a recursive postorder traversal.
     *
     * @param node     the node index used in the traversal
     * @param templist the temporary list used in the traversal
     */
    protected void postorder(int node, DoubleUnorderedLinkedList<T> templist) {
        if (node < count) {
            if (tree[node] != null) {
                postorder(node * 2 + 1, templist);
                postorder(node * 2 + 2, templist);
                templist.addToRear(tree[node]);
            }
        }
    }

    /**
     * Returns an iterator for a level-order traversal of the binary tree.
     *
     * @return Iterator over the binary tree.
     */
    @Override
    public Iterator<T> iteratorLevelOrder() {
        DoubleUnorderedLinkedList<T> templist = new DoubleUnorderedLinkedList<>();
        levelOrder(0, templist);
        return templist.iterator();
    }

    /**
     * Performs a level-order traversal.
     *
     * @param node     the starting node index for traversal
     * @param tempList the temporary list used in the traversal
     */
    protected void levelOrder(int node, DoubleUnorderedLinkedList<T> tempList) {
        CircularArrayQueue<Integer> queue = new CircularArrayQueue<>();
        if (node < count && tree[node] != null) {
            queue.enqueue(node);
        }
        while (!queue.isEmpty()) {
            int currentIndex = queue.dequeue();
            T currentElement = tree[currentIndex];
            tempList.addToRear(currentElement);

            int leftChildIndex = currentIndex * 2 + 1;
            if (leftChildIndex < count && tree[leftChildIndex] != null) {
                queue.enqueue(leftChildIndex);
            }

            int rightChildIndex = currentIndex * 2 + 2;
            if (rightChildIndex < count && tree[rightChildIndex] != null) {
                queue.enqueue(rightChildIndex);
            }
        }
    }

    /**
     * Returns the index of the specified element in the binary tree.
     *
     * @param element The element to find the index for.
     * @return The index of the element.
     * @throws NoSuchElementException If the element is not found in the tree.
     */
    public int indexOf(T element) throws NoSuchElementException {
        for (int ct = 0; ct < count; ct++) {
            if (element.equals(tree[ct])) {
                return ct;
            }
        }
        throw new NoSuchElementException(ELEMENT_ERROR);
    }
}
