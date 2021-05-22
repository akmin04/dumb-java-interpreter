package info.andrewmin.dji.core.exceptions;

import info.andrewmin.dji.core.tokens.SymbolTokenVariant;

/**
 * A user error for when a variable is not found on the left hand side of an assignment expression.
 */
public final class ExpectedVariableException extends BaseUserException {
    /**
     * Construct a new expected variable exception.
     *
     * @param op The binary operator.
     */
    public ExpectedVariableException(SymbolTokenVariant op) {
        super("Excepted a variable before " + op.symbol);
    }
}
