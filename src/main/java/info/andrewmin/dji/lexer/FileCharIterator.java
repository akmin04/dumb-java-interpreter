package info.andrewmin.dji.lexer;

import info.andrewmin.dji.exceptions.BaseUserException;
import info.andrewmin.dji.exceptions.FileReadException;
import info.andrewmin.dji.exceptions.InvalidSourceFileException;

import java.io.*;

/**
 * A peek-able iterator over a file split into FileChars.
 *
 * @see FileChar
 */
public class FileCharIterator {

    private final String fileName;
    private final BufferedReader reader;

    /**
     * Store iterator values in a buffer to allow peeking.
     */
    private FileChar buffer;

    private boolean isEOF = false;
    private int line = 1;
    private int column = 1;

    /**
     * Construct a FileCharIterator from a file name.
     *
     * @param fileName the file name.
     */
    public FileCharIterator(String fileName) throws BaseUserException {
        try {
            this.fileName = fileName;
            this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        } catch (FileNotFoundException e) {
            throw new InvalidSourceFileException(fileName);
        }
        updateBuffer();
    }

    /**
     * Whether or not more tokens exist.
     *
     * @return if the end of the file has been reached or not.
     */
    public boolean hasNext() {
        return !isEOF;
    }

    /**
     * Consume and return the next FileChar.
     *
     * @return the next FileChar.
     */
    public FileChar next() throws BaseUserException {
        // TODO less crappy copy lol
        FileChar oldBuffer = (buffer == null)
                ? null
                : new FileChar(buffer.getC(), buffer.getLoc().getLine(), buffer.getLoc().getColumn());
        updateBuffer();
        return oldBuffer;
    }

    /**
     * Peek at the next FileChar.
     *
     * @return a FileChar.
     */
    public FileChar peek() {
        return buffer;
    }

    /**
     * Read the next character in the file and update the buffer and line/column location.
     */
    private void updateBuffer() throws BaseUserException {
        if (isEOF) {
            buffer = null;
            return;
        }

        char c = 0;
        try {
            int i = reader.read();
            if (i == -1) {
                isEOF = true;
                buffer = null;
                return;
            }
            c = (char) i;
        } catch (IOException e) {
            throw new FileReadException(fileName, e);
        }

        buffer = new FileChar(c, line, column);

        if (c == '\n') {
            line++;
            column = 1;
        } else {
            column++;
        }
    }
}
