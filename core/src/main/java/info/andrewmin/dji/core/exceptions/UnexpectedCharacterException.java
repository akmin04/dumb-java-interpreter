package info.andrewmin.dji.core.exceptions;

import info.andrewmin.dji.core.lexer.FileLoc;
import info.andrewmin.dji.core.tokens.Token;

/**
 * A user error related to an unexpected character.
 */
public final class UnexpectedCharacterException extends BaseUserException {

    /**
     * Construct a general unexpected character exception.
     *
     * @param unexpected The unexpected string.
     * @param loc        The location.
     */
    public UnexpectedCharacterException(String unexpected, FileLoc loc) {
        super("Unexpected " + unexpected + " at " + loc);
    }

    /**
     * Construct a unexpected character exception from a token.
     *
     * @param t The unexpected token.
     */
    public UnexpectedCharacterException(Token t) {
        this(t.rawString(), t.getStartLoc());
    }
}
