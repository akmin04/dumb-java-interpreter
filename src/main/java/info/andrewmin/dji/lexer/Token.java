package info.andrewmin.dji.lexer;

/**
 * An individual token output from the Lexer.
 *
 * @param <T> the value type (if any).
 * @see TokenType
 */
public class Token<T> {
    private final TokenType type;
    private final T value;

    /**
     * Construct a Token with no value.
     *
     * @param type the TokenType.
     */
    public Token(TokenType type) {
        this.type = type;
        this.value = null;
    }

    /**
     * Construct a Token with a value.
     *
     * @param type  the TokenType.
     * @param value the token's value (literal value, variable name, etc).
     */
    public Token(TokenType type, T value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Get type.
     *
     * @return the TokenType.
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Get value.
     *
     * @return the value.
     */
    public T getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Token(" + type + ")" + (value != null ? ": " + value : "");
    }
}