public class BubbleGrid{
    public int[][] G;
    public int height;
    public int width;
    private int[][] marked;

    public BubbleGrid(int[][] grid) {
        G = grid;
        height = grid.length;
        width = grid[0].length;
    }

    private int size() {
        int sum = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                sum += G[i][j];
            }
        }
        return sum;
    }

    private void shoot(int[] pos) {
        int x = pos[0];
        int y = pos[1];
        marked = new int[height][width];

        if (G[x][y] == 0) { return; }
        else {
            G[x][y] = 0;
            for (int j = 0; j < width; j++) {
                if (G[0][j] == 1) {
                    mark(0, j);
                }
            }
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (G[i][j] == 1) {
                        G[i][j] = 0;
                    }
                    else if (G[i][j] == 5) {
                        G[i][j] = 1;
                    }
                }
            }
            return;
        }
    }

    private void mark(int x, int y) { // mark "immune" cells
        if (x >= 0 && x < height && y >= 0 && y < width
                && marked[x][y] != 1 && G[x][y] == 1) {
            G[x][y] = 5;
            marked[x][y] = 1;
            mark(x - 1, y);
            mark(x + 1, y);
            mark(x, y - 1);
            mark(x, y + 1);
        }
        else return;
    }


    public int[] popBubbles(int[][] darts) {
        int len = darts.length;
        int[] outList = new int[len];
        for (int n = 0; n < len; n++) {
            int s1 = size();
            shoot(darts[n]);
            int s2 = size();
            if (s1 == s2) {
                outList[n] = 0;
            }
            else {
                outList[n] = s1 - s2 - 1;
            }
        }
        return outList;
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
