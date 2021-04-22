package info.andrewmin.dji.lexer;

import info.andrewmin.dji.Logger;

import java.io.*;
import java.util.Iterator;

/**
 * A peek-able iterator over a file split into FileChars.
 *
 * @see FileChar
 */
public class FileCharIterator implements Iterator<FileChar> {

    private BufferedReader reader;
    private FileChar buffer;

    private boolean isEOF = false;
    private int line = 1;
    private int column = 1;

    /**
     * Construct a FileCharIterator from a file name.
     *
     * @param fileName the file name.
     */
    public FileCharIterator(String fileName) {
        try {
            this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        } catch (FileNotFoundException e) {
            Logger.getDefault().error(this, "Unable to open " + fileName);
        }
        updateBuffer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext() {
        return !isEOF;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileChar next() {
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

    private void updateBuffer() {
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
            Logger.getDefault().error(this, e.toString());
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
