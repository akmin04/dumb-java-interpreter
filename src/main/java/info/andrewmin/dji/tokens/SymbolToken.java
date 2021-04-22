package info.andrewmin.dji.tokens;

/**
 * A symbol token.
 *
 * @see SymbolTokenType
 */
public class SymbolToken extends Token {
    private final SymbolTokenType type;

    /**
     * Construct a SymbolToken.
     *
     * @param type the SymbolTokenType.
     */
    public SymbolToken(SymbolTokenType type) {
        super("Symbol");
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
