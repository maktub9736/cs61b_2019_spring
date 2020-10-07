package bearmaps;

import java.util.ArrayList;
import java.util.List;

public class NaivePointSet implements PointSet {
    private ArrayList<Point> pts;
    public int calTimes = 0;

    public NaivePointSet(List<Point> points) {
        pts = new ArrayList();
        for (int i = 0; i < points.size(); i++) {
            pts.add(points.get(i));
        }
    }

    @Override
    public Point nearest(double x, double y) {
        double d = -1;
        Point target = new Point(x, y);
        Point nearestPoint = pts.get(0);
        for (int i = 0; i < pts.size(); i++) {
            calTimes++;
            Point p = pts.get(i);
            double dst = Point.distance(p, target);
            if (d == -1 || dst < d) {
                d = dst;
                nearestPoint = p;
            }
        }
        return nearestPoint;
    }
}
