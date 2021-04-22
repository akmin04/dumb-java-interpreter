package info.andrewmin.dji.tokens;

public class IdentifierToken extends Token {
    private final String identifier;

    public IdentifierToken(String identifier) {
        super("Identifier");
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return super.toString() + ": " + identifier;
    }
}
