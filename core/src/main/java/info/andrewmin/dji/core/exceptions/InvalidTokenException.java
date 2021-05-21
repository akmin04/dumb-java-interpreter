package info.andrewmin.dji.core.exceptions;

import info.andrewmin.dji.core.lexer.FileChar;

/**
 * A user error for an invalid token found during lexical analysis.
 * <p>
 * e.g. a stray tilde ~
 */
public final class InvalidTokenException extends BaseUserException {
    /**
     * Construct a new invalid token exception.
     *
     * @param firstChar The first character of the invalid token.
     */
    public InvalidTokenException(FileChar firstChar) {
        super("Invalid token " + firstChar);
    }
}
