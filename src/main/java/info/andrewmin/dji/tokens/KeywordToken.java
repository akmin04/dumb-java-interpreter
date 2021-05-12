package info.andrewmin.dji.tokens;

import info.andrewmin.dji.exceptions.InternalCompilerException;
import info.andrewmin.dji.lexer.FileLoc;

/**
 * A keyword token.
 *
 * @see KeywordTokenType
 */
public final class KeywordToken extends Token {
    private final KeywordTokenType type;

    /**
     * Construct a KeywordToken.
     *
     * @param type the KeywordTokenType.
     */
    public KeywordToken(FileLoc loc, KeywordTokenType type) {
        super("Keyword", loc);
        this.type = type;

        if (type == KeywordTokenType.FALSE || type == KeywordTokenType.TRUE) {
            throw new InternalCompilerException("Boolean literal tokens should be LiteralToken.Boolean, not KeywordToken");
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
