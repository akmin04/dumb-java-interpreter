package info.andrewmin.dji.lexer;

import info.andrewmin.dji.exceptions.FileReadException;
import info.andrewmin.dji.exceptions.InvalidSourceFileException;

import java.io.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A peek-able iterator over a file split into FileChars.
 *
 * @see FileChar
 */
public final class FileCharIterator implements Iterator<FileChar> {

    private final String fileName;
    private final BufferedReader reader;

    /**
     * Store iterator values in a buffer to allow peeking.
     */
    private FileChar buffer;
    private FileChar current;

    private int line = 1;
    private int column = 1;

    /**
     * Construct a FileCharIterator from a file.
     *
     * @param file the source file.
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

    public FileCharIterator(String raw) {
        this.fileName = "RAW_INPUT";
        this.reader = new BufferedReader(new StringReader(raw));
        updateBuffer();
    }

    /**
     * Whether or not more tokens exist.
     *
     * @return if the end of the file has been reached or not.
     */
    @Override
    public boolean hasNext() {
        return buffer != null;
    }

    /**
     * Consume and return the next FileChar.
     *
     * @return the next FileChar.
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

    public FileChar peek() {
        if (buffer == null) {
            throw new NoSuchElementException();
        }
        return buffer;
    }

    public FileChar current() {
        return current;
    }

    /**
     * Read the next character in the file and update the buffer and line/column location.
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
