import org.junit.Test;
import org.junit.Assert;

public class TestUnionFind {
    UnionFind unionFind = new UnionFind(8);

    @Test
    public void testSizeOf() {
        unionFind.union(2, 4);
        unionFind.union(1, 3);
        unionFind.union(3, 4);
        int expected = 4;
        int actual = unionFind.sizeOf(4);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testUnion() {
        unionFind.union(2, 4);
        unionFind.union(1, 3);
        unionFind.union(3, 4);
        unionFind.union(0, 6);
        unionFind.union(7, 5);
        unionFind.union(0, 7);
        unionFind.union(1, 6);;
    }
}
