package info.andrewmin.dji.core.tokens;

import info.andrewmin.dji.core.exceptions.InternalException;
import info.andrewmin.dji.core.lexer.FileLoc;

/**
 * A keyword token.
 *
 * @see KeywordTokenVariant
 */
public final class KeywordToken extends Token {
    private final KeywordTokenVariant variant;

    /**
     * Construct a new keyword token.
     *
     * @param startLoc The starting location of the first character of the token (inclusive).
     * @param endLoc   The ending location of the first character of the token (exclusive).
     * @param variant  The token variant.
     */
    public KeywordToken(FileLoc startLoc, FileLoc endLoc, KeywordTokenVariant variant) {
        super("Keyword", startLoc, endLoc);
        this.variant = variant;

        if (variant == KeywordTokenVariant.FALSE || variant == KeywordTokenVariant.TRUE) {
            throw new InternalException("Boolean literal tokens should be LiteralToken.Boolean, not KeywordToken");
        }
    }

    /**
     * Get the keyword variant.
     *
     * @return The keyword variant.
     */
    public KeywordTokenVariant getVariant() {
        return variant;
    }

    @Override
    public String rawString() {
        return variant.keyword;
    }
}
