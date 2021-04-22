package info.andrewmin.dji.exceptions;

import java.io.IOException;

/**
 * A user error related to IO errors when reading source files.
 */
public class FileReadException extends BaseUserException {
    public FileReadException(String fileName, IOException e) {
        super("Encountered an IO error while reading " + fileName + "\nDetails: " + e.toString());
    }
}
