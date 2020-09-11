public class ArrayDeque<T> implements Deque<T> {
    private int size;
    private T[] items;
    private Pos nextFirst;    /** Could use modulus %. */
    private Pos nextLast;

    private class Pos {
        public int position;

        public Pos(int x) {
            position = x;
        }

        public void moveRight(){
            if (position < items.length - 1) {
                position += 1;
                return;
            }
            position = 0;
            return;
        }

        public void moveLeft() {
            if (position > 0) {
                position -= 1;
                return;
            }
            position = items.length - 1;
            return;
        }
    }

    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[8];
        nextLast = new Pos(0);
        nextFirst = new Pos(items.length - 1);
    }

    public ArrayDeque(ArrayDeque other) {
        size = other.size;
        items = (T[]) new Object[other.items.length];
        System.arraycopy(items, 0, other, 0, other.items.length);
    }

    private void resize(int capacity) {
        T[] temp = (T[]) new Object[capacity];

        if (nextFirst.position <= items.length - 2) {
      /* First, copy from the first item to the end of the array.
      i.e. (length - 1 - nF) pieces. */
            if (size <= items.length - 1 - nextFirst.position) {
                System.arraycopy(items,nextFirst.position + 1, temp, 0, size);
            }
            else {
                System.arraycopy(items, nextFirst.position + 1, temp, 0, items.length - 1 - nextFirst.position);
                System.arraycopy(items, 0, temp, items.length - 1 - nextFirst.position, nextLast.position);
            }
        }

        else {
            System.arraycopy(items, 0, temp, 0, nextLast.position);
        }
        items = temp;
        nextLast.position = size;
        nextFirst.position = capacity - 1;
    }

    @Override
    public void addFirst(T item) {
        if (size >= items.length - 1) {
            resize(size * 3);
        }
        items[nextFirst.position] = item;
        size += 1;
        nextFirst.moveLeft();
    }

    @Override
    public void addLast(T item) {
        if (size >= items.length - 1) {
            resize(size * 3);
        }
        items[nextLast.position] = item;
        size +=1;
        nextLast.moveRight();
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        nextFirst.moveRight();
        size -= 1;
        T firstItem = items[nextFirst.position];
        /** Empty the first spot to save memory space. */
        items[nextFirst.position] = null;

        if (items.length >= 16 && size < items.length / 4) {
            resize(size * 3);
        }
        return firstItem;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }

        nextLast.moveLeft();
        size -= 1;
        T lastItem = items[nextLast.position];
        /** Empty the last spot to save memory space. */
        items[nextLast.position] = null;

        if (items.length >= 16 && size < items.length / 4) {
            resize(size * 3);
        }
        return lastItem;
    }

    @Override
    public T get(int index) {
         /* Using array index, i < items.length
        * i.e. [- - 2 5 3 1 -], the 3rd item is 5. */

        if (nextFirst.position <= nextLast.position) {
            if ((0 <= index && index < nextFirst.position)
                    || (nextLast.position < index && index < items.length)) {
                return items[index];
            }
        }
        else if (index > nextLast.position && index < nextFirst.position) {
            return items[index];
        }
        return null;

        /* Assuming index i means the i-th item, i <= size.
        * i.e. [- - - - 4 5 3 1 - -], the 2nd item is 5.
        *
        if (index <= 0 || index > size) {
            return null;
        }
        if (nextFirst.position + index < items.length) {
            return items[nextFirst.position + index];
        }
        return = items[index - items.length + nextFirst.position];
        */
    }

    @Override
    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = 0; i< size() ; i++) {
            int index = (i + nextFirst.position) % items.length;
            System.out.print(items[i] + " ");
        }
    }
}
