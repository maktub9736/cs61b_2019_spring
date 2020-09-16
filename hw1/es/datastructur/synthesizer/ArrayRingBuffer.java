package es.datastructur.synthesizer;
import java.util.Iterator;

//DONE: Make sure to that this class and all of its methods are public
//DONE: Make sure to add the override tag for all overridden methods
//DONE: Make sure to make this class implement BoundedQueue<T>

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // DONE: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    public void enqueue(T x) {
        // DONE: Enqueue the item. Don't forget to increase fillCount and update
        //       last.
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        last = addOne(last);
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T dequeue() {
        // DONE: Dequeue the first item. Don't forget to decrease fillCount and
        //       update first.
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        } else {
            T popItem = rb[first];
            rb[first] = null;
            first = addOne(first);
            fillCount -= 1;
            return popItem;
        }
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T peek() {
        // DONE: Return the first item. None of your instance variables should
        //       change.
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        } else {
            return rb[first];
        }
    }

    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    public int addOne(int index) {
        return (index + 1) % capacity();
    }


    // DONE: When you get to part 4, implement the needed code to support
    //       iteration and equals.

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }
    private class MyIterator implements Iterator<T> {
        int actIndex;
        int visitTwice;

        MyIterator() {
            this.actIndex = 0;
            this.visitTwice = 0;
        }

        @Override
        public boolean hasNext() {
            if (actIndex < capacity() - 1) {
                return true;
            } else if (actIndex == capacity() - 1 && visitTwice < 1) {
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            if (this.hasNext()) {
                if (visitTwice < 1) {
                    visitTwice = 1;
                    return rb[actIndex];
                } else {
                    visitTwice = 0;
                    return rb[actIndex++];
                }
            }
            throw new RuntimeException("Ring buffer underflow");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() != this.getClass()) {
            return false;
        }

        ArrayRingBuffer<T> other = (ArrayRingBuffer<T>) o;

        if (other.capacity() != this.capacity()
            || other.fillCount() != this.fillCount()) {
            return false;
        }

        if (other.peek() != this.peek()) {
            return false;
        }

        while (other.iterator().hasNext() && this.iterator().hasNext()) {
            if (other.iterator().next() != this.iterator().next()) {
                return false;
            }
        }
        return true;
    }
}

