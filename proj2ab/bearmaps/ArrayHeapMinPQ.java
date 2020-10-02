package bearmaps;
import java.util.*;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<PriorityNode> heap;
    private HashMap items;

    public ArrayHeapMinPQ() {
        heap = new ArrayList();
        items = new HashMap();
    }

    public void add(T item, double priority) {
        items.put(item, heap.size());
        int k = heap.size();
        heap.add(new PriorityNode(item, priority));
        swim(k);
    }

    private int parent(int k) {
        return (k - 1) / 2;
    }

    private void swap(int p1, int p2) {
        PriorityNode n1 = heap.get(p1);
        PriorityNode n2 = heap.get(p2);
        items.replace(n1.item, p2);
        items.replace(n2.item, p1);
        heap.set(p1, n2);
        heap.set(p2, n1);
    }

    private void swim(int k) {
        if (k == 0) {
            return;
        }
        if (prior(k, parent(k))) {
            swap(k, parent(k));
            swim(parent(k));
        }
    }

    private void dive(int k) {
        int leftChild = 2 * k + 1;
        int rightChild = 2 * k + 2;
        if (leftChild >= heap.size()) {       // k has no child
            return;
        } else if (rightChild >= heap.size() && prior(leftChild, k)) {      // k has one child
            swap(leftChild, k);
            dive(leftChild);
        } else if (rightChild < heap.size()) {
            if (prior(leftChild, rightChild) && prior(leftChild, k)) {
                swap(leftChild, k);
                dive(leftChild);
            } else if (prior(rightChild, leftChild) && prior(rightChild, k)) {
                swap(rightChild, k);
                dive(rightChild);
            }
        }
    }

    private boolean prior(int p1, int p2) {
       if (heap.get(p1).priority <= heap.get(p2).priority) {
           return true;
       }
       return false;
    }

    @Override
    public boolean contains(T item) {
        return items.containsKey(item);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public T getSmallest() {
        if (heap.size() == 0) {
            throw new NoSuchElementException();
        } else {
            return heap.get(0).item;
        }
    }

    @Override
    public T removeSmallest() {
        if (heap.size() == 0) {
            throw new NoSuchElementException();
        } else {
            PriorityNode smallest = heap.get(0);
            int last = heap.size() - 1;
            items.remove(smallest.item);
            heap.set(0, heap.get(last));
            heap.remove(last);
            dive(0);
            return smallest.item;
        }
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!items.containsKey(item)) {
            throw new NoSuchElementException();
        }
        int index = (int) items.get(item);
        items.remove(item);
        swap(index, heap.size() - 1);
        heap.remove(size() - 1);
        dive(index);
        add(item, priority);
    }

    private class PriorityNode {
        private double priority;
        private T item;

        public PriorityNode(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }
    }
}
