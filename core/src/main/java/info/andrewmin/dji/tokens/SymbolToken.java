package info.andrewmin.dji.tokens;

import info.andrewmin.dji.lexer.FileLoc;

/**
 * A symbol token.
 *
 * @see SymbolTokenVariant
 */
public final class SymbolToken extends Token {
    private final SymbolTokenVariant type;

    /**
     * Construct a SymbolToken.
     *
     * @param loc  the location of the first character of the token.
     * @param type the SymbolTokenType.
     */
    public SymbolToken(FileLoc startLoc, FileLoc endLoc, SymbolTokenVariant type) {
        super("Symbol", startLoc, endLoc);
        this.type = type;
    }

    /**
     * Get the symbol type.
     *
     * @return a SymbolTokenType.
     */
    public SymbolTokenVariant getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
//    @Override
//    public String toString() {
//        return super.toString() + ": " + type.name() + " " + type.symbol;
//    }
    @Override
    public String rawString() {
        return type.symbol;
    }
}
