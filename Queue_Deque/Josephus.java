import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 08722 Data Structures for Application Programmers.
 *
 * Homework Assignment 2 Solve Josephus problem
 * with different data structures
 * and different algorithms and compare running times
 *
 * Andrew ID: AAKASHB1
 * @author: Aakash Bhatia
 * Assuming there are many people in a circle, I would use the LinkedList (remove with
 * index method) to find the survivors position because it is the quickest of them all.
 * Removing with index turns out to be a faster process because here we do not have to
 * add elements to the end of our list to know who is going to be eliminated. Instead we
 * directly compute the index that is going to be eliminated and remove the person on the
 * computed index.
 */
public class Josephus {
    /**
     * Instance variable for nItems.
     */
    private int nItems;
    /**
     * Uses ArrayDeque class as Queue/Deque to find the survivor's position.
     * @param size Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle. The value has to be greater than 0
     * @return The position value of the survivor
     */
    public int playWithAD(int size, int rotation) {
        if (size <= 0 || rotation <= 0) {
            throw new RuntimeException();
        }
        ArrayDeque<Integer> arr = new ArrayDeque<Integer>();
        int front = 0;
        nItems = 0;
        int temp = 0;
        for (int i = 0; i < size; i = i + 1) {
            arr.add(i + 1);
            nItems = nItems + 1;
        }
/*        if (rotation > size) {
            rotation = rotation- size + 1 ;
        }*/
        while (nItems != 1) {
            if (rotation > nItems) {
                temp = rotation % nItems;
                if (temp == 0) {
                    temp = nItems;
                }
            } else {
                temp = rotation;
            }
            for (int i = 0; i < temp - 1; i = i + 1) {
                arr.add(arr.remove());
                }
            arr.remove();
            nItems = nItems - 1;
        }
        return arr.peek();
    }

    /**
     * Uses LinkedList class as Queue/Deque to find the survivor's position.
     * @param size Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle. The value has to be greater than 0
     * @return The position value of the survivor
     */
    public int playWithLL(int size, int rotation) {
        int temp = 0;
        if (size <= 0 || rotation <= 0) {
            throw new RuntimeException();
        }
        Deque<Integer> list = new LinkedList<Integer>();
        int front = 0; int back = -1; nItems = 0;
        for (int i = 0; i < size; i = i + 1) {
            list.add(i + 1);
            nItems = nItems + 1;
        }
        while (nItems != 1) {
            if (rotation > nItems) {
                temp = rotation % nItems;
                if (temp == 0) {
                    temp = nItems;
                }
            } else {
                temp = rotation;
            }
            for (int i = 0; i < temp - 1; i = i + 1) {
                list.add(list.remove());
                }
            list.remove();
            nItems = nItems - 1;
        }
        return list.peek();
    }

    /**
     * Uses LinkedList class to find the survivor's position.
     * However, do NOT use the LinkedList as Queue/Deque
     * Instead, use the LinkedList as "List"
     * That means, it uses index value to find and remove a person to be executed in the circle
     *
     * Note: Think carefully about this method!!
     * When in doubt, please visit one of the office hours!!
     *
     * @param size Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle. The value has to be greater than 0
     * @return The position value of the survivor
     */
    public int playWithLLAt(int size, int rotation) {
        if (size <= 0 || rotation <= 0) {
            throw new RuntimeException();
        }
        List<Integer> list = new LinkedList<Integer>();
        int front = 0; int back = -1; nItems = 0;
        for (int i = 0; i < size; i = i + 1) {
            list.add(i + 1);
            nItems = nItems + 1;
        }
        int temp = (rotation - 1) % nItems;
        while (nItems != 1) {
            list.remove(temp);
            nItems = nItems - 1;
            temp = ((temp + rotation - 1) % nItems) % nItems;
        }
        return list.get(0);
    }
}
