import java.util.*;

/** @author Yuan Cao
 *  Reference: https://algs4.cs.princeton.edu/34hash/SeparateChainingHashST.java.html
 *  */

public class MyHashMap<K, V> implements Map61B<K, V> {
    private int n = 0;   // number of key-value pairs
    private int m;   // hash table size
    private double loadFactor = 0.75;
    public Set<K> keys = new HashSet<K>();
    private ArrayList<V> searchKey;

    public MyHashMap() {
        m = 16;
        searchKey = new ArrayList<V>();
        for (int i = 0; i < m; i++) {
            searchKey.add(i, null);
        }
    }

    public MyHashMap(int initialSize) {
        m = initialSize;
        searchKey = new ArrayList<V>();
        for (int i = 0; i < m; i++) {
            searchKey.add(i, null);
        }
    }

    public MyHashMap(int initialSize, double loadFactor) {
        m = initialSize;
        this.loadFactor = loadFactor;
        searchKey = new ArrayList<V>();
        for (int i = 0; i < m; i++) {
            searchKey.add(i, null);
        }
    }

    @Override
    public void clear() {
        n = 0;
        for (int i = 0; i < m; i++) {
            searchKey.set(i, null);
        }
    }

    @Override
    public boolean containsKey(K k) {
        if (n == 0) {
            return false;
        }
        return keys.contains(k);
    }

    @Override
    public V get(K key) {
        int i = hash(key);
        return searchKey.get(i);
    }

    private int hash(K k) {
        return k.hashCode() & 0x7fffffff % m;
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public void put(K key, V value) {
        if (!containsKey(key)) {
            n += 1;
            keys.add(key);
            searchKey.set(hash(key), value);
        } else {
            searchKey.set(hash(key), value);
        }
        if (n / m > loadFactor) {
            resize(2 * m);
        }
    }

    private void resize(int newSize) {
        MyHashMap temp = new MyHashMap<K, V>(newSize);
        for (K key : keys) {
            temp.put(key, this.get(key));
        }
        this.n = temp.n;
        this.m = temp.m;
        this.searchKey = temp.searchKey;
    }

    @Override
    public Set<K> keySet() {
        return keys;
    }

    @Override
    public V remove(K key) {
        if (!keys.contains(key)) {
            return null;
        } else {
            int i = hash(key);
            keys.remove(key);
            n -= 1;
            V removedValue = searchKey.remove(i);
            searchKey.add(i, null);
            return removedValue;
        }
    }

    @Override
    public V remove(K key, V value) {
        if (!keys.contains(key) || (keys.contains(key) && searchKey.get(hash(key)) != value)) {
            return null;
        } else {
            int i = hash(key);
            keys.remove(key);
            n -= 1;
            V removedValue = searchKey.remove(i);
            searchKey.add(i, null);
            return removedValue;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return keys.iterator();
    }

    public static void main(String[] args) {
        ArrayList a = new ArrayList(5);
        ArrayList b = new ArrayList();
        for (int i = 0; i < 5; i++) {
            b.add(i, null);
        }
        b.add(3,10);
        int c = 10;
        System.out.println(b.size());
    }
}
