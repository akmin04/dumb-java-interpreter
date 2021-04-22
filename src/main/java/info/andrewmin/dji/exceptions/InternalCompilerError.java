package info.andrewmin.dji.exceptions;

/**
 * An unchecked internal compiler error.
 */
public class InternalCompilerError extends RuntimeException {
    public InternalCompilerError(String message) {
        super(message);
    }
}
