package info.andrewmin.dji.tokens;

import info.andrewmin.dji.lexer.FileLoc;

/**
 * A token with a literal value.
 *
 * @param <T> the value type.
 */
public abstract class LiteralToken<T> extends Token {
    private final T value;

    private LiteralToken(java.lang.String name, FileLoc loc, T value) {
        super(name + "Literal", loc);
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
    public static final class Boolean extends LiteralToken<java.lang.Boolean> {
        public Boolean(FileLoc loc, boolean value) {
            super("Boolean", loc, value);
        }
    }

    /**
     * An integer literal.
     */
    public static final class Int extends LiteralToken<Integer> {
        public Int(FileLoc loc, int value) {
            super("Int", loc, value);
        }
    }

    /**
     * A double literal.
     */
    public static final class Double extends LiteralToken<java.lang.Double> {
        public Double(FileLoc loc, double value) {
            super("Double", loc, value);
        }
    }

    /**
     * A character literal.
     */
    public static final class Char extends LiteralToken<Character> {
        public Char(FileLoc loc, char value) {
            super("Char", loc, value);
        }
    }

    /**
     * A string literal.
     */
    public static final class String extends LiteralToken<java.lang.String> {
        public String(FileLoc loc, java.lang.String value) {
            super("String", loc, value);
        }
    }

}
