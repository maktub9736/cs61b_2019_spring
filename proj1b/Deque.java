public interface Deque<T> {
    default boolean isEmpty() {
        return true;
    }
    T removeFirst();
    T removeLast();
    void addFirst(T item);
    void addLast(T item);
    int size();
    T get(int index);
    void printDeque();
}
