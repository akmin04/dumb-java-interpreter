package info.andrewmin.dji.exceptions;

import info.andrewmin.dji.lexer.FileChar;

/**
 * A user error for an invalid token found during lexical analysis.
 * <p>
 * e.g. a stray tilde ~
 */
public class InvalidTokenException extends BaseUserException {
    public InvalidTokenException(FileChar firstChar) {
        super("Invalid token " + firstChar);
    }
}
