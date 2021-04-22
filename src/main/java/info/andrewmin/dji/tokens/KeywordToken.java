package info.andrewmin.dji.tokens;

import info.andrewmin.dji.Logger;

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
        super("Keyword");
        this.type = type;

        if (type == KeywordTokenType.FALSE || type == KeywordTokenType.TRUE) {
            Logger.getDefault().fatal(this, "Boolean literal tokens should be LiteralToken.Boolean, not KeywordToken");
        }
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
