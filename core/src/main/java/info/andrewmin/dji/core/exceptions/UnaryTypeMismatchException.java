package info.andrewmin.dji.core.exceptions;

import info.andrewmin.dji.core.tokens.SymbolTokenVariant;
import info.andrewmin.dji.core.tokens.TypeTokenVariant;

/**
 * A user error for an invalid type on a unary operator.
 */
public final class UnaryTypeMismatchException extends BaseUserException {
    /**
     * Construct a new unary type mismatch exception.
     *
     * @param op          The unary operator.
     * @param invalidType The invalid type.
     */
    public UnaryTypeMismatchException(SymbolTokenVariant op, TypeTokenVariant invalidType) {
        super("Cannot apply " + op.symbol + " to " + invalidType);
    }
}
