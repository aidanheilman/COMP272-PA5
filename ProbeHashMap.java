public class ProbeHashMap<K, V> {
    private static final Object AVAILABLE = new Object();   // tombstone
    private Object[] keys;
    private Object[] vals;
    private int n = 0;

    public ProbeHashMap(int capacity) { keys = new Object[capacity]; vals = new Object[capacity]; }

    private int h(Object key) { return (key.hashCode() & 0x7fffffff) % keys.length; }

    // Returns index of key, or -1 if absent
    private int find(Object key) {
        int i = h(key);
        for (int p = 0; p < keys.length; p++) {
            if (keys[i] == null) return -1;                     // empty: stop
            if (keys[i] != AVAILABLE && keys[i].equals(key)) return i;
            i = (i + 1) % keys.length;                          // next cell
        }
        return -1;
    }
   
    public V get(K key) { int i = find(key); return i < 0 ? null : (V) vals[i]; }

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
        // TODO: Implement this method.
        throw new UnsupportedOperationException("Not implemented yet.");
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
        // TODO: Implement this method.
        throw new UnsupportedOperationException("Not implemented yet.");
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
        // TODO: Implement this method.
        throw new UnsupportedOperationException("Not implemented yet.");
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
        // TODO: Implement this method.
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /*
     * TODO: Explain the role of tombstones:
     * - How does a tombstone differ from a never-used slot?
     * - Why can replacing a deleted key with null break searches?
     * - How do searches and insertions handle tombstones?
     * Include a short example with colliding keys and a deletion.
     */
    
    
}
