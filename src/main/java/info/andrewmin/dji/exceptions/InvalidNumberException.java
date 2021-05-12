package info.andrewmin.dji.exceptions;

import info.andrewmin.dji.lexer.FileChar;

public class InvalidNumberException extends BaseUserException {
    public InvalidNumberException(FileChar firstDigit, String rawLiteral) {
        super("Invalid number " + rawLiteral + " at " + firstDigit.getLoc());
    }

}
