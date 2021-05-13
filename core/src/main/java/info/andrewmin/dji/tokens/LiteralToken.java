package info.andrewmin.dji.tokens;

import info.andrewmin.dji.lexer.FileLoc;

/**
 * A token with a literal value.
 *
 * @param <T> the value type.
 */
public abstract class LiteralToken<T> extends Token {
    private final T value;

    private LiteralToken(java.lang.String name, FileLoc startLoc, FileLoc endLoc, T value) {
        super(name + "Literal", startLoc, endLoc);
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
//    @Override
//    public java.lang.String toString() {
//        return super.toString() + ": " + value;
//    }
    @Override
    public java.lang.String rawString() {
        return value.toString();
    }

    /**
     * A boolean literal.
     */
    public static final class Boolean extends LiteralToken<java.lang.Boolean> {
        public Boolean(FileLoc startLoc, FileLoc endLoc, boolean value) {
            super("Boolean", startLoc, endLoc, value);
        }
    }

    /**
     * An integer literal.
     */
    public static final class Int extends LiteralToken<Integer> {
        public Int(FileLoc startLoc, FileLoc endLoc, int value) {
            super("Int", startLoc, endLoc, value);
        }
    }

    /**
     * A double literal.
     */
    public static final class Double extends LiteralToken<java.lang.Double> {
        public Double(FileLoc startLoc, FileLoc endLoc, double value) {
            super("Double", startLoc, endLoc, value);
        }
    }

    /**
     * A character literal.
     */
    public static final class Char extends LiteralToken<Character> {
        public Char(FileLoc startLoc, FileLoc endLoc, char value) {
            super("Char", startLoc, endLoc, value);
        }
    }

    /**
     * A string literal.
     */
    public static final class String extends LiteralToken<java.lang.String> {
        public String(FileLoc startLoc, FileLoc endLoc, java.lang.String value) {
            super("String", startLoc, endLoc, value);
        }
    }

}
