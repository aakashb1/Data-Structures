/**
 * 08722 Data Structures for Application Programmers.
 *
 * Homework Assignment 4
 * HashTable Implementation with linear probing
 *
 * Andrew ID: AAKASHB1
 * @author Aakash Bhatia
 */
public class MyHashTable implements MyHTInterface {
    /**
     * constant for default capacity of the array.
     */
    private static final int DEFAULT_CAPACITY = 10;
    /**
     * Constant to indicate item in a cell (index) has been deleted.
     */
    private static final DataItem DELETED = new DataItem("#DEL#");
    /**
     * Instance variable for initial number of elements in the hashtable.
     */
    private int capacity = 0;
    /**
     * Instance variable for numberOfCollisions.
     */
    private int numberOfCollisions = 0;
    /**
     * Underlying array of DataItem.
     */
    private DataItem[] hashArray;
    /**
     * No-args constructor.
     */
    public MyHashTable() {
        hashArray = new DataItem[DEFAULT_CAPACITY];
    }
    /**
     * Constructs a new hashtable with the specified initial capacity.
     * precondition: initialCapacity is a positive integer (not equal to zero)
     * @param initialCapacity initial capacity of the table
     */
    public MyHashTable(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException();
        } else {
            hashArray = new DataItem[initialCapacity];
        }
    }
    /**
     * Instead of using String's hashCode, you are to implement your own here.
     * You need to take the table length into your account in this method.
     *
     * In other words, you are to combine the following two steps into one step.
     * 1. converting Object into integer value
     * 2. compress into the table using modular hashing (division method)
     *
     * Helper method to hash a string for English lowercase alphabet and blank,
     * we have 27 total. But, you can assume that blank will not be added into
     * your table. Refer to the instructions for the definition of words.
     *
     * For example, "cats" : 3*27^3 + 1*27^2 + 20*27^1 + 19*27^0 = 60,337
     *
     * But, to make the hash process faster, Horner's method should be applied as follows;
     *
     * var4*n^4 + var3*n^3 + var2*n^2 + var1*n^1 + var0*n^0 can be rewritten as
     * (((var4*n + var3)*n + var2)*n + var1)*n + var0
     *
     * Note: You must use 27 for this homework.
     *
     * However, if you have time, I would encourage you to try with other
     * constant values than 27 and compare the results but it is not required.
     * @param input input string for which the hash value needs to be calculated
     * @return int hash value of the input string
     */
    private int hashFunc(String input) {
        int check = 0;
        check = power(0, 0, input);
        return check;
    }
    /**
     * Recursive function that Returns value of the input
     *  string using (((var4*n + var3)*n + var2)*n + var1)*n + var0.
     * @param input String value to calculate the integer equivalent.
     * @param sum recursively calculated sum.
     * @param ind index of the character in the string.
     * @return integer equivalent value of the string.
     */
    private int power(int sum, int ind, String input) {
        int n = 27;
        if (ind == input.length()) {
            return sum;
        } else if (ind == input.length() - 1) {
            sum = sum + (input.charAt(ind) - 96);
            sum = sum % hashArray.length;
            return sum;
        } else {
            sum = (sum + (input.charAt(ind) - 96)) * n;
            sum = sum % hashArray.length;
            return power(sum, ind + 1, input);
        }
        }
    /**
     * Returns the next immediate prime number given a number.
     * @param num input number.
     * @return integer value of the next immediate prime.
     */
    private int nextPrime(int num) {
        if (isPrime(num)) {
            return num;
        }
        return nextPrime(num + 1);
    }
    /**
     * Validates if a number is prime or not.
     * @param prime number to be validated.
     * @return boolean true or false, true if prime else false.
     */
    private boolean isPrime(int prime) {
        if (prime % 2 == 0) {
            return false;
        } else {
            for (int i = 3; i * i < prime; i = i + 2) {
                if (prime % i == 0) {
                    return false;
                }
            }
            return true;
        }
    }
    /**
     * Inserts a new String value (word).
     * Frequency of each word to be stored too.
     * @param value String value to add
     */
    @Override
    public void insert(String value) {
        if (check(value)) {
                DataItem newItem = new DataItem(value);
                newItem.setFrequency(1);
                float curLF = (float) 1 / hashArray.length;
                if (loadFactor() + curLF > 0.5) {
                    rehash();
                }
                int hashVal = hashValue(value);
                int initialVal = hashVal;
                while ((hashArray[hashVal] != null) && (hashArray[hashVal] != DELETED)) {
                    if (hashArray[hashVal].value.compareTo(value) == 0) {
                        hashArray[hashVal].setFrequency(hashArray[hashVal].getFrequency() + 1);
                        return;
                    }
                    hashVal++;
                    hashVal = hashVal % hashArray.length;
                }
                hashArray[hashVal] = newItem;
                capacity = capacity + 1;
                int count = 0;
                hashVal = initialVal;
                while (count < hashArray.length) {
                    if ((hashArray[hashVal] != null) && (hashArray[hashVal] != DELETED)) {
                        if (Integer.compare(hashValue(hashArray[hashVal].value), initialVal) == 0) {
                            if (hashArray[hashVal].value.compareTo(value) != 0) {
                                numberOfCollisions = numberOfCollisions + 1;
                                break;
                            }
                        }
                    }
                    hashVal = hashVal + 1;
                    hashVal = hashVal % hashArray.length;
                    count = count + 1;
                }
                return;
            } else {
                return;
                }
        }
    /**
     * Inserts a string value to the temp array along with the frequency.
     * Frequency of each word to be stored too.
     * @param value String value to add.
     * @param freq1 Frequency value to be stored.
     */
    private void insertNew(String value, int freq1) {
        DataItem newItem = new DataItem(value);
        newItem.setFrequency(freq1);
        int hashVal = hashValue(value);
        if (hashArray[hashVal] != null && !contains(value)) {
            numberOfCollisions = numberOfCollisions + 1;
        }
        while ((hashArray[hashVal] != null) && (hashArray[hashVal] != DELETED)) {
            hashVal++;
            hashVal = hashVal % hashArray.length;
        }
        hashArray[hashVal] = newItem;
    }
    /**
     * doubles array length and rehash items whenever the load factor is reached.
     */
    private void rehash() {
        DataItem[] temp = hashArray;
        int newLength = nextPrime(2 * hashArray.length);
        int items = (int) (loadFactor() * hashArray.length);
        System.out.printf("Rehashing %d items, new length is %d \n", items + 1, newLength);
        hashArray = new DataItem[newLength];
        capacity = 0;
        numberOfCollisions = 0;
        for (int i = 0; i < temp.length; i = i + 1) {
            if (temp[i] != null && temp[i] != DELETED) {
                capacity = capacity + 1;
                insertNew(temp[i].value, temp[i].frequency);
            }
        }
    }
    /**
     * Returns the size, number of items, of the table.
     * @return the number of items in the table
     */
    @Override
    public int size() {
        return capacity;
    }
    /**
     * Displays the values of the table.
     * If an index is empty, it shows **
     * If previously existed data item got deleted, then it should show #DEL#
     */
    @Override
    public void display() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hashArray.length; i = i + 1) {
            if (hashArray[i] == null) {
                sb.append("** ");
            } else if (hashArray[i] == DELETED) {
                sb.append("#DEL# ");
            } else {
                sb.append("[").append(hashArray[i].value).append(", ").append(hashArray[i].frequency).append("] ");
            }
        }
        System.out.println(sb);
    }
    /**
     * Returns true if value is contained in the table.
     * @param key String key value to search
     * @return true if found, false if not found.
     */
    @Override
    public boolean contains(String key) {
        int hashValue = hashFunc(key);
        int count = 0;
        if (hashValue < 0) {
            return false;
        }
        while (hashArray[hashValue] != null && count < hashArray.length) {
            count = count + 1;
            if (hashArray[hashValue].value.compareTo(key) == 0) {
                return true;
            }
            hashValue = hashValue + 1;
            hashValue = hashValue % hashArray.length;
        }
        return false;
    }
    /**
     * Returns the number of collisions in relation to insert and rehash.
     * When rehashing process happens, the number of collisions should be properly updated.
     *
     * The definition of collision is "two different keys map to the same hash value."
     * Be careful with the situation where you could overcount.
     * Try to think as if you are using separate chaining.
     * "How would you count the number of collisions?" when using separate chaining.
     * @return number of collisions
     */
    @Override
    public int numOfCollisions() {
        return numberOfCollisions;
    }
    /**
     * Returns the hash value of a String.
     * Assume that String value is going to be a word with all lowercase letters.
     * @param value value for which the hash value should be calculated
     * @return int hash value of a String
     */
    @Override
    public int hashValue(String value) {
        int hashVal = hashFunc(value);
        return hashVal;
    }
    /**
     * Returns the loadfactor of the current hashArray.
     * @return lf float loadFactor.
     */
    public float loadFactor() {
        float lf = (float) capacity / hashArray.length;
        return lf;
    }
    /**
     * Returns the frequency of a key String.
     * @param key string value to find its frequency
     * @return frequency value if found. If not found, return 0
     */
    @Override
    public int showFrequency(String key) {
        if (contains(key)) {
            int hash = getHashValue(key);
            if (hash != 0) {
                return hashArray[hash].getFrequency();
            }
            return 0;
        } else {
            return 0;
        }
    }
    /**
     * Fetches current hashValue of input string.
     * @param key String to get hash value of.
     * @return 0 if hashArray does not contain the input string else returns the hashvalue.
     */
    private int getHashValue(String key) {
        if (contains(key)) {
            int hashValue = hashFunc(key);
            while (hashArray[hashValue] != null) {
                if (hashArray[hashValue].value.compareTo(key) == 0) {
                    return hashValue;
                }
                hashValue = hashValue + 1;
                hashValue = hashValue % hashArray.length;
            }
        }
        return 0;
    }
    /**
     * Removes and returns removed value.
     * @param key String to remove
     * @return value that is removed. If not found, return null
     */
    @Override
    public String remove(String key) {
        if (contains(key)) {
            int index = getHashValue(key);
            String discarded = hashArray[index].value;
            hashArray[index] = DELETED;
            capacity = capacity - 1;
            return discarded;
        }
        return null;
    }
    /**
     * private static data item nested class.
     */
    private static class DataItem {
        /**
         * String value.
         */
        private String value;
        /**
         * String value's frequency.
         */
        private int frequency;
        /**
         * Constructor for inner static class dataItem.
         * @param str sets value of the new instance of dataItem created.
         */
        DataItem(String str) {
            value = str;
        }
        /**
         * Setter method for frequency.
         * @param freq sets frequency if current dataItem.
         */
        public void setFrequency(int freq) {
            frequency = freq;
        }
        /**
         * Getter method for frequency.
         * @return frequency of corresponding dataItem.
         */
        public int getFrequency() {
            return frequency;
        }
    }
    /**
     * Simple private helper method to validate a word.
     * @param s text to check
     * @return true if valid, false if not
     */
    private boolean check(String s) {
        return s != null && s.matches("[a-z]+");
    }
}
