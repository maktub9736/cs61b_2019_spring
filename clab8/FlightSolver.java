import java.util.ArrayList;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are >= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {
    public ArrayList<Node> heap;

    /** Heap: [[time, num] ...] */
    public FlightSolver(ArrayList<Flight> flights) {  // Input array -> a heap of flights
        heap = new ArrayList<Node>();
        for (int i = 0; i < flights.size(); i++) {
            Flight f = flights.get(i);
            Node enterNode = new Node(f.startTime, f.passengers);
            Node leaveNode = new Node(f.endTime, -f.passengers);
            addNode(enterNode);
            addNode(leaveNode);
        }
    }

    public int solve() {
        int max = 0;
        int passenger = 0;
        int i = 0;
        while (heap.size() > 0) {
            Node n = removeFirst();
            passenger += n.passengers;
            if (passenger > max) {
                max = passenger;
            }
        }
        return max;
    }

    private void addNode(Node x) {
        int place = heap.size();
        heap.add(place, x);
        swim(place);
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

    private void swap(int p1, int p2) {
        Node temp = heap.get(p1);
        heap.set(p1, heap.get(p2));
        heap.set(p2, temp);
    }

    private int parent(int k) {
        return (k - 1) / 2;
    }

    private Node removeFirst() {
        Node first = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        check(0);
        return first;
    }

    private void check(int k) {
        int leftChild = 2 * k + 1;
        int rightChild = 2 * k + 2;
        if (leftChild >= heap.size()) {       // k has no child
            return;
        } else if (rightChild >= heap.size() && prior(leftChild, k)) {      // k has one child
            swap(leftChild, k);
            check(leftChild);
        } else if (rightChild < heap.size()) {
            if (prior(leftChild, rightChild) && prior(leftChild, k)) {
                swap(leftChild, k);
                check(leftChild);
            } else if (prior(rightChild, leftChild) && prior(rightChild, k)){
                swap(rightChild, k);
                check(rightChild);
            }
        }
    }

    private boolean prior(int p1, int p2) {
        if (heap.get(p1).time < heap.get(p2).time
                || (heap.get(p1).time == heap.get(p2).time && heap.get(p2).passengers < 0)) {
            return true;
        }
        return false;
    }

    private class Node {
        int time;
        int passengers;

        public Node(int t, int p) {
            time = t;
            passengers = p;
        }
    }

    public static void main(String[] args) {
        ArrayList a = new ArrayList();
        a.add(0, 3);
    }
}
