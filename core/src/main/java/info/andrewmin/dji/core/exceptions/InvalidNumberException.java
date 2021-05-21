package info.andrewmin.dji.core.exceptions;

import info.andrewmin.dji.core.lexer.FileChar;

/**
 * A user error related to an invalid number literal.
 * <p>
 * e.g. an integer literal too large.
 */
public final class InvalidNumberException extends BaseUserException {
    /**
     * Construct a new invalid number exception
     *
     * @param firstDigit The first digit in the number
     * @param rawLiteral The raw literal.
     */
    public InvalidNumberException(FileChar firstDigit, String rawLiteral) {
        super("Invalid number " + rawLiteral + " at " + firstDigit.getLoc());
    }
}
