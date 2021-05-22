package info.andrewmin.dji.core.exceptions;

/**
 * A user error for when a non-variable expression is found the the left hand side of an assignment.
 */
public final class NonVariableAssignException extends BaseUserException {
    /**
     * Construct a new non variable assign exception
     */
    public NonVariableAssignException() {
        super("Cannot assign to a non-variable");
    }
}
