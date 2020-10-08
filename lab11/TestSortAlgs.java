import edu.princeton.cs.algs4.Queue;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue q = new Queue();
        q.enqueue(2);
        q.enqueue(5);
        q.enqueue(3);
        q.enqueue(1);
        q.enqueue(6);
        q.enqueue(4);
        q.enqueue(7);
        Queue sortedQ = QuickSort.quickSort(q);
        assertTrue(isSorted(sortedQ));
    }

    @Test
    public void testMergeSort() {
        Queue q = new Queue();
        q.enqueue(2);
        q.enqueue(5);
        q.enqueue(3);
        q.enqueue(1);
        q.enqueue(6);
        q.enqueue(4);
        q.enqueue(7);
        Queue sortedQ = MergeSort.mergeSort(q);
        assertTrue(isSorted(sortedQ));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
