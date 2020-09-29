import java.util.ArrayList;
import java.util.List;

public class MyTrieSet implements TrieSet61B {
    private static final int R = 256;        // extended ASCII
    private Node root;      // root of trie
    private int n;          // number of keys in trie
    private Node curr;

    public MyTrieSet() {
        root = new Node();
        n = 0;
    }

    @Override
    public void clear() {
        root = new Node();
        n = 0;
    }

    @Override
    public boolean contains(String key) {
        boolean contain = containsPrev(key);
        if (contain && curr.isKey) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        curr = root;
        for (int i = 0, m = key.length(); i < m; i++) {
            char c = key.charAt(i);
            if (curr.next[c] == null) {
                curr.next[c] = new Node();
            }
            curr = curr.next[c];
        }
        if (!curr.isKey) {
            curr.isKey = true;
            this.n += 1;
        }
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        if (!containsPrev(prefix)) {
            return new ArrayList();
        } else {
            ArrayList keys = new ArrayList();
            getTo(prefix);
            collect(curr, prefix, keys);
            return keys;
        }
    }

    private void getTo(String word) {
        curr = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            curr = curr.next[c];
        }
    }

    private boolean containsPrev(String prefix) {  // if contains prefix, point to the last letter
        curr = root;
        for (int i = 0, m = prefix.length(); i < m; i++) {
            char c = prefix.charAt(i);
            if (curr.next[c] == null) {
                return false;
            }
            curr = curr.next[c];
        }
        return true;
    }

    private void collect(Node x, String prefix, ArrayList box) {
        if (x == null) {
            return;
        }
        for (int i = 0; i < 256; i++) {
            Node pointer = x.next[i];
            if (pointer != null) {
                char c = (char) i;
                String newPrefix = prefix + c;
                if (pointer.isKey) {
                    box.add(newPrefix);
                }
                collect(pointer, newPrefix, box);
            }
        }
    }

    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }

    public int size() {
        return n;
    }

    // R-way trie node
    private static class Node {
        private boolean isKey = false;
        private Node[] next = new Node[R];

        public Node(){}

        public Node(boolean isKey) {
            this.isKey = isKey;
        }
    }
}
