package info.andrewmin.dji;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Wraps an iterator and allows peeking.
 *
 * @param <T> the type of elements returned.
 */
public class Peekable<T> implements Iterator<T> {
    private final Iterator<T> iter;
    private T buffer;
    private T current;

    public Peekable(Iterator<T> iter) {
        this.iter = iter;
        this.buffer = iter.hasNext() ? iter.next() : null;
        this.current = null;
    }

    @Override
    public boolean hasNext() {
        return buffer != null;
    }

    @Override
    public T next() {
        if (!iter.hasNext() && buffer == null) {
            throw new NoSuchElementException();
        }
        T next = buffer;
        current = buffer;
        buffer = iter.hasNext() ? iter.next() : null;
        return next;
    }

    public T peek() {
        if (buffer == null) {
            throw new NoSuchElementException();
        }
        return buffer;
    }

    public T current() {
        return current;
    }
}
