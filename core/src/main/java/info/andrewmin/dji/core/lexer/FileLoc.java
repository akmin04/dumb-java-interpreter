package info.andrewmin.dji.core.lexer;

/**
 * A line and column location of a character or token in a source file.
 *
 * @see FileChar
 */
public final class FileLoc {
    private final int line;
    private final int column;

    /**
     * Construct a new file location.
     *
     * @param line   The file line number.
     * @param column The file column number.
     */
    public FileLoc(int line, int column) {
        this.line = line;
        this.column = column;
    }

    /**
     * Get the line number.
     *
     * @return The line number.
     */
    public int getLine() {
        return line;
    }

    /**
     * Get the column number.
     *
     * @return The column number.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Return a new file location with the added column.
     *
     * @param c The column value to add.
     * @return The new file location.
     */
    public FileLoc addCol(int c) {
        return new FileLoc(line, column + c);
    }

    /**
     * Return a new file location with the added line.
     *
     * @param l The line value to add.
     * @return The new file location.
     */
    public FileLoc addLine(int l) {
        return new FileLoc(line + l, column);
    }

    /**
     * Return a new file location with the sum of lines and columns.
     *
     * @param loc The other file location to add.
     * @return The new file location.
     */
    public FileLoc add(FileLoc loc) {
        return new FileLoc(line + loc.line, column + loc.column);
    }

    /**
     * Return a new file location with the difference of lines and columns.
     *
     * @param loc The other file location to subtract.
     * @return The new file location.
     */
    public FileLoc sub(FileLoc loc) {
        return new FileLoc(line - loc.line, column - loc.column);
    }

    @Override
    public String toString() {
        return "line " + line + ", column " + column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileLoc fileLoc = (FileLoc) o;
        return line == fileLoc.line && column == fileLoc.column;
    }

}
