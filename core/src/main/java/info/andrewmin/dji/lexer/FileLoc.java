package info.andrewmin.dji.lexer;

/**
 * The line and column location of a character or token in a source file.
 *
 * @see FileChar
 */
public final class FileLoc {
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

    public FileLoc addCol(int c) {
        return new FileLoc(line, column + c);
    }

    public FileLoc addLine(int l) {
        return new FileLoc(line + l, column);
    }

    public FileLoc add(FileLoc loc) {
        return new FileLoc(line + loc.line, column + loc.column);
    }

    public FileLoc sub(FileLoc loc) {
        return new FileLoc(line - loc.line, column - loc.column);
    }

    /**
     * {@inheritDoc}
     */
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
