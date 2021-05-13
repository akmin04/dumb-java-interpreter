package info.andrewmin.dji.lexer;

/**
 * A character and its location in the source file.
 *
 * @see FileLoc
 * @see FileCharIterator
 */
public final class FileChar {
    private final char c;
    private final FileLoc loc;

    /**
     * Construct a FileChar.
     *
     * @param c   the character.
     * @param loc the location.
     */
    public FileChar(char c, FileLoc loc) {
        this.c = c;
        this.loc = loc;
    }

    /**
     * Construct a FileChar
     *
     * @param c      the character.
     * @param line   the line number.
     * @param column the column number.
     */
    public FileChar(char c, int line, int column) {
        this(c, new FileLoc(line, column));
    }

    /**
     * Construct a FileChar without location data.
     *
     * @param c the character.
     */
    public FileChar(char c) {
        this(c, -1, -1);
    }

    /**
     * Get the character.
     *
     * @return c
     */
    public char getC() {
        return c;
    }

    /**
     * Get the location.
     *
     * @return loc
     */
    public FileLoc getLoc() {
        return loc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        String escaped;
        switch (c) {
            case '\n':
                escaped = "\\n";
                break;
            case '\t':
                escaped = "\\t";
                break;
            default:
                escaped = Character.toString(c);
        }
        return escaped + " at " + loc;
    }
}
