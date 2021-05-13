package info.andrewmin.dji.exceptions;

/**
 * An unchecked internal compiler error.
 */
public class InternalCompilerException extends RuntimeException {
    public InternalCompilerException(String message) {
        super(message);
    }
}
