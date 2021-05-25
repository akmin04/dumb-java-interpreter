package info.andrewmin.dji.core.tokens;

import info.andrewmin.dji.core.lexer.FileLoc;

/**
 * An identifier token.
 * <p>
 * Variable names, function names.
 * Identifiers should always be first checked that they are not keywords.
 *
 * @see KeywordTokenVariant
 */
public final class IdentifierToken extends Token {
    private final String identifier;

    /**
     * Construct a new identifier token.
     *
     * @param startLoc   The starting location of the first character of the token (inclusive).
     * @param endLoc     The ending location of the first character of the token (exclusive).
     * @param identifier The identifier.
     */
    public IdentifierToken(FileLoc startLoc, FileLoc endLoc, String identifier) {
        super("Identifier", startLoc, endLoc);
        this.identifier = identifier;
    }

    /**
     * Get the identifier.
     *
     * @return The identifier.
     */
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String rawString() {
        return identifier;
    }
}
