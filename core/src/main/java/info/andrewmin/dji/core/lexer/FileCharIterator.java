package info.andrewmin.dji.core.lexer;

import info.andrewmin.dji.core.exceptions.FileReadException;
import info.andrewmin.dji.core.exceptions.InvalidSourceFileException;

import java.io.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A peek-able iterator that reads a source file and splits it into [FileChar]s
 *
 * @see FileChar
 */
public final class FileCharIterator implements Iterator<FileChar> {
    private final String fileName;
    private final BufferedReader reader;

    private FileChar buffer;
    private FileChar current;

    private int line = 1;
    private int column = 1;

    /**
     * Construct a new file character iterator from a file.
     *
     * @param file The source file.
     */
    public FileCharIterator(File file) {
        this.fileName = file.getName();
        try {
            this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            throw new InvalidSourceFileException(this.fileName);
        }
        updateBuffer();
        this.current = null;
    }

    /**
     * Construct a new file character iterator from raw text for debugging.
     *
     * @param raw The raw source text.
     */
    public FileCharIterator(String raw) {
        this.fileName = "RAW_INPUT";
        this.reader = new BufferedReader(new StringReader(raw));
        updateBuffer();
    }

    /**
     * Check if more characters exist or not.
     *
     * @return If more characters exist.
     */
    @Override
    public boolean hasNext() {
        return buffer != null;
    }

    /**
     * Consume and return the next character.
     *
     * @return The next character.
     */
    @Override
    public FileChar next() {
        if (buffer == null) {
            throw new NoSuchElementException();
        }
        FileChar c = buffer;
        current = buffer;
        updateBuffer();
        return c;
    }

    /**
     * Get the next character without consuming it.
     *
     * @return The next character.
     */
    public FileChar peek() {
        if (buffer == null) {
            throw new NoSuchElementException();
        }
        return buffer;
    }

    /**
     * Get the current character (the most recently consumed).
     *
     * @return The current character.
     */
    public FileChar current() {
        return current;
    }

    /**
     * Read the next character in the file and update the buffer and location.
     */
    private void updateBuffer() {
        try {
            int i = reader.read();
            if (i == -1) {
                buffer = null;
                return;
            }

            char c = (char) i;
            buffer = new FileChar(c, line, column);

            if (c == '\n') {
                line++;
                column = 1;
            } else {
                column++;
            }
        } catch (IOException e) {
            throw new FileReadException(fileName, e);
        }
    }
}
