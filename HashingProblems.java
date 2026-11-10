import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

// Assume non-null inputs and non-null map keys and values.

public class HashingProblems {
    /** Problem 1
     * Compute the average of the values associated with keys that appear in 
     * both the array and the map. Count each matching key once. 
     * Return `0.0` if there are no matches.
     *
     * @param keys
     * @param map
     * @return double
     */
    public static double getAverage(int[] keys, HashMap<Integer, Integer> map) {
        double sum = 0.0; // Init vars to be used in avg calculation
        int n = 0;
        for (int key : keys) { 
            Integer val = map.get(key);
            if (val != null) {sum += val; n++;} // If there was a match, add it to sum and increase n by 1
        }
        if (n == 0) {return sum;} // If no matches, return 0.0. Done before arithmetic to avoid 0/0 error
        double avg = sum/n;
        return avg;    
    }

    /** Problem 2
     * Return the values associated with odd keys, including negative odd keys. 
     * Include one value per qualifying key; the order does not matter. 
     * Return an empty list if there are no odd keys.
     * 
     * @param map
     * @return ArrayList<Integer> 
     */
    public static ArrayList<Integer> odd(HashMap<Integer, Integer> map) {
        ArrayList<Integer> results = new ArrayList<>();
        for (int key : map.keySet()) {if (key%2 != 0) {results.add(map.get(key));}}
        return results;
    }

    /** Problem 3
     * Count the distinct unordered value pairs whose absolute difference is `k`, where `k >= 0`. 
     * Each pair must use two different array positions. Duplicate occurrences do not 
     * create additional value pairs; when `k == 0`, a value must occur at least twice to form a pair.
     * 
     * For example, `[1, 1, 3, 5]` with `k = 2` gives **2** pairs: `(1, 3)` and `(3, 5)`.
     * Use a `HashMap` or `HashSet` to achieve **expected O(n) time**. 
     * Briefly explain your approach, time complexity, and auxiliary space complexity in a comment. 
     * You may assume integer arithmetic does not overflow.
     * 
     * @param numbers
     * @param k
     * @return int
     * 
     * Put list into hashset, for (int key : numbers){if (set.contains(key+k) != null) {pairs ++}} O(2n), N auxiliary space for hash
     * need to solve for if k = 0 still
     * if (k == 0) can use add logic to determine if multiples -- .add() returns false if a val is already present
     * 
     * Approach:
     * Used HashSet because no values to be stored, only keys. Split into two possibilities, if k is not 0, and if it is. 
     * Important note that I had to troubleshoot: contains() returns only a boolean, not a count. For keeping track of duplicates
     * and ensuring that pairs wouldn't be counted more than once, I took advantage of the add() logic, which returns false upon
     * an attempt to add a value that already exists.
     * 
     * For the case that k is not zero, add all of the unique numbers to a HashSet called set.
     * Iterate through the set values and check if the set contains the current value + k, keeping track of a pair count for each true return.
     * For the case that k is zero, add all of the numbers to set while keeping track of if add returns true or false.
     * Every time it returns false (meaning 2 of the same number are in numbers), add that number to a separate HashSet called 
     * dupes (short for duplicates). The dupes.add() call also will only add one of each number, so this ensures that each duplicate number is 
     * only counted one time. Assign the final size of dupes to pairs.
     * 
     * Time Complexity:
     * Worst case O(2n) when k!=0 becuase two full iterations through numbers, assuming no duplicate values
     * 
     * Auxiliary Space Complexity:
     * Worst case O(n) 
     * Even though there are two HashSets when k == 0, Operating under the worst case assumption 
     * that each n has one duplicate, dupe will only ever hold maximum 0.5n, while set holds the other 0.5n.
     * Any further duplicates will not be added to set or dupes, and if there are no duplicates, set will hold all n and dupes will hold nothing.
     *
     */
    public static int twoSums(int[] numbers, int k) {
        int pairs = 0;
        HashSet<Integer> set = new HashSet<>();
        HashSet<Integer> dupes = new HashSet<>();
        if (k != 0) {
            // add() method will not add duplicates
            for (int num : numbers) {set.add(num);}
            for (int num : set) {if (set.contains(num+k)) {pairs++;}}
        } else {
            for (int num : numbers) {if (set.add(num) == false) {dupes.add(num);}}
            pairs = dupes.size();
        }
        return pairs;
    }
}