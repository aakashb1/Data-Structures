/**
  * 08722 Data Structures for Application Programmers.
 * Homework6
 *
 * Andrew ID: aakashb1.
 * @author Aakash Bhatia.
 */
import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;
/**
 * BST Class.
 * Recursion used for.
 * 1) for getting the height of the tree.
 * 2) for calculating the number of nodes in the tree.
 * 3) for searching any data in the tree.
 * 4) for inserting any data in the tree.
 * @param <T> data.
 */
public class BST<T extends Comparable<T>> implements Iterable<T>, BSTInterface<T> {
    /**
     * Instance variable for root.
     */
    private Node<T> root;
    /**
     * Instance variable for comparator.
     */
    private Comparator<T> comparator;
    /**
     * Constructor for BST class to store words that contain a string, frequency and pages of occurrence.
     */
    public BST() {
        this(null);
    }
    /**
     * Constructor for BST class to store data.
     * @param comp comparator to use while inserting/searching data.
     */
    public BST(Comparator<T> comp) {
        comparator = comp;
        root = null;
    }
    /**
     * getter for private comparator.
     * @return comparator instance.
     */
    public Comparator<T> comparator() {
        return comparator;
    }
    /**
     * getter for root of BST.
     * @return root.data data contained in the root of the BST.
     */
    public T getRoot() {
        if (root != null) {
            return root.data;
        }
        return null;
    }
    /**
     * getter for height of BST.
     * @return height of the BST.
     */
    public int getHeight() {
        if (root != null) {
            return getHeight(root);
        }
        return 0;
    }
    /**
     * Helper Recursive function. Base case is
     * reached when the current node
     * is null. Recursive case continues
     * till the current node is null.
     * helper method for getHeight().
     * @param current node.
     * @return height of the BST.
     */
    private int getHeight(Node<T> current) {
        if (current == null) {
            return -1;
        } else {
            int lDepth = getHeight(current.left);
            int rDepth = getHeight(current.right);
            if (lDepth > rDepth) {
                return (lDepth + 1);
            } else {
                return (rDepth + 1);
            }
        }
    }
    /**
     * helper method to compare two data objects.
     * @param x data 1.
     * @param y data 2.
     * @return value after comparison.
     */
    private int compare(T x, T y) {
        if (comparator == null) {
            return x.compareTo(y);
        } else {
            return comparator.compare(x, y);
        }
    }
    /**
     * getter for number of nodes in BST.
     * @return number of nodes in the BST.
     */
    public int getNumberOfNodes() {
        if (root != null) {
            return getNumberOfNodes(root);
        }
        return 0;
    }
    /**
     * Helper recursive function. Base case is reached when the current node
     * is null, at this time we return nodes . Recursive case: keeps incrementing
     * nodes by 1 till the current node is null.
     * helper method for getNumberOfNodes().
     * @param current node of recursion.
     * @return number of nodes in the BST.
     */
    private int getNumberOfNodes(Node<T> current) {
        int nodes = 0;
        if (current == null) {
            return nodes;
        }
        if (current.left == null && current.right == null) {
            nodes = nodes + 1;
            return nodes;
        } else {
            nodes = nodes + 1;
            return nodes + getNumberOfNodes(current.left) + getNumberOfNodes(current.right);
        }
    }
    /**
     * search any data in BST.
     * @param toSearch data to search in BST
     * @return data null if not found else returns data found.
     */
    @Override
    public T search(T toSearch) {
        if (root != null) {
            return search(root, toSearch);
        } else {
            return null;
        }
    }
    /**
     * Helper Recursive function. Base case is reached when the current node
     * is null, we return null to signify node not found. Recursive case continues
     * till the current node is null.
     * helper method for search(T toSearch).
     * @param toSearch data to search in BST
     * @param current node of recursion.
     * @return data null if not found else returns data found.
     */
    private T search(Node<T> current, T toSearch) {
        if (current == null) {
            return null;
        }
        int dummy = compare(current.data, toSearch);
        if (dummy == 0) {
            return current.data;
        }
        if (dummy > 0) {
            return search(current.left, toSearch);
        }
        return search(current.right, toSearch);
    }
    /**
     * Recursive helper function used.
     * insert any data in BST.
     * @param toInsert data to insert in BST
     */
    @Override
    public void insert(T toInsert) {
        Node<T> newNode = new Node<T>(toInsert);
        boolean isLC = true;
        if (root == null) {
            root = newNode;
        } else {
            insert(root, root, newNode, isLC);
        }
    }
    /**
     * Helper recursive function, where Base case is reached when the current node
     * is null. At this point we insert the node to be inserted. Recursive case
     * continues till the current node reached is null.
     * helper method for search(T toInsert).
     * @param newNode data to insert in BST.
     * @param current node of recursion.
     * @param parent node of current node.
     * @param isLC true if the child is the left of the parent else false.
     */
    public void insert(Node<T> current, Node<T> parent, Node<T> newNode, boolean isLC) {
        if (current == null) {
            if (isLC) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
        } else {
            int dummy = compare(current.data, newNode.data);
            if (dummy == 0) {
                return;
            }
            if (dummy > 0) {
                isLC = true;
                insert(current.left, current, newNode, isLC);
            } else {
                isLC = false;
                insert(current.right, current, newNode, isLC);
            }
        }
    }
    /**
     * Iterator implementations that returns Iterator object.
     */
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }
    /**
     * Inner class for BinaryTreeIterator.
     * that implements Iterator interface.
     */
    private class BinaryTreeIterator implements Iterator<T> {
        /**
         * local variable for stack.
         */
        private Stack<Node<T>> binTree;
        /**
         * Constructor for iterator.
         */
        public BinaryTreeIterator() {
            binTree = new Stack<Node<T>>();
            Node<T> current = root;
            while (current != null) {
                binTree.push(current);
                current = current.left;
            }
        }
        /**
         * hasNext implementation.
         * @return boolean true if next node exists else false.
         */
        @Override
        public boolean hasNext() {
            return !binTree.isEmpty();
        }
        /**
         * next implementation.
         */
        @Override
        public T next() {
            Node<T> node = binTree.pop();
            T output = node.data;
            if (node.right != null) {
                node = node.right;
                while (node != null) {
                    binTree.push(node);
                    node = node.left;
                }
            }
            return output;
        }
    }
    /**
     * static nested class for node.
     * @param <T> data.
     */
    private static class Node<T> {
        /**
         * T data.
         */
        private T data;
        /**
         * references to left children node.
         */
        private Node<T> left;
        /**
         * references to right children node.
         */
        private Node<T> right;
        /**
         * Constructs a new node with data.
         * @param d data..
         */
        Node(T d) {
            this(d, null, null);
        }
        /**
         * Constructs a new node with data.
         * @param d data.
         * @param l left child.
         * @param r right child.
         */
        Node(T d, Node<T> l, Node<T> r) {
            if (d != null) {
                data = d;
            }
            left = l;
            right = r;
        }
        @Override
        public String toString() {
            return data.toString();
        }
    }
}
