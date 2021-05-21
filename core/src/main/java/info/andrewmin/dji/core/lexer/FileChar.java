package info.andrewmin.dji.core.lexer;

/**
 * A character and its location in a source file.
 *
 * @see FileLoc
 * @see FileCharIterator
 */
public final class FileChar {
    private final char c;
    private final FileLoc loc;

    /**
     * Construct a new file character.
     *
     * @param c   The character.
     * @param loc The location.
     */
    public FileChar(char c, FileLoc loc) {
        this.c = c;
        this.loc = loc;
    }

    /**
     * Construct a new file character.
     *
     * @param c      The character.
     * @param line   The location line number.
     * @param column The location column number.
     */
    public FileChar(char c, int line, int column) {
        this(c, new FileLoc(line, column));
    }

    /**
     * Construct a new file character without location data.
     *
     * @param c The character.
     */
    public FileChar(char c) {
        this(c, -1, -1);
    }

    /**
     * Get the character.
     *
     * @return The character.
     */
    public char getC() {
        return c;
    }

    /**
     * Get the location.
     *
     * @return The location
     */
    public FileLoc getLoc() {
        return loc;
    }

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
