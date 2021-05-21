package info.andrewmin.dji.exceptions;

import info.andrewmin.dji.lexer.FileLoc;

/**
 * A user error related to a missing or expected character.
 * <p>
 * e.g. missing an ending double quote after a string literal.
 */
public class ExpectedEntityException extends BaseUserException {
    public ExpectedEntityException(String expected, FileLoc at) {
        super("Expected " + expected + " at " + at);
    }
}
