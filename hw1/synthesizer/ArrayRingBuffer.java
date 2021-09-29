// TODO: Make sure to make this class a part of the synthesizer package
package synthesizer;
import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    public int capacity() {
        return capacity;
    }

    public int fillCount() {
        return fillCount;
    }

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        this.capacity = capacity;
        this.fillCount = 0;
        this.first = 0;
        this.last = 0;
        this.rb = (T[]) new Object[this.capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if (fillCount == capacity) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        last += 1;
        if (last > capacity - 1) {
            last = 0;
        }
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
        if (fillCount == 0) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T toReturn = rb[first];
        rb[first] = null;
        first += 1;
        if (first > capacity - 1) {
            first = 0;
        }
        fillCount -= 1;
        return toReturn;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        return rb[first];
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.

    public Iterator<T> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<T> {
        private int pointerPosition;
        private int count;

        MyIterator() {
            pointerPosition = first;
            count = 0;
        }

        public boolean hasNext() {
            return count != fillCount;
        }

        public T next() {
            T toReturn = rb[pointerPosition];
            pointerPosition += 1;
            if (pointerPosition > capacity - 1) {
                pointerPosition = 0;
            }
            count += 1;
            return toReturn;
        }
    }
}
