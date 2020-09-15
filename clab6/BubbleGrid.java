public class BubbleGrid extends UnionFind {
    private int[][] grid;
    private int height;
    private int width;
    private int[][] marked;

    /* Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space. */
    public BubbleGrid(int[][] grid) {
        super(grid.length * grid[0].length + 1);
        this.grid = grid;
        height = grid.length;
        width = grid[0].length;
    }

    /* Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid. */
    public int[] popBubbles(int[][] darts) {
        int s0 = count();
        int[] output = new int[darts.length];
        for (int i = 0; i < darts.length; i++) {
            int[] dart = darts[i];
            if (!isBubble(dart[0], dart[1])) {
                output[i] = 0;
            }
            else {
                grid[dart[0]][dart[1]] = 5;  // Temporarily mark pierced bubble to 5
                int s1 = count();
                output[i] = s1 < s0 ? s0 - s1 - 1: 0;
                grid[dart[0]][dart[1]] = 1;  // Reset grid
            }
        }
        return output;
    }

    private int count() {
        resetLst();
        marked = new int[height][width];
        for (int j = 0; j < width; j++) {
            UnionNeighbors(0, j);
        }
        return sizeOf(0);
    }

    /* Use DFS to union all the bubbles that are stuck. */
    private void UnionNeighbors(int x, int y) {
        if (isBubble(x, y) && marked[x][y] == 0) {
            marked[x][y] = 1;
            if (x == 0) {  // Top bubbles, union(this, ceiling)
                union(gridToLst(x, y), 0);
            }
            int[][] dir = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for (int[] d: dir) { // If neighbor is bubble, union(this, neighbor) then recursion
                if (isBubble(x + d[0], y + d[1])) {
                    union(gridToLst(x, y), gridToLst(x + d[0], y + d[1]));
                    UnionNeighbors(x + d[0], y + d[1]);
                }
            }
        }
    }

    private boolean isBubble(int x, int y) {
        if (x >= 0 && x < height && y >= 0 && y < width) {
            return grid[x][y] == 1;
        }
        else { return false; }
    }

    private int gridToLst(int x, int y) {
        return x * width + y + 1;  // Lst[0] is the ceiling
    }

    public static void main(String[] args) {
        int[][] grid = new int[][] {{1, 1, 0},{1, 0, 0},{1, 1, 0},{1, 1, 1}};
        BubbleGrid b = new BubbleGrid(grid);
        int[][] darts = new int[][] {{2, 2}, {2, 0}};
        int[] a = b.popBubbles(darts);
        for (int i = 0; i < a.length; i++){
            System.out.print(a[i]+ " ");
        }
    }
}
