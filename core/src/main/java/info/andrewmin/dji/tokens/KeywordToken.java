package info.andrewmin.dji.tokens;

import info.andrewmin.dji.exceptions.InternalCompilerException;
import info.andrewmin.dji.lexer.FileLoc;

/**
 * A keyword token.
 *
 * @see KeywordTokenVariant
 */
public final class KeywordToken extends Token {
    private final KeywordTokenVariant type;

    /**
     * Construct a KeywordToken.
     *
     * @param loc  the location of the first character of the token.
     * @param type the KeywordTokenType.
     */
    public KeywordToken(FileLoc startLoc, FileLoc endLoc, KeywordTokenVariant type) {
        super("Keyword", startLoc, endLoc);
        this.type = type;

        if (type == KeywordTokenVariant.FALSE || type == KeywordTokenVariant.TRUE) {
            throw new InternalCompilerException("Boolean literal tokens should be LiteralToken.Boolean, not KeywordToken");
        }
    }

    /**
     * Get the keyword type.
     *
     * @return a KeywordTokenType.
     */
    public KeywordTokenVariant getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
//    @Override
//    public String toString() {
//        return super.toString() + ": " + type;
//    }
    @Override
    public String rawString() {
        return type.keyword;
    }
}
