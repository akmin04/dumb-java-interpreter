package info.andrewmin.dji.core.exceptions;

/**
 * A base exception for errors that should be presented to the user.
 * <p>
 * e.g. Syntax errors, invalid tokens, invalid files.
 */
public abstract class BaseUserException extends RuntimeException {
    /**
     * Construct a new base user exception.
     *
     * @param message the exception message.
     */
    BaseUserException(String message) {
        super(message);
    }
}
