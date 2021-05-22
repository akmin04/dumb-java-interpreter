package info.andrewmin.dji.core.exceptions;

/**
 * A user error for when an identifier (function or variable name) is declared more than once.
 */
public final class InvalidIdentifierException extends BaseUserException {
    /**
     * Construct a new invalid identifier exception.
     *
     * @param identifier The invalid identifier.
     */
    public InvalidIdentifierException(String identifier) {
        super(identifier + " already exists");
    }
}
