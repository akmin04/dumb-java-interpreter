package info.andrewmin.dji.core.tokens;

import info.andrewmin.dji.core.lexer.FileLoc;

/**
 * A token with a literal value.
 *
 * @param <T> the value type.
 */
public abstract class LiteralToken<T> extends Token {
    private final T value;

    /**
     * Construct a new literal token.
     * <p>
     * To be invoked by subclass constructors only.
     *
     * @param name     The name of the literal token.
     * @param startLoc The starting location of the first character of the token (inclusive).
     * @param endLoc   The ending location of the first character of the token (exclusive).
     * @param value    The literal value.
     */
    private LiteralToken(java.lang.String name, FileLoc startLoc, FileLoc endLoc, T value) {
        super(name + "Literal", startLoc, endLoc);
        this.value = value;
    }

    /**
     * Get the token value.
     *
     * @return The token value.
     */
    public T getValue() {
        return value;
    }

    @Override
    public java.lang.String rawString() {
        return value.toString();
    }

    /**
     * A boolean literal.
     */
    public static final class Boolean extends LiteralToken<java.lang.Boolean> {
        /**
         * Construct a new boolean literal.
         *
         * @param startLoc The starting location of the first character of the token (inclusive).
         * @param endLoc   The ending location of the first character of the token (exclusive).
         * @param value    The boolean value.
         */
        public Boolean(FileLoc startLoc, FileLoc endLoc, boolean value) {
            super("Boolean", startLoc, endLoc, value);
        }
    }

    /**
     * An integer literal.
     */
    public static final class Int extends LiteralToken<Integer> {
        /**
         * Construct a new integer literal.
         *
         * @param startLoc The starting location of the first character of the token (inclusive).
         * @param endLoc   The ending location of the first character of the token (exclusive).
         * @param value    The integer value.
         */
        public Int(FileLoc startLoc, FileLoc endLoc, int value) {
            super("Int", startLoc, endLoc, value);
        }
    }

    /**
     * A double literal.
     */
    public static final class Double extends LiteralToken<java.lang.Double> {
        /**
         * Construct a new double literal.
         *
         * @param startLoc The starting location of the first character of the token (inclusive).
         * @param endLoc   The ending location of the first character of the token (exclusive).
         * @param value    The double value.
         */
        public Double(FileLoc startLoc, FileLoc endLoc, double value) {
            super("Double", startLoc, endLoc, value);
        }
    }

    /**
     * A character literal.
     */
    public static final class Char extends LiteralToken<Character> {
        /**
         * Construct a new character literal.
         *
         * @param startLoc The starting location of the first character of the token (inclusive).
         * @param endLoc   The ending location of the first character of the token (exclusive).
         * @param value    The character value.
         */
        public Char(FileLoc startLoc, FileLoc endLoc, char value) {
            super("Char", startLoc, endLoc, value);
        }
    }

    /**
     * A string literal.
     */
    public static final class String extends LiteralToken<java.lang.String> {
        /**
         * Construct a new string literal.
         *
         * @param startLoc The starting location of the first character of the token (inclusive).
         * @param endLoc   The ending location of the first character of the token (exclusive).
         * @param value    The string value.
         */
        public String(FileLoc startLoc, FileLoc endLoc, java.lang.String value) {
            super("String", startLoc, endLoc, value);
        }
    }

}
