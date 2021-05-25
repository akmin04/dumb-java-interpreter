package info.andrewmin.dji.core.tokens;

import info.andrewmin.dji.core.lexer.FileLoc;

/**
 * A type token.
 *
 * @see TypeTokenVariant
 */
public final class TypeToken extends Token {
    private final TypeTokenVariant variant;

    /**
     * Construct a new variant token.
     *
     * @param startLoc The starting location of the first character of the token (inclusive).
     * @param endLoc   The ending location of the first character of the token (exclusive).
     * @param variant  The variant.
     */
    public TypeToken(FileLoc startLoc, FileLoc endLoc, TypeTokenVariant variant) {
        super("Type", startLoc, endLoc);
        this.variant = variant;
    }

    /**
     * Get the type variant.
     *
     * @return The type variant.
     */
    public TypeTokenVariant getVariant() {
        return variant;
    }

    @Override
    public String rawString() {
        return variant.type;
    }
}
