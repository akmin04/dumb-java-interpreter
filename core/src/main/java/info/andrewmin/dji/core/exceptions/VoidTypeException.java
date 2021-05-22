package info.andrewmin.dji.core.exceptions;

/**
 * A user error for when void is used as a variable type.
 */
public final class VoidTypeException extends BaseUserException {
    /**
     * Construct a new void type exception.
     *
     * @param var The variable name.
     */
    public VoidTypeException(String var) {
        super("Variable " + var + " cannot have a void type");
    }
}
