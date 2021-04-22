package info.andrewmin.dji.exceptions;

/**
 * A user error when a source file is unable to be opened.
 */
public class InvalidSourceFileException extends BaseUserException {
    public InvalidSourceFileException(String fileName) {
        super("Unable to open file " + fileName);
    }
}
