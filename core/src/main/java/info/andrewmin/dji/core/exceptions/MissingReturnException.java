package info.andrewmin.dji.core.exceptions;

/**
 * A user error for a missing return statement when the function is not void.
 */
public final class MissingReturnException extends BaseUserException {
    /**
     * Construct a new missing return exception;
     *
     * @param func The function name;
     */
    public MissingReturnException(String func) {
        super("Missing return statement in " + func);
    }
}
