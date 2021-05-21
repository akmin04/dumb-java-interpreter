package info.andrewmin.dji.core.exceptions;

/**
 * An unchecked internal interpreter error.
 */
public final class InternalException extends RuntimeException {
    /**
     * Construct a new internal exception.
     *
     * @param message The exception message.
     */
    public InternalException(String message) {
        super(message);
    }
}
