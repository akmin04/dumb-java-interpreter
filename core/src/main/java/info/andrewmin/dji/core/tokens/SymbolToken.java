package info.andrewmin.dji.core.tokens;

import info.andrewmin.dji.core.lexer.FileLoc;

/**
 * A symbol token.
 *
 * @see SymbolTokenVariant
 */
public final class SymbolToken extends Token {
    private final SymbolTokenVariant variant;

    /**
     * Construct a new symbol token.
     *
     * @param startLoc The starting location of the first character of the token (inclusive).
     * @param endLoc   The ending location of the first character of the token (exclusive).
     * @param variant  The variant.
     */
    public SymbolToken(FileLoc startLoc, FileLoc endLoc, SymbolTokenVariant variant) {
        super("Symbol", startLoc, endLoc);
        this.variant = variant;
    }

    /**
     * Get the symbol variant.
     *
     * @return The symbol variant.
     */
    public SymbolTokenVariant getVariant() {
        return variant;
    }

    @Override
    public String rawString() {
        return variant.symbol;
    }
}
