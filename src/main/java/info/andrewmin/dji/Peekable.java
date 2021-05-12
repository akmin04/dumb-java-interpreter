package info.andrewmin.dji;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Peekable<T> implements Iterator<T> {
    private final Iterator<T> iter;
    private T buffer;

    public Peekable(Iterator<T> iter) {
        this.iter = iter;
        buffer = iter.hasNext() ? iter.next() : null;
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
        buffer = iter.hasNext() ? iter.next() : null;
        return next;
    }

    public T peek() {
        return buffer;
    }
}
