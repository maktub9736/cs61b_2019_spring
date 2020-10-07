package bearmaps;

import java.util.List;

public class KDTree implements PointSet {
    private TreeNode root;
    public int calTimes = 0;

    public KDTree(List<Point> points) {
        root = new TreeNode(points.get(0));
        root.isOddLevel = false;
        for (int i = 1; i < points.size(); i++) {
            root.addNode(new TreeNode(points.get(i)));
        }
    }

    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        return findNearest(root, goal, root).point;
    }

    private TreeNode findNearest(TreeNode n, Point goal, TreeNode best) {
        if (n == null) {
            return best;
        }
        calTimes++;
        if (n.distance(goal) < best.distance(goal)) {
            best = n;
        }
        TreeNode goodSide;
        TreeNode badSide;
        if (n.compareTo(goal) <= 0) {
            goodSide = n.left;
            badSide = n.right;
        } else {
            goodSide = n.right;
            badSide = n.left;
        }
        best = findNearest(goodSide, goal, best);
        if (n.partDistance(goal) < best.distance(goal)) {
            best = findNearest(badSide, goal, best);
        }
        return best;
    }

    private class TreeNode implements Comparable<Point> {
        private Point point;
        public TreeNode left;
        public TreeNode right;
        public boolean isOddLevel;     // false: compare x; true: compare y

        public TreeNode() {}

        public TreeNode(Point p) {
            this.point = p;
        }

        public double distance(Point other) {
            return Point.distance(this.point, other);
        }

        public double partDistance(Point other) {
            if (this.isOddLevel) {
                return Math.pow(this.point.getY() - other.getY(), 2);
            } else {
                return Math.pow(this.point.getX() - other.getX(), 2);
            }
        }

        public boolean reverse() {
            return !isOddLevel;
        }

        @Override
        public int compareTo(Point other) {
            if (this.isOddLevel) {
                return Double.compare(this.point.getY(), other.getY());
            } else {
                return Double.compare(this.point.getX(), other.getX());
            }
        }

        public void addNode(TreeNode other) {
            if (compareTo(other.point) < 0) {
                if (this.left == null) {
                    this.left = other;
                    other.isOddLevel = this.reverse();
                } else {
                    this.left.addNode(other);
                }
            } else {
                if (this.right == null) {
                    this.right = other;
                    other.isOddLevel = this.reverse();
                } else {
                    this.right.addNode(other);
                }
            }
        }
    }

}