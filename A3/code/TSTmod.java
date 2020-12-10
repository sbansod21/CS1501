//TST Mod taken from Lab6 cuz the methods in the original tst file
//dont allow StringBuilder
public class TSTmod<Value> {
    private int n; // size
    private Node<Value> root; // root of TST

    private static class Node<Value> {
        private char c; // character
        private Node<Value> left, mid, right; // left, middle, and right subtries
        private Value val; // value associated with string
    }

    /**
     * Initializes an empty string symbol table.
     */
    public TSTmod() {
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     * 
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return n;
    }

    /**
     * Does this symbol table contain the given key?
     * 
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key} and
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(StringBuilder key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key.
     * 
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol
     *         table and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(StringBuilder key) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with null argument");
        }
        if (key.length() == 0)
            throw new IllegalArgumentException("key must have length >= 1");
        Node<Value> x = get(root, key, 0);
        if (x == null)
            return null;
        return x.val;
    }

    // return subtrie corresponding to given key
    private Node<Value> get(Node<Value> x, StringBuilder key, int d) {
        if (x == null)
            return null;
        if (key.length() == 0)
            throw new IllegalArgumentException("key must have length >= 1");
        char c = key.charAt(d);
        if (c < x.c)
            return get(x.left, key, d);
        else if (c > x.c)
            return get(x.right, key, d);
        else if (d < key.length() - 1)
            return get(x.mid, key, d + 1);
        else
            return x;
    }

    /**
     * Inserts the key-value pair into the symbol table, overwriting the old value
     * with the new value if the key is already in the symbol table. If the value is
     * {@code null}, this effectively deletes the key from the symbol table.
     * 
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(StringBuilder key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with null key");
        }
        if (!contains(key))
            n++;
        root = put(root, key, val, 0);
    }

    private Node<Value> put(Node<Value> x, StringBuilder key, Value val, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node<Value>();
            x.c = c;
        }
        if (c < x.c)
            x.left = put(x.left, key, val, d);
        else if (c > x.c)
            x.right = put(x.right, key, val, d);
        else if (d < key.length() - 1)
            x.mid = put(x.mid, key, val, d + 1);
        else
            x.val = val;
        return x;
    }

    public StringBuilder longestPrefixOf(StringBuilder query) {
        if (query == null) {
            throw new IllegalArgumentException("calls longestPrefixOf() with null argument");
        }
        if (query.length() == 0)
            return null;
        int length = 0;
        Node<Value> x = root;
        int i = 0;
        while (x != null && i < query.length()) {
            char c = query.charAt(i);
            if (c < x.c)
                x = x.left;
            else if (c > x.c)
                x = x.right;
            else {
                i++;
                if (x.val != null)
                    length = i;
                x = x.mid;
            }
        }
        return new StringBuilder(query.substring(0, length));
    }

}