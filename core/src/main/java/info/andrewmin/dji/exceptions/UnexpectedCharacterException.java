package info.andrewmin.dji.exceptions;

import info.andrewmin.dji.lexer.FileLoc;
import info.andrewmin.dji.tokens.Token;

/**
 * A user error related to an unexpected character.
 */
public class UnexpectedCharacterException extends BaseUserException {

    public UnexpectedCharacterException(String unexpected, FileLoc loc) {
        super("Unexpected " + unexpected + " at " + loc);
    }

    public UnexpectedCharacterException(Token t) {
        this(t.rawString(), t.getStartLoc());
    }
}
