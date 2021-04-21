package info.andrewmin.dji.lexer;

/**
 * The line and column location of a character or token in a source file.
 *
 * @see FileChar
 */
public class FileLoc {
    private final int line;
    private final int column;

    /**
     * Construct a FileLoc.
     *
     * @param line   the file line number.
     * @param column the file column number.
     */
    public FileLoc(int line, int column) {
        this.line = line;
        this.column = column;
    }

    /**
     * Get the line number.
     *
     * @return line number.
     */
    public int getLine() {
        return line;
    }

    /**
     * Get the column number.
     *
     * @return column number.
     */
    public int getColumn() {
        return column;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Line " + line + ", Column " + column;
    }
}
