package info.andrewmin.dji.tokens;

/**
 * A base DJava token.
 */
public abstract class Token {
    private final String name;

    protected Token(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Token(" + name + ")";
    }
}
