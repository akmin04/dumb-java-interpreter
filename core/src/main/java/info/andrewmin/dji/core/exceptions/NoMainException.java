package info.andrewmin.dji.core.exceptions;

/**
 * A user error for a program without a main function.
 */
public final class NoMainException extends BaseUserException {
    /**
     * Construct a new no main exception.
     */
    public NoMainException() {
        super("No main function found");
    }
}
