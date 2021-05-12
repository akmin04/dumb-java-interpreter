package info.andrewmin.dji.tokens;

import info.andrewmin.dji.lexer.FileLoc;

/**
 * A symbol token.
 *
 * @see SymbolTokenType
 */
public final class SymbolToken extends Token {
    private final SymbolTokenType type;

    /**
     * Construct a SymbolToken.
     *
     * @param type the SymbolTokenType.
     */
    public SymbolToken(FileLoc loc, SymbolTokenType type) {
        super("Symbol", loc);
        this.type = type;
    }

    /**
     * Get the symbol type.
     *
     * @return a SymbolTokenType.
     */
    public SymbolTokenType getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return super.toString() + ": " + type.name() + " " + type.symbol;
    }
}
