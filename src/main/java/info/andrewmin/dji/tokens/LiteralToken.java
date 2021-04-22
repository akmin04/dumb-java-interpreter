package info.andrewmin.dji.tokens;

/**
 * A token with a literal value.
 *
 * @param <T> the value type.
 */
public abstract class LiteralToken<T> extends Token {
    private final T value;

    private LiteralToken(java.lang.String name, T value) {
        super(name + "Literal");
        this.value = value;
    }

    /**
     * Get the token's value.
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
    public java.lang.String toString() {
        return super.toString() + ": " + value;
    }

    /**
     * A boolean literal.
     */
    public static class Boolean extends LiteralToken<java.lang.Boolean> {
        public Boolean(boolean value) {
            super("Boolean", value);
        }
    }

    /**
     * An integer literal.
     */
    public static class Int extends LiteralToken<Integer> {
        public Int(int value) {
            super("Int", value);
        }
    }

    /**
     * A double literal.
     */
    public static class Double extends LiteralToken<java.lang.Double> {
        public Double(double value) {
            super("Double", value);
        }
    }

    /**
     * A character literal.
     */
    public static class Char extends LiteralToken<Character> {
        public Char(char value) {
            super("Char", value);
        }
    }

    /**
     * A string literal.
     */
    public static class String extends LiteralToken<java.lang.String> {
        public String(java.lang.String value) {
            super("String", value);
        }
    }

}
