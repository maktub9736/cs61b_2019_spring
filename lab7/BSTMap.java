import java.util.*;
import java.util.HashSet;

public class BSTMap<K extends Comparable, V> implements Map61B<K, V> {
    private Node root;             // root of BST
    private Set<K> keySet;

    private class Node {
        private K key;             // sorted by key
        private V val;             // associated data
        private Node left, right;  // left and right subtrees
        private int size;          // number of nodes in subtree

        public Node(K key, V val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    public BSTMap() {
        keySet = new HashSet<K>();
    }

    public void clear() {
        root = null;
    }

    public boolean containsKey(K k) {
        return get(k) != null;
    }

    public V get(K k) {
        if (k != null) {
            return get(root, k);
        } else {
            throw new IllegalArgumentException("Argument is null");
        }
    }

    private V get(Node x, K k) {
        if (x == null) {
            return null;
        } else {
            int cmp = k.compareTo(x.key);
            if (cmp < 0) {
                return get(x.left, k);
            } else if (cmp > 0) {
                return get(x.right, k);
            } else {
                return x.val;
            }
        }
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        } else {
            return x.size;
        }
    }

    public void put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        root = put(root, key, value);
    }

    private Node put(Node x, K key, V value) {
        if (x == null) {
            keySet.add(key);
            return new Node(key, value, 1);
        } else {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x.left = put(x.left,  key, value);
            } else if (cmp > 0) {
                x.right = put(x.right, key, value);
            } else {
                x.val = value;
            }
            x.size = 1 + size(x.left) + size(x.right);
            return x;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIter();
    }

    private class BSTMapIter implements Iterator<K> {
        private Queue<Node> que;

        public BSTMapIter() {
            que = new LinkedList<Node>();
            keys(root, que);
        }

        private void keys(Node x, Queue<Node> q) {  // Add Nodes to queue
            if (x == null) {
                return;
            }
            keys(x.left, q);
            q.add(x);
            keys(x.right, q);
        }

        @Override
        public boolean hasNext() {
            return que.size() > 0;
        }

        @Override
        public K next(){
            K ret = que.poll().key;
            return ret;
        }

    }

    
    public void printInOrder() {
        for (Iterator<K> ki = new BSTMapIter(); ki.hasNext();) {
            K k = ki.next();
            System.out.println(k + " : " + this.get(k));
        }
    }

    @Override
    public V remove(K key) {
        if (!keySet.contains(key)) {
            return null;
        } else {
            V getVal = get(key);
            root = remove(root, key);
            return getVal;
        }
    }

    @Override
    public V remove(K key, V value) {
        if (!keySet.contains(key)) {
            return null;
        } else {
            V getVal = get(key);
            if (getVal == value) {
                root = remove(root, key);
                return value;
            } else {
                return null;
            }
        }
    }

    private Node remove(Node x, K k) {  // Help function: we are sure k is in the keySet
        if (size(x) == 1) { // x is leaf
                keySet.remove(k);
                return null;
        } else {  // x has at least one child
            x.size -= 1;
            int cmp = k.compareTo(x.key);
            if (cmp < 0) {
                x.left = remove(x.left, k);
            } else if (cmp > 0) {
                x.right = remove(x.right, k);
            } else {
                if (size(x.left) == 0) {
                    x = x.right;
                } else if (size(x.right) == 0) {
                    x = x.left;
                } else { // x has children on both sides
                    Node newX = min(x.right);
                    x.key = newX.key;
                    x.val = newX.val;
                    x.right = remove(x.right, newX.key);
                    keySet.add(newX.key);
                }
                keySet.remove(k);
            }
            return x;
        }
    }

    private Node min(Node x) {
        while (size(x.left) > 0) {
            return min(x.left);
        }
        return x;
    }

    @Override
    public Set<K> keySet() {
        return keySet;
    }

    public static void main(String[] args) {
        BSTMap temp = new BSTMap();
        temp.put(1, 15);
        temp.put(3, 10);
        temp.put(6, 15);
        temp.put(9, 10);
        temp.put(5, 18);
        temp.put(4, 17);
        temp.put(2, 19);
        temp.put(8, 25);
        temp.put(7, 16);
//        temp.printInOrder();
        temp.remove(1);
        temp.printInOrder();
    }
}
