public class IntNode<T> {
    public T value;
    public IntNode prev;
    public IntNode next;

    public IntNode(T v, IntNode p, IntNode n) {
        this.value = v;
        this.prev = p;
        this.next = n;
    }

    public IntNode(IntNode<T> a) {
        this.value = a.value;
        this.prev = a.prev;
        this.next = a.next;
    }
}
