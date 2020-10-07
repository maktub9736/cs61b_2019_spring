package bearmaps;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import edu.princeton.cs.introcs.Stopwatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/** @author yuchengwto from github. Modified by Yuan Cao.*/

public class KDTreeTest {
    @Test
    public void sanityTest() {
        Point a = new Point(2, 3);
        Point b = new Point(4, 2);
        Point c = new Point(4, 5);
        Point d = new Point(3, 3);
        Point e = new Point(1, 5);
        Point f = new Point(4, 4);
        KDTree kd = new KDTree(List.of(a, b, c, d, e, f));
        assertEquals(e, kd.nearest(0, 7));
    }

    private static final Random r = new Random(2019);

    private Point getRandomPoint() {
        double x = r.nextDouble()*10;
        double y = r.nextDouble()*10;
        return new Point(x, y);
    }

    private List<Point> getRandomPoints(int n) {
        int i = 0;
        List<Point> points = new ArrayList<>(n);
        while (i != n) {
            points.add(getRandomPoint());
            i++;
        }
        return points;
    }

    private void testRandomNPointsMQueries(int n, int m) {
        List<Point> points = getRandomPoints(n);
        List<Point> testPoints = getRandomPoints(m);
        KDTree kd = new KDTree(points);
        NaivePointSet nps = new NaivePointSet(points);
        int countdiff = 0;

        for (Point p: testPoints) {
            Point actual = kd.nearest(p.getX(), p.getY());
            Point expect = nps.nearest(p.getX(), p.getY());
//            assertEquals(expect, actual);
            if (!actual.equals(expect)) {
                countdiff += 1;
            }
        }
        System.out.println("Wrong times: " + countdiff);
        System.out.println("KD calculated " + kd.calTimes + " times.");
        System.out.println("NPS calculated " + nps.calTimes + " times.");
    }

    private void testTimingNPointsMQueries(int n, int m) {
        List<Point> points = getRandomPoints(n);
        List<Point> testPoints = getRandomPoints(m);
        KDTree kd = new KDTree(points);
        NaivePointSet nps = new NaivePointSet(points);

        Stopwatch swkdt = new Stopwatch();
        for (Point p: testPoints) {
            kd.nearest(p.getX(), p.getY());
        }
        double kdTime = swkdt.elapsedTime();

        Stopwatch swnps = new Stopwatch();
        for (Point p: testPoints) {
            nps.nearest(p.getX(), p.getY());
        }
        double npsTime = swnps.elapsedTime();
        double diff = npsTime/kdTime;

        System.out.println("kdTime consume: "+ kdTime + ".");
        System.out.println("npsTime consume: " + npsTime + ".");
        System.out.println("KD calculated " + kd.calTimes + " times.");
        System.out.println("NPS calculated " + nps.calTimes + " times.");
        System.out.println("KD is " + Math.round(diff) + " times faster than NPS.");
        assertTrue(kdTime/npsTime < 0.1);
    }

    @Test
    public void testRandomPoints() {
        testRandomNPointsMQueries(100000, 10000);
    }

    @Test
    public void testTimingPoints() {
        testTimingNPointsMQueries(100000, 10000);
        /*
        kdTime consume: 0.105.
        npsTime consume: 3.632.
        KD calculated 1708302 times.
        NPS calculated 1000000000 times.
        KD is 35 times faster than NPS.
        * */
    }
}
