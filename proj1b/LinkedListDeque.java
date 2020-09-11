import java.util.Objects;
import java.util.OptionalInt;

public class LinkedListDeque<T> {
    private int size;
    /* The first item (if it exists) is at sentinel.next. */
    private IntNode<T> sentinel;

    /** Generate an empty Deck. */
    public LinkedListDeque() {
        sentinel = new IntNode(null, null, null);
        size = 0;
    }

    /** Creates a deep copy of other. */
    public LinkedListDeque(LinkedListDeque<T> other) {
        sentinel = new IntNode(other.sentinel);
        size = 0;
        for (int i = 0; i < other.size(); i += 1) {
            T val = other.get(i);
            addLast(val);
        }
    }

    /**Adds an item of type T to the front of the deque. */
    public void addFirst(T v) {
        if (size == 0) {
            IntNode firstNode = new IntNode(v, sentinel, sentinel);
            sentinel.next = firstNode;
            sentinel.prev = firstNode;
        }
        else {
            /** Generate a new node. */
            IntNode newFirst = new IntNode(v, sentinel, sentinel.next);
            /* Don't forget to change the pointers of the last node. */

            /** Change the pointers of sentinel and the original first node. */
            IntNode oldFirst = sentinel.next;
            oldFirst.prev = newFirst;
            sentinel.next = newFirst;
        }
        size += 1;
    }

    /** Adds an item of type T to the back of the deque. */
    public void addLast(T v) {
        if (this.isEmpty()) {
            IntNode lastNode = new IntNode(v, sentinel, sentinel);
            sentinel.next = lastNode;
            sentinel.prev = lastNode;
        }
        else {
            /** Generate a new node. */
            IntNode newLast = new IntNode(v, sentinel.prev, sentinel);

            /** Change the existing pointers on sentinel and the original last node. */
            IntNode oldLast = sentinel.prev;
            oldLast.next = newLast;
            sentinel.prev = newLast;
        }
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty(){
        return size == 0;
    }

    /** Must take constant time. */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space.
     *  Once all the items have been printed, print out a new line. */
    public void printDeque() {
        IntNode pointer = sentinel.next;
        for (int i = 0; i < size; i += 1) {
            System.out.print(pointer.value + " ");
            pointer = pointer.next;
        }
        System.out.println("\n");
    }

    /** Removes and returns the item at the front of the deque.
     *  If no such item exists, returns null.*/
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        IntNode<T> firstNode = sentinel.next;
        IntNode<T> secondNode = firstNode.next;
        sentinel.next = secondNode;
        secondNode.prev = sentinel;
        size -= 1;
        return firstNode.value;
    }

    /** Removes and returns the item at the back of the deque.
     *  If no such item exists, returns null.*/
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        IntNode<T> lastNode = sentinel.prev;
        IntNode<T> secondLastNode = lastNode.prev;
        sentinel.prev = secondLastNode;
        secondLastNode.next = sentinel;
        size -= 1;
        return lastNode.value;
    }

    /** Gets the item at the given index,
     *  where 0 is the front, 1 is the next item, and so forth.
     *  If no such item exists, returns null.
     *  Must not alter the deque!*/
    public T get(int index) {
        if (size == 0) {
            return null;
        }
        IntNode<T> pointer = sentinel.next;
        for (int i = 0; i < index; i += 1) {
            pointer = pointer.next;
        }
        return pointer.value;
    }

    /** Same as get, but uses recursion. */
//    public T getRecursive(int index) {
//
//    }
    public static void main(String[] args) {
        LinkedListDeque lld1 = new LinkedListDeque();
        lld1.addFirst(15);
        lld1.addLast(10);
        lld1.addFirst(2);
        lld1.printDeque();
        LinkedListDeque lld2 = new LinkedListDeque(lld1);
        lld1.removeFirst();
        lld2.printDeque();

    }
}
