/**
 * 08722 Data Structures for Application Programmers.
 * Homework6
 *
 * Andrew ID: aakashb1.
 * @author Aakash Bhatia.
 */
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
/**
 * Class Word.
 */
public class Word implements Comparable<Word> {
    /**
     * Instance variable for word.
     */
    private String word;
    /**
     * Instance variable for index to store line numbers for each word in the text.
     */
    private Set<Integer> index = new HashSet<Integer>();
    /**
     * Instance variable for frequency.
     */
    private int frequency;
    /**
     * Constructor for Word.
     * @param str string of word.
     */
    public Word(String str) {
        word = str;
    }
    /**
     * Setter for string in word.
     * @param str string of word.
     */
    public void setWord(String str) {
        word = str;
    }
    /**
     * Getter for string in word.
     * @return str string of word.
     */
    public String getWord() {
        return word;
    }
    /**
     * helper method to add integer line number to current set of linenumbers.
     * @param line line number.
     */
    public void addToIndex(Integer line) {
        index.add(line);
    }
    /**
     * Setter for frequency of word.
     * @param ind frequency of word.
     */
    public void setFrequency(int ind) {
        frequency = ind;
    }
    /**
     * getter for frequency of word.
     * @return ind frequency of word.
     */
    public int getFrequency() {
        return frequency;
    }
    /**
     * getter for the set of line numbers.
     * @return index set of lie numbers.
     */
    public Set<Integer> getIndex() {
        Iterator<Integer> itr = index.iterator();
        Set<Integer> copy = new HashSet<Integer>();
        while (itr.hasNext()) {
            copy.add(itr.next());
        }
        return index;
    }
    /**
     * comparable implementation for word class.
     * @return integer for the natural ordering of the word class.
     */
    @Override
    public int compareTo(Word arg0) {
        return this.word.compareTo(arg0.word);
    }
    /**
     * toString implementation.
     * @return string.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(word).append(" ").append(frequency).append(" ").append(index);
        return sb.toString();
    }
}
