import java.util.Objects;

public class ProbeHashMap<K, V> {
    private static final Object AVAILABLE = new Object();   // tombstone
    private Object[] keys;
    private Object[] vals;
    private int n = 0;

    /**
     * ProbeHashMap Constructor
     *
     * @param capacity the desired capacity at construction
     * Corresponding key and value object attributed to the map,
     * each with n = capacity indices
     * 
     */
    public ProbeHashMap(int capacity) { keys = new Object[capacity]; vals = new Object[capacity]; }

    /**
     * Returns the compressed hash for the key passed as param
     *
     * @param key the key whose hash value should be compressed
     * @return the hash of the key, also the indice of the key
     * 
     * Hashes the key using java builtin
     * Uses bitwise AND 0x7fffffff to flip sign bit of hash to positive
     * Compresses now positive hash to fit in map capacity using modulo
     *
     */
    private int h(Object key) { return (key.hashCode() & 0x7fffffff) % keys.length; }

    /**
     * Returns index of key, or -1 if absent
     *
     * @param key the key whose index should be found
     * @return the index of the key, or -1 if not in map
     *
     * Iterates through hashmap linearly until key or null is found,
     * skipping over tombstones as needed
     * 
     */
    private int find(Object key) {
        int i = h(key);
        for (int p = 0; p < keys.length; p++) {
            if (keys[i] == null) return -1;                     // empty: stop
            if (keys[i] != AVAILABLE && keys[i].equals(key)) return i;
            i = (i + 1) % keys.length;                          // next cell
        }
        return -1;
    }

    /**
     * Returns the value associated with a given key, null otherwise
     *
     * @param key the key whose value should be returned
     * @return the associated value, or null if the key is not found
     *
     * Passes key into find()
     * If find returns -1, get returns null
     * Otherwise, find() returns the index of the key, which is
     * used to retrieve its corresponding value from vals[]
     * 
     */
    public V get(K key) { int i = find(key); return i < 0 ? null : (V) vals[i]; }

    /**
     * Adds a (key, value) pair to the hash map
     *
     * @param key the key to add to the hash map
     * @param value the corresponding value to add to the hash map
     * @return null if key was previously nonexistent
     * @return old value if key was already in the hash map
     * @throws IllegalStateException if table is at capacity
     *
     * If key already exists in table, replace old value with new value, return old value
     * If the table is already full, throw exception
     * If key is not in table and table is not full, insert new pair, update size, return null
     */
    public V put(K key, V value) {
        int i = find(key);
        if (i >= 0) { V old = (V) vals[i]; vals[i] = value; return old; }
        if (n == keys.length) throw new IllegalStateException("table full");
        i = h(key);
        while (keys[i] != null && keys[i] != AVAILABLE)          // first free cell
            i = (i + 1) % keys.length;
        keys[i] = key; vals[i] = value; n++;
        return null;
    }

    /**
     * Returns a string representation of all keys in the hashmap
     *
     * @return the string representation
     *
     * Iterates through all keys in the map
     * Uses java's builtin StringBuilder object for iteration efficiency
     * Null keys are represented by "_" char
     * Tombstones are represented by "A"
     * Otherwise, a string representation of the key object is appended.
     * I.e. if the key is apple, represented by "apple"
     * Every key is separated by a space in the overarching string representation
     * 
     */
    public String dump() {
        StringBuilder sb = new StringBuilder();
        for (Object k : keys) sb.append(k == null ? "_" : k == AVAILABLE ? "A" : k.toString()).append(' ');
        return sb.toString();
    }
    
    /**
     * Removes the entry associated with key, if present.
     *
     * @param key the key whose entry should be removed
     * @return the removed value, or null if the key is not found
     *
     * After a successful removal:
     * - Mark the tombstone.
     * - Return the stored value.
     * - Update the number of active entries.
     * Other entries must remain reachable through probing.
     */
    public V remove(K key) {
        int i = find(key);
        if (i >= 0) {V removed = (V) vals[i]; keys[i] = AVAILABLE; vals[i] = null; n --; return removed;}
        return null;        
    }

    /**
     * Removes the entry associated with key only if its current
     * value equals the specified value.
     *
     * @param key the key whose entry should be removed
     * @param value the value that must match the stored value
     * @return true if the entry was removed; false otherwise
     *
     * If the key is absent or its value does not match, leave the
     * map unchanged. A successful removal must use a tombstone
     * and update the number of active entries.
     * Compare values by equality, including possible null values.
     */
    public boolean remove(K key, V value) {
        int i = find(key);
        if (i >= 0 && Objects.equals(vals[i], value)) {keys[i] = AVAILABLE; vals[i] = null; n --; return true;}
        return false;
    }

    /**
     * Replaces the value associated with key only if the key
     * is already present in the map.
     *
     * @param key the key whose value should be replaced
     * @param value the new value to store
     * @return true if the replacement occurred; false otherwise
     *
     * If the key is absent, leave the map unchanged.
     * Do not insert a new entry or change the number of entries.
     * A key mapped to null is still considered present.
     */
    public boolean replace(K key, V value) {
        int i = find(key);
        if (i >= 0) { vals[i] = value; return true;}
        return false;
    }

    /**
     * Replaces the value associated with key with newValue only
     * if the key is present and its current value equals oldValue.
     *
     * @param key the key whose value should be replaced
     * @param oldValue the value that must match the stored value
     * @param newValue the new value to store
     * @return the previous value if replacement occurred;
     *         null otherwise
     *
     * If the key is absent or its value does not match, leave the
     * map unchanged. Do not change the number of entries.
     * Compare values by equality, including possible null values.
     * A successful replacement of a null value also returns null.
     */
    public V replace(K key, V oldValue, V newValue) {
        int i = find(key);
        if (i >= 0 && Objects.equals(vals[i], oldValue)) {V replaced = (V) vals[i]; vals[i] = newValue; return replaced;}
        return null;
    }

    /*
     * TODO: Explain the role of tombstones:
     * - How does a tombstone differ from a never-used slot?
     * - Why can replacing a deleted key with null break searches?
     * - How do searches and insertions handle tombstones?
     * Include a short example with colliding keys and a deletion.
     * 
     * Explaination of tombstones:
     * 
     * A tombstone is a special value used to replace a deleted node (i.e. AVAILABLE, -1, "T")
     * that distinctly differs from the default initialized placeholder value (i.e. null, 0) 
     * It denotes that the slot is available for insertion, but should be traversed over when 
     * searching for a key in the hash map. 
     * 
     * Replacing a deleted key with null breaks searches because the sentinel value that stops a 
     * looping search iteration is also null. Null acts as a signal to search that the collisions
     * are done in this cluster, and if the desired key was present, it would have been found by 
     * now. When a value in the middle of a cluster is deleted and replaced with null, search will
     * falsely terminate before it reaches the end of the cluster, creating a high likelihood that 
     * it will falsely return the result that the desired key is absent, when really it was just
     * further ahead in the cluster.
     * 
     * Searches handle tombstones by effectively treating them as filled slots. A search will
     * continue past a tombstone, just like it would continue past a key that does not match.
     * Insertions handle tombstones as if they were empty, unintialized slots. An insertion will
     * stop at a tombstone and insert the new key at that index.
     * 
     * Assume you have a capacity = 4 hash map holding 3 keys with a small cluster resulting from collisions:
     * [a, b, c, null]
     * Assume b is deleted, and replaced with null (NOT a tombstone)
     * [a, null, c, null]
     * Now, you want to search for c, which originally hashes to keys[0].
     * If you call search(c), it is supposed to first check keys[0] then keys[0+1], then find c at keys[0+2].
     * However, because it terminates the search upon finding a null value, it will stop at keys[0+1],
     * and falsely signal that c is not present, even though c is in fact present in the hash map. 
     * This is a problem.
     * 
     * Now, assume b is deleted, and replaced with a tombstone instead of null.
     * Additionally assume that search() was programmed to utilize the tombstone correctly.
     * The hash map now looks like this:
     * [a, TOMBSTONE, c, null]
     * Because search() is programmed to treat the tombstone as though it were a key, search(c) will do this:
     * Check keys[0], then check keys[0+1], then check keys[0+2], keys[2] is a match to c, this result is 
     * correctly returned, and the tombstone still allows for a new key to be inserted there later.
     * 
     * 
     * 
     */
    
    
}
