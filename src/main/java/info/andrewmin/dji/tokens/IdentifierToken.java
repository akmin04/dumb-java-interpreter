package info.andrewmin.dji.tokens;

/**
 * An identifier token.
 * <p>
 * Variable names, function names.
 * Identifiers should always be first checked that they are not keywords.
 *
 * @see KeywordTokenType
 */
public class IdentifierToken extends Token {
    private final String identifier;

    /**
     * Construct an IdentifierToken
     *
     * @param identifier the identifier.
     */
    public IdentifierToken(String identifier) {
        super("Identifier");
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
