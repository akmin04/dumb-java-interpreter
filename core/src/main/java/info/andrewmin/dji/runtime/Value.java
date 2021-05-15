package info.andrewmin.dji.runtime;

import info.andrewmin.dji.exceptions.InternalCompilerException;
import info.andrewmin.dji.tokens.LiteralToken;
import info.andrewmin.dji.tokens.TypeTokenVariant;

public abstract class Value<T> {
    private final TypeTokenVariant type;
    private final T value;

    private Value(TypeTokenVariant type, T value) {
        this.type = type;
        this.value = value;
    }

    public static Value<?> fromToken(LiteralToken<?> token) {
        if (token instanceof LiteralToken.Boolean) {
            return new Boolean(((LiteralToken.Boolean) token).getValue());
        } else if (token instanceof LiteralToken.Int) {
            return new Int(((LiteralToken.Int) token).getValue());
        } else if (token instanceof LiteralToken.Double) {
            return new Double(((LiteralToken.Double) token).getValue());
        } else if (token instanceof LiteralToken.Char) {
            return new Char(((LiteralToken.Char) token).getValue());
        } else if (token instanceof LiteralToken.String) {
            return new String(((LiteralToken.String) token).getValue());
        }
        throw new InternalCompilerException("Unhandled type");
    }

    public TypeTokenVariant getType() {
        return type;
    }

    public T getValue() {
        return value;
    }

    @Override
    public java.lang.String toString() {
        return type + "(" + value + ")";
    }

    public static final class Boolean extends Value<java.lang.Boolean> {
        public Boolean(boolean value) {
            super(TypeTokenVariant.BOOLEAN, value);
        }
    }

    public static final class Int extends Value<Integer> {
        public Int(int value) {
            super(TypeTokenVariant.INT, value);
        }
    }

    public static final class Double extends Value<java.lang.Double> {
        public Double(double value) {
            super(TypeTokenVariant.DOUBLE, value);
        }
    }

    public static final class Char extends Value<Character> {
        public Char(char value) {
            super(TypeTokenVariant.CHAR, value);
        }
    }

    public static final class String extends Value<java.lang.String> {
        public String(java.lang.String value) {
            super(TypeTokenVariant.STRING, value);
        }
    }
}