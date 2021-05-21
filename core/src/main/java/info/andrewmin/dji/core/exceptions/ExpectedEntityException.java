package info.andrewmin.dji.core.exceptions;

import info.andrewmin.dji.core.lexer.FileLoc;

/**
 * A user error related to a missing or expected character/token/node.
 * <p>
 * e.g. missing an ending double quote after a string literal.
 */
public final class ExpectedEntityException extends BaseUserException {
    /**
     * Construct a new expected entity exception.
     *
     * @param expected The expected entity.
     * @param at       The expected location.
     */
    public ExpectedEntityException(String expected, FileLoc at) {
        super("Expected " + expected + " at " + at);
    }
}
