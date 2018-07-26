/**
 * 08722 Data Structures for Application Programmers.
 * Homework6
 *
 * Andrew ID: aakashb1.
 * @author Aakash Bhatia.
 */
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
/**
 * Class Index.
 */
public class Index {
    /**
     * @param fileName name of the file, eg.
     * "C:\\Users\\Aakash Bhatia\\eclipse-workspace\\Data Structures\\Homework6\\src\\test.txt".
     * @return tree BST.
     */
    public BST<Word> buildIndex(String fileName) {
        BST<Word> tree = new BST<Word>();
        return insert(fileName, tree, false);
    }
    /**
     * @param fileName name of the file.
     * @param comparator comparator to use while inserting/searching elements in a bst.
     * @return tree BST.
     */
    public BST<Word> buildIndex(String fileName, Comparator<Word> comparator) {
        BST<Word> tree = new BST<Word>(comparator);
        if (comparator.getClass().getSimpleName().equals("IgnoreCase")) {
            return insert(fileName, tree, true);
        }
        return insert(fileName, tree, false);
    }
    /**
     * @param list to be inserted.
     * @param comparator comparator to use while inserting/searching elements in a bst.
     * @return tree BST.
     */
    public BST<Word> buildIndex(ArrayList<Word> list, Comparator<Word> comparator) {
        BST<Word> tree = new BST<Word>(comparator);
        for (Word data: list) {
            if (isWord(data.getWord())) {
                tree.insert(data);
            }
        }
        return tree;
    }
    /**
     * helper method for insert method.
     * @param fileName string of filename to be inserted.
     * @param tree data structure to add elements in.
     * @param ignoreCase true if case is to be ignored else false.
     * @return tree BST.
     */
    private BST<Word> insert(String fileName, BST<Word> tree, boolean ignoreCase) {
        int linenumber = 0;
        Scanner scanner = null;
        try {
            File newFile = new File(fileName);
            scanner = new Scanner(newFile, "latin1");
            while (scanner.hasNextLine()) {
                linenumber = linenumber + 1;
                String line = scanner.nextLine();
                String[] wordsFromText = line.split("\\W");
                for (String word : wordsFromText) {
                    if (isWord(word)) {
                        if (ignoreCase) {
                            word = word.toLowerCase();
                        }
                        Word add = new Word(word);
                        Word dummy = tree.search(add);
                        if (dummy != null) {
                            int freq = dummy.getFrequency();
                            dummy.setFrequency(freq + 1);
                            dummy.addToIndex(linenumber);
                        } else {
                            add.setFrequency(1);
                            add.addToIndex(linenumber);
                            tree.insert(add);
                        }
                        }
                    }
                }
            } catch (Exception e) {
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return tree;
    }
    /**
     * method to sort tree in alphabetic order.
     * @param tree binary tree structure to be sorted.
     * @return unsorted alphabetically sorted ArrayList.
     */
    public ArrayList<Word> sortByAlpha(BST<Word> tree) {
/*         * Even though there should be no ties with regard to words in BST,
         * in the spirit of using what you wrote,
         * use AlphaFreq comparator in this method.*/
         ArrayList<Word> unsorted = new ArrayList<Word>();
         for (Word data : tree) {
             unsorted.add(data);
         }
         insertionSort(unsorted, "alpha");
         return unsorted;
    }
    /**
     * method to sort tree by frequency.
     * @param tree binary tree structure to be sorted.
     * @return unsorted by frequency sorted ArrayList.
     */
    public ArrayList<Word> sortByFrequency(BST<Word> tree) {
        ArrayList<Word> unsorted = new ArrayList<Word>();
        for (Word data : tree) {
            unsorted.add(data);
        }
        insertionSort(unsorted, "freq");
        return unsorted;
    }
    /**
     * method to get words with highest frequency.
     * @param tree binary tree structure to be sorted.
     * @return highFreq most frequently occuring words.
     */
    public ArrayList<Word> getHighestFrequency(BST<Word> tree) {
        ArrayList<Word> unsorted = new ArrayList<Word>();
        for (Word data : tree) {
            unsorted.add(data);
        }
        insertionSort(unsorted, "freq");
        int highestFreq = unsorted.get(0).getFrequency();
        ArrayList<Word> highFreq = new ArrayList<Word>();
        for (Word data: unsorted) {
            if (Integer.compare(data.getFrequency(), highestFreq) == 0) {
                highFreq.add(data);
            }
        }
        return highFreq;
    }
    /**
     * Simple private helper method to validate a word.
     * @param text text to check
     * @return true if valid, false if not
     */
    private boolean isWord(String text) {
        return text != null && text.matches("[a-zA-Z]+");
    }
    /**
     * Sorts employees either by last name or zip using Insertion Sort.
     * @param unsorted list of word objects
     * @param key key param value should be either "alpha" or "freq"
     */
    public static void insertionSort(ArrayList<Word> unsorted, String key) {
        if (key.equals("freq")) {
            Comparator<Word> comp = new Frequency();
            for (int out = 1; out < unsorted.size(); out++) {
                Word tmp = unsorted.get(out); // store the value temporarily
                int in = out; // initially set to be the same as out
                //while (in > 0 && Integer.compare(tmp.getFrequency(), unsorted.get(in - 1).getFrequency()) > 0) {
                while (in > 0 && comp.compare(unsorted.get(in - 1), tmp) > 0) {
                    //unsorted.remove(in);
                    unsorted.set(in, unsorted.get(in - 1));
                    in--;
                }
                if (out != in) {
                    unsorted.set(in, tmp);
                }
            }
        } else {
            Comparator<Word> comp = new AlphaFreq();
            for (int out = 1; out < unsorted.size(); out++) {
                Word tmp = unsorted.get(out); // store the value temporarily
                int in = out; // initially set to be the same as out
                //while (in > 0 && unsorted.get(in - 1).getWord().compareTo(tmp.getWord()) > 0) {
                while (in > 0 && comp.compare(unsorted.get(in - 1), tmp) > 0) {
                    //Word tem = unsorted.remove(in);
                    unsorted.set(in, unsorted.get(in - 1));
                    in--;
                }
                if (out != in) {
                    unsorted.set(in, tmp);
                    }
                }
            }
    }
}
