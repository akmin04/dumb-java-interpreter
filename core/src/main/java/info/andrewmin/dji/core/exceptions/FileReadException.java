package info.andrewmin.dji.core.exceptions;

import java.io.IOException;

/**
 * A user error related to IO errors when reading source files.
 */
public final class FileReadException extends BaseUserException {
    /**
     * Construct a new file read excpetion.
     *
     * @param fileName The file name.
     * @param e        The IO exception.
     */
    public FileReadException(String fileName, IOException e) {
        super("Encountered an IO error while reading " + fileName + "\nDetails: " + e.toString());
    }
}
