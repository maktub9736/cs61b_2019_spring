import java.util.Arrays;

public class UnionFind {
    private int N;
    private int[] lst;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        N = n;
        lst = new int[N];
        Arrays.fill(lst, -1);
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex >= N || vertex < 0) {
            throw new IllegalArgumentException("Invalid index");
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        if (lst[v1] < 0) {
            return -lst[v1];
        }
        else {
            return sizeOf(lst[v1]);
        }
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        return lst[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        validate(v1);
        validate(v2);
        if (v1 == v2 || connected(v1, v2)) {return;}
        else {
            if (sizeOf(v1) >= sizeOf(v2)) { // set 1 attach to the root of set 2
                lst[find(v1)] = find(v2);
                lst[find(v2)] -= sizeOf(v1);
            }
            else {
                lst[find(v2)] = find(v1);
                lst[find(v1)] -= sizeOf(v2);
            }
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        /* First, find the root. */
        int root = findRoot(vertex);

        /* Second, path-compression. */
        int pointer = vertex;
        while (parent(pointer) >=0 && parent(pointer) != root) {
            lst[pointer] = root;
            pointer = parent(pointer);
        }

        return root;
    }

    /** helps to identify the root. */
    private int findRoot(int vertex) {
        int par = parent(vertex);
        if (par >= 0) { // has a parent
            return findRoot(par);
        }
        else {
            return vertex;
        }
    }
}
