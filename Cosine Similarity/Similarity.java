/**
 * 08722 Data Structures for Application Programmers.
 * Homework5
 *
 * Andrew ID: aakashb1.
 * @author Aakash Bhatia.
 */
import java.io.File;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
/**
 * Class Similarity.
 * Data Structures used:
 * 1) HashMap to store lower cases words as keys and their values as the number of times that words occurs in the text.
 */
public class Similarity {
    /**
     * Instance variable for map.
     */
    private Map<String, BigInteger> strMap = new HashMap<String, BigInteger>();
    /**
     * Instance variable for number of words.
     */
    private BigInteger size = BigInteger.ZERO;
    /**
     * Instance variable for number of lines.
     */
    private int lines;
    /**
     * Constructor for Similarity class for type string.
     * @param string string to insert.
     */
    public Similarity(String string) {
        insert(string);
        }
    /**
     * Constructor for Similarity class for type file.
     * @param file file to insert.
     */
    public Similarity(File file) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(file, "latin1");
            while (scanner.hasNextLine()) {
                lines = lines + 1;
                String line = scanner.nextLine();
                insert(line);
                }
            } catch (Exception e) {
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
    /**
     * private helper to insert string.
     * @param add string to add.
     */
    private void insert(String add) {
        if (add != null) {
            String[] wordsFromText = add.split("\\W");
            for (String word : wordsFromText) {
                if (isWord(word)) {
                    size = size.add(BigInteger.ONE);
                    String temp = word.toLowerCase();
                    if (strMap.containsKey(temp)) {
                        BigInteger dummy = strMap.get(temp).add(BigInteger.ONE);
                        strMap.put(temp, dummy);
                    } else {
                        strMap.put(temp, BigInteger.ONE);
                    }
                }
            }
        }
    }
    /**
     * method to calculate number of words in a given.
     * @return number of words.
     */
    public BigInteger numOfWords() {
/*        Iterator<String> itr = strMap.keySet().iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }*/
        return size;
    }
    /**
     * method to calculate number of lines in a given file.
     * @return number of lines.
     */
    public int numOfLines() {
        return lines;
    }
    /**
     * method to calculate number of words (excluding duplicates).
     * @return number of words.
     */
    public int numOfWordsNoDups() {
        return strMap.size();
    }
    /**
     * method to calculate euclidean norm of current instance of map.
     * @return euclidean norm (square root of the sum of squares of the frequencies).
     */
    public double euclideanNorm() {
        double sum = 0;
        Iterator<BigInteger> itr = strMap.values().iterator();
        while (itr.hasNext()) {
            sum = sum + Math.pow(itr.next().doubleValue(), 2);
        }
        return Math.pow(sum, 0.5);
        //double x = Math.pow(strMap.values, 2);
    }
    /**
     * method to calculate dot product between two documents/dictionaries. The method
     * does not fall into quadratic time complexity because first we iterate through one
     * map and check if the current word exists in the other map , if the word exists
     * then only the dot product is calculated else it is not. This helps us to take into account
     * those keys that actually are in the dictionary. Also we check which map has lesser size
     * and iterate the keys of the map with a smaller size.
     * @param map external map.
     * @return dot product.
     */
    public double dotProduct(Map<String, BigInteger> map) {
        if (map == null) {
            return 0;
        }
        double dot = 0;
        //System.out.println(map.size()); System.out.println(strMap.size());
        if (map.size() > strMap.size()) {
            Iterator<String> itr = strMap.keySet().iterator();
            while (itr.hasNext()) {
                String dummy = itr.next();
                if (map.containsKey(dummy)) {
                    dot = dot + (((map.get(dummy).doubleValue()) * (strMap.get(dummy).doubleValue())));
                }
            }
        } else {
            Iterator<String> itr = map.keySet().iterator();
            while (itr.hasNext()) {
                String dummy = itr.next();
                if (strMap.containsKey(dummy)) {
                    dot = dot + (((map.get(dummy).doubleValue()) * (strMap.get(dummy).doubleValue())));
                }
            }
        }
        return dot;
    }
    /**
     * method to calculate distance between two documents/dictionaries.
     * @param map external map.
     * @return 0 if documents are totally similar, 1 if not at all similar and in between for anything else.
     */
    public double distance(Map<String, BigInteger> map) {
        if (map == null) {
            return Math.PI / 2.0;
        }
        double d1 = euclideanNorm();
        double d2 = euclideanNorm1(map);
        if (Double.compare(d1, 0) == 0 || Double.compare(d2, 0) == 0) {
            return Math.PI / 2.0;
        }
/*        if (Double.compare(d1, d2) == 0) {
            return 0;
        }*/
        double prod = dotProduct(map) / ((d1 * d2));
        if (prod > 1) {
            return 0;
        }
        return Math.acos(prod);
    }
    /**
     * Private helper method for calculating euclidean Norm of external map.
     * @param map external map.
     * @return euclidean norm.
     */
    private double euclideanNorm1(Map<String, BigInteger> map) {
        if (map == null) {
            return 0;
        }
        double sum = 0;
        Iterator<BigInteger> itr = map.values().iterator();
        while (itr.hasNext()) {
            sum = sum + Math.pow(itr.next().doubleValue(), 2);
        }
        return Math.pow(sum, 0.5);
    }
    /**
     * Getter for private Map.
     * @return deep copy of Map.
     */
    public Map<String, BigInteger> getMap() {
        Map<String, BigInteger> dummy = new HashMap<String, BigInteger>();
        Iterator<String> itr = strMap.keySet().iterator();
        while (itr.hasNext()) {
            String dum = itr.next();
            dummy.put(dum, strMap.get(dum));
        }
        return dummy;
    }
    /**
     * Simple private helper method to validate a word.
     * @param text text to check
     * @return true if valid, false if not
     */
    private boolean isWord(String text) {
        return text != null && text.matches("[a-zA-Z]+");
    }
}
