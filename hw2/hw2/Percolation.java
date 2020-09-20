package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.introcs.StdRandom;

public class Percolation{
    private int[][] grid;
    private int n;
    private WeightedQuickUnionUF uf;
    private int numOfOpen;

    public Percolation(int N) {                 // DONE: create N-by-N grid, with all sites initially blocked
        if (N <= 0) {
            throw new IllegalArgumentException();
        } else {
            this.n = N;
            grid = new int[N][N];
            uf = new WeightedQuickUnionUF(N * N + 1);
            numOfOpen = 0;
        }
    }

    /** Blocked -> 0; Open -> 1 */
    public void open(int row, int col) {        // DONE: open the site (row, col) if it is not open already
        if (!validate(row, col)) {
            throw new IndexOutOfBoundsException();
        } else if (isOpen(row, col)) {
            return;
        } else {
            grid[row][col] = 1;
            numOfOpen += 1;
            unionNeighbors(row, col);
            if (isTopRow(row, col)) {
                uf.union(0, xyTo1D(row, col));
            }
        }
    }

    public boolean isOpen(int row, int col) {   // DONE: is the site (row, col) open?
        if (!validate(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return grid[row][col] == 1;
    }

    public boolean isFull(int row, int col) {   // DONE: is the site (row, col) full?
        if (!validate(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return uf.connected(0, xyTo1D(row, col));
    }

    public int numberOfOpenSites() {            // DONE: number of open sites
       return numOfOpen;
    }

    public boolean percolates() {               // DONE: does the system percolate?
        for (int j = 0; j < n; j++) {
            if (uf.connected(xyTo1D(n - 1, j), 0)) {
                return true;
            }
        }
        return false;
    }

    private boolean validate(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < n;
    }

    private void unionNeighbors(int row, int col) {
        int[][] dir = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] d : dir) {
            if (validate(row + d[0], col + d[1]) && isOpen(row + d[0], col + d[1])) {
                uf.union(xyTo1D(row, col), xyTo1D(row + d[0], col + d[1]));
            }
        }
    }

    private int xyTo1D(int row, int col) {
        if (validate(row, col)) {
            return row * n + col + 1;     // ceiling is 0
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    private boolean isTopRow(int row, int col) {
        return validate(row, col) && row == 0;
    }

    public void openSite() {
        int a = StdRandom.uniform(n);
        int b = StdRandom.uniform(n);
        this.open(a, b);
    }

    public static void main(String[] args) {   // use for unit testing (not required, but keep this here for the autograder)
        int N = 5;

    }
}
