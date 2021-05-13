package info.andrewmin.dji.exceptions;

/**
 * The base excpetion for djava errors that should be presented to the user.
 * <p>
 * e.g. Syntax errors, invalid tokens, invalid files.
 */
public abstract class BaseUserException extends RuntimeException {
    protected BaseUserException(String message) {
        super(message);
    }
}
