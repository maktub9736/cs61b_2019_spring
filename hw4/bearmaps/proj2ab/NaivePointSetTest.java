package bearmaps;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class NaivePointSetTest {

    @Test
    public void sanityTest() {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);
        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0);
        assertEquals(p2, ret);
    }
}
