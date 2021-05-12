package info.andrewmin.dji.tokens;

import info.andrewmin.dji.lexer.FileLoc;

/**
 * An identifier token.
 * <p>
 * Variable names, function names.
 * Identifiers should always be first checked that they are not keywords.
 *
 * @see KeywordTokenType
 */
public final class IdentifierToken extends Token {
    private final String identifier;

    /**
     * Construct an IdentifierToken
     *
     * @param identifier the identifier.
     */
    public IdentifierToken(FileLoc loc, String identifier) {
        super("Identifier", loc);
        this.identifier = identifier;
    }

    /**
     * Get the identifier.
     *
     * @return the identifier.
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return super.toString() + ": " + identifier;
    }
}
