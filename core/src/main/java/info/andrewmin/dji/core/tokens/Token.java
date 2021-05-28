package info.andrewmin.dji.core.tokens;

import info.andrewmin.dji.core.lexer.FileLoc;

/**
 * A base token.
 */
public abstract class Token {
    private final String name;
    private final FileLoc startLoc;
    private final FileLoc endLoc;

    /**
     * Construct a new token.
     *
     * @param name     The token name.
     * @param startLoc The starting location.
     * @param endLoc   The ending location.
     */
    protected Token(String name, FileLoc startLoc, FileLoc endLoc) {
        this.name = name;
        this.startLoc = startLoc;
        this.endLoc = endLoc;
    }

    /**
     * Get the starting location.
     *
     * @return The starting location.
     */
    public FileLoc getStartLoc() {
        return startLoc;
    }

    /**
     * Get the ending location.
     *
     * @return The ending location.
     */
    public FileLoc getEndLoc() {
        return endLoc;
    }

    /**
     * Get the length of the token.
     *
     * @return The length of the token.
     */
    public int length() {
        return rawString().length();
    }

    /**
     * Check if the token is a specific keyword variant.
     *
     * @param variant The specific keyword variant.
     * @return If the token is a specific keyword variant.
     */
    public boolean isKeyword(KeywordTokenVariant variant) {
        if (!(this instanceof KeywordToken)) {
            return false;
        }
        return ((KeywordToken) this).getVariant() == variant;
    }

    /**
     * Check if the token is a specific symbol variant.
     *
     * @param variant The specific symbol variant.
     * @return If the token is a specific symbol variant.
     */
    public boolean isSymbol(SymbolTokenVariant variant) {
        if (!(this instanceof SymbolToken)) {
            return false;
        }
        return ((SymbolToken) this).getVariant() == variant;
    }

    @Override
    public String toString() {
        return name + ": " + rawString();
    }

    /**
     * The raw token string as in the source file.
     *
     * @return The raw token string.
     */
    abstract public String rawString();
}
