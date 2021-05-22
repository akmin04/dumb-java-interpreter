package info.andrewmin.dji.core.exceptions;

import info.andrewmin.dji.core.tokens.TypeTokenVariant;

/**
 * A user error for mismatched types.
 */
public final class TypeMismatchException extends BaseUserException {
    /**
     * Construct a new type mismatch exception.
     *
     * @param expected The expected type.
     * @param actual   The actual received type.
     */
    public TypeMismatchException(TypeTokenVariant expected, TypeTokenVariant actual) {
        super("Expected " + expected + ", but received " + actual);
    }
}
