package info.andrewmin.dji.tokens;

import info.andrewmin.dji.exceptions.InternalCompilerError;

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
            throw new InternalCompilerError("Boolean literal tokens should be LiteralToken.Boolean, not KeywordToken");
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
