package info.andrewmin.dji.core.exceptions;

/**
 * A user error for an invalid control flow statement outside of a loop body.
 */
public final class InvalidFlowStatementException extends BaseUserException {
    /**
     * Construct a new invalid flow statement exception.
     *
     * @param statement The invalid control flow statement.
     */
    public InvalidFlowStatementException(String statement) {
        super("Invalid " + statement + " outside loop");
    }
}
