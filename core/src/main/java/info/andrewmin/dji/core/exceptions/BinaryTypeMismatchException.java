package info.andrewmin.dji.core.exceptions;

import info.andrewmin.dji.core.tokens.SymbolTokenVariant;
import info.andrewmin.dji.core.tokens.TypeTokenVariant;

/**
 * A user error for invalid types on a binary operator.
 */
public final class BinaryTypeMismatchException extends BaseUserException {
    /**
     * Construct a new binary type mismatch exception.
     *
     * @param op       The operator.
     * @param invalid1 The left type.
     * @param invalid2 The right type.
     */
    public BinaryTypeMismatchException(SymbolTokenVariant op, TypeTokenVariant invalid1, TypeTokenVariant invalid2) {
        super("Cannot apply " + op + " to " + invalid1 + " and " + invalid2);
    }
}
