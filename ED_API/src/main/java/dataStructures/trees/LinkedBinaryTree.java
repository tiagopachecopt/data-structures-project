package dataStructures.trees;

import dataStructures.exceptions.EmptyCollectionException;
import dataStructures.lists.DoubleUnorderedLinkedList;
import dataStructures.queues.LinkedQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * LinkedBinaryTree implements the BinaryTreeADT interface
 */
public class LinkedBinaryTree<T> implements BinaryTreeADT<T> {
    protected int count;
    protected BinaryTreeNode<T> root;

    /**
     * Creates an empty binary tree.
     */
    public LinkedBinaryTree() {
        count = 0;
        root = null;
    }

    /**
     * Creates a binary tree with the specified element as its root.
     *
     * @param element the element that will become the root of the
     *                new binary tree
     */
    public LinkedBinaryTree(T element) {
        count = 1;
        root = new BinaryTreeNode<T>(element);
    }

    /**
     * Returns the element at the root of this binary tree.
     *
     * @return the element at the root of this binary tree
     */
    @Override
    public T getRoot() {
        return root.element;
    }

    /**
     * Checks if this binary tree is empty.
     *
     * @return true if the binary tree is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Returns the number of elements in this binary tree.
     *
     * @return the number of elements in this binary tree
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * Checks if the binary tree contains the specified target element.
     *
     * @param targetElement the element being sought in this tree
     * @return true if the element is in the tree, false otherwise
     */
    @Override
    public boolean contains(T targetElement) {
        try {
            find(targetElement);
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
     * @param targetElement the element being sought in this tree
     * @return a reference to the specified target
     * @throws NoSuchElementException if an element not found
     *                                exception occurs
     */
    @Override
    public T find(T targetElement) throws NoSuchElementException {
        BinaryTreeNode<T> current = findAgain(targetElement, root);

        if (current == null)
            throw new NoSuchElementException("binary tree");

        return (current.element);
    }

    /**
     * Returns a reference to the specified target element if it is
     * found in this binary tree.
     *
     * @param targetElement the element being sought in this tree
     * @param next          the element to begin searching from
     */
    private BinaryTreeNode<T> findAgain(T targetElement, BinaryTreeNode<T> next) {
        if (next == null)
            return null;

        if (next.element.equals(targetElement)) {
            return next;
        }
        BinaryTreeNode<T> temp = findAgain(targetElement, next.left);
        if (temp == null) {
            temp = findAgain(targetElement, next.right);
        }

        return temp;
    }

    /**
     * Performs an inorder traversal on this binary tree by calling an
     * overloaded, recursive inorder method that starts with
     * the root.
     *
     * @return an in order iterator over this binary tree
     */
    @Override
    public Iterator<T> iteratorInOrder() {
        DoubleUnorderedLinkedList<T> tempList = new DoubleUnorderedLinkedList<T>();
        inorder(root, tempList);

        return tempList.iterator();
    }

    /**
     * Performs a recursive inorder traversal.
     *
     * @param node     the node to be used as the root
     *                 for this traversal
     * @param tempList the temporary list for use in this traversal
     */
    protected void inorder(BinaryTreeNode<T> node,
                           DoubleUnorderedLinkedList<T> tempList) {
        if (node != null) {
            inorder(node.left, tempList);
            tempList.addToRear(node.element);
            inorder(node.right, tempList);
        }
    }

    /**
     * Returns an iterator for the preorder traversal of this binary tree.
     *
     * @return a preorder iterator over this binary tree
     */
    @Override
    public Iterator<T> iteratorPreOrder() {
        DoubleUnorderedLinkedList<T> tempList = new DoubleUnorderedLinkedList<T>();
        preOrder(root, tempList);

        return tempList.iterator();
    }

    /**
     * Performs a recursive preorder traversal.
     *
     * @param node     the node to be used as the root
     *                 for this traversal
     * @param tempList the temporary list for use in this traversal
     */
    protected void preOrder(BinaryTreeNode<T> node,
                            DoubleUnorderedLinkedList<T> tempList) {
        if (node != null) {
            tempList.addToRear(node.element);
            preOrder(node.left, tempList);
            preOrder(node.right, tempList);
        }
    }

    /**
     * Returns an iterator for the postorder traversal of this binary tree.
     *
     * @return a postorder iterator over this binary tree
     */
    @Override
    public Iterator<T> iteratorPostOrder() {
        DoubleUnorderedLinkedList<T> tempList = new DoubleUnorderedLinkedList<T>();
        postOrder(root, tempList);

        return tempList.iterator();
    }

    /**
     * Performs a recursive postorder traversal.
     *
     * @param node     the node to be used as the root
     *                 for this traversal
     * @param tempList the temporary list for use in this traversal
     */
    protected void postOrder(BinaryTreeNode<T> node,
                             DoubleUnorderedLinkedList<T> tempList) {
        if (node != null) {
            postOrder(node.left, tempList);
            postOrder(node.right, tempList);
            tempList.addToRear(node.element);
        }
    }

    /**
     * Returns an iterator for the level-order traversal of this binary tree.
     *
     * @return a level-order iterator over this binary tree
     */
    @Override
    public Iterator<T> iteratorLevelOrder() throws EmptyCollectionException {
        DoubleUnorderedLinkedList<T> tempList = new DoubleUnorderedLinkedList<T>();
        levelOrder(root, tempList);

        return tempList.iterator();
    }

    /**
     * Performs a level-order traversal.
     *
     * @param node     the node to be used as the root
     *                 for this traversal
     * @param tempList the temporary list for use in this traversal
     */
    protected void levelOrder(BinaryTreeNode<T> node, DoubleUnorderedLinkedList<T> tempList) throws EmptyCollectionException {
        LinkedQueue<BinaryTreeNode<T>> queue = new LinkedQueue<>();

        if (node != null) {
            queue.enqueue(node);
        }
        while (!queue.isEmpty()) {
            BinaryTreeNode<T> head = queue.dequeue();
            tempList.addToRear(head.element);

            if (head.left != null) {
                queue.enqueue(head.left);
            }

            if (head.right != null) {
                queue.enqueue(head.right);
            }
        }
    }

}
