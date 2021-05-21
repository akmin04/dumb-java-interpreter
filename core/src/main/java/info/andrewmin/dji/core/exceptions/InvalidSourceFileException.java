package info.andrewmin.dji.core.exceptions;

/**
 * A user error when a source file is unable to be opened.
 */
public final class InvalidSourceFileException extends BaseUserException {
    /**
     * Construct a new invalid source file exception.
     *
     * @param fileName The file name.
     */
    public InvalidSourceFileException(String fileName) {
        super("Unable to open file " + fileName);
    }
}
