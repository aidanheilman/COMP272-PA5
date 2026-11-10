# COMP 272 — Assignment 5: Hashing

**Total: 100 points**

## Exercise 1 — Complete `ProbeHashMap` (50 points)

Use the provided [ProbeHashMap.java](https://github.com/Kennaoui/COMP272_Fall26/blob/main/M05/ProbeHashMap.java).

### Q1. Comment the code (8 points)

Add comments before each existing constructor and method explaining what it does and how it works. Use the comments provided before the methods you will implement in Q2 as examples of how to document your code.

### Q2. Implement the missing methods (30 points)

Complete the following methods according to the comments in the starter file:

- `V remove(K key)`
- `boolean remove(K key, V value)`
- `boolean replace(K key, V value)`
- `V replace(K key, V oldValue, V newValue)`

### Q3. Explain tombstones (6 points)

Complete the comment at the end of the file explaining the role of tombstones. Include a small example involving colliding keys and a deletion.

### Q4. Test your implementation (6 points)

Add tests in a `main` method. Cover successful and unsuccessful operations, collisions, deletion, and reuse of tombstones. Clearly show expected and actual results.

## Exercise 2 — Hashing Problems (50 points)

Create `HashingProblems.java`. Use Java's library `HashMap` or `HashSet` for this exercise, rather than your `ProbeHashMap`. Assume non-null inputs and non-null map keys and values. Do not modify the inputs.

### Q1. `getAverage` (15 points)

```java
public static double getAverage(int[] keys, HashMap<Integer, Integer> map)
```

Compute the average of the values associated with keys that appear in both the array and the map. Count each matching key once. Return `0.0` if there are no matches.

### Q2. `odd` (10 points)

```java
public static ArrayList<Integer> odd(HashMap<Integer, Integer> map)
```

Return the values associated with odd keys, including negative odd keys. Include one value per qualifying key; the order does not matter. Return an empty list if there are no odd keys.

### Q3. `twoSums` (20 points)

```java
public static int twoSums(int[] numbers, int k)
```

Count the distinct unordered value pairs whose absolute difference is `k`, where `k >= 0`. Each pair must use two different array positions. Duplicate occurrences do not create additional value pairs; when `k == 0`, a value must occur at least twice to form a pair.

For example, `[1, 1, 3, 5]` with `k = 2` gives **2** pairs: `(1, 3)` and `(3, 5)`.

Use a `HashMap` or `HashSet` to achieve **expected O(n) time**. Briefly explain your approach, time complexity, and auxiliary space complexity in a comment. You may assume integer arithmetic does not overflow.

### Testing (5 points)

Add a `main` method that tests all three methods, including relevant edge cases. Clearly show expected and actual results.

## Submission

Submit two files that compile and run:

- `ProbeHashMap.java`
- `HashingProblems.java`
