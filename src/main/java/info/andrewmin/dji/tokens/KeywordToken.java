package info.andrewmin.dji.tokens;

/**
 * A keyword token.
 *
 * @see KeywordTokenType
 */
public class KeywordToken extends Token {
    private final KeywordTokenType type;

    /**
     * Construct a KeywordToken.
     *
     * @param type the KeywordTokenType.
     */
    public KeywordToken(KeywordTokenType type) {
        super(type.name());
        this.type = type;
    }

    /**
     * Get the keyword type.
     *
     * @return a KeywordTokenType.
     */
    public KeywordTokenType getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return super.toString() + ": " + type;
    }
}
