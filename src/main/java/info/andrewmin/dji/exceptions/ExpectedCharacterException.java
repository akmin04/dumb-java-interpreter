package info.andrewmin.dji.exceptions;

import info.andrewmin.dji.lexer.FileLoc;

/**
 * A user error related to a missing or expected character.
 * <p>
 * e.g. missing an ending double quote after a string literal.
 */
public class ExpectedCharacterException extends BaseUserException {
    public ExpectedCharacterException(String expected, FileLoc at) {
        super("Expected " + expected + " at " + at);
    }
}
