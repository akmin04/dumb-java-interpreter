package info.andrewmin.dji.core.runtime;

import info.andrewmin.dji.core.exceptions.BinaryTypeMismatchException;
import info.andrewmin.dji.core.exceptions.InternalException;
import info.andrewmin.dji.core.exceptions.UnaryTypeMismatchException;
import info.andrewmin.dji.core.tokens.LiteralToken;
import info.andrewmin.dji.core.tokens.SymbolTokenVariant;
import info.andrewmin.dji.core.tokens.TypeTokenVariant;

/**
 * A runtime variable value.
 *
 * @param <T> The variable type.
 * @see Var
 */
public abstract class Value<T> {
    private final TypeTokenVariant type;
    private final T value;

    /**
     * Construct a new runtime variable value.
     * <p>
     * To be invoked by subclass constructors only.
     *
     * @param type  The variable type.
     * @param value The variable value.
     */
    private Value(TypeTokenVariant type, T value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Construct a new runtime variable value from a literal token.
     *
     * @param token The literal token.
     * @return The runtime variable value.
     */
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
        throw new InternalException("Unhandled type");
    }

    /**
     * Get the variable type.
     *
     * @return The variable type.
     */
    public TypeTokenVariant getType() {
        return type;
    }

    /**
     * Get the variable value.
     *
     * @return The variable value.
     */
    public T getValue() {
        return value;
    }

    /**
     * Perform addition with another value.
     *
     * @param other The other value.
     * @return The resulting value.
     */
    public abstract Value<?> add(Value<?> other);

    /**
     * Perform subtraction with another value.
     *
     * @param other The other value.
     * @return The resulting value.
     */
    public abstract Value<?> sub(Value<?> other);

    /**
     * Perform multiplication with another value.
     *
     * @param other The other value.
     * @return The resulting value.
     */
    public abstract Value<?> mul(Value<?> other);

    /**
     * Perform division with another value.
     *
     * @param other The other value.
     * @return The resulting value.
     */
    public abstract Value<?> quo(Value<?> other);

    /**
     * Perform remainder with another value.
     *
     * @param other The other value.
     * @return The resulting value.
     */
    public abstract Value<?> rem(Value<?> other);

    /**
     * Perform binary inversion.
     *
     * @return The resulting value.
     */
    public abstract Value<?> not();

    /**
     * Perform number negation.
     *
     * @return The resulting value.
     */
    public abstract Value<?> negate();

    /**
     * Perform equality comparison with another value.
     *
     * @param other The other value.
     * @return The comparison result.
     */
    public abstract boolean eql(Value<?> other);

    /**
     * Perform less than comparison with another value.
     *
     * @param other The other value.
     * @return The comparison result.
     */
    public abstract boolean lss(Value<?> other);

    /**
     * Perform greater than comparison with another value.
     *
     * @param other The other value.
     * @return The comparison result.
     */
    public abstract boolean gtr(Value<?> other);

    /**
     * Perform not equal comparison with another value.
     *
     * @param other The other value.
     * @return The comparison result.
     */
    public boolean neq(Value<?> other) {
        return !eql(other);
    }

    /**
     * Perform less than or equal to comparison with another value.
     *
     * @param other The other value.
     * @return The comparison result.
     */
    public boolean leq(Value<?> other) {
        return !gtr(other);
    }

    /**
     * Perform greater than or equal to comparison with another value.
     *
     * @param other The other value.
     * @return The comparison result.
     */
    public boolean geq(Value<?> other) {
        return !lss(other);
    }

    /**
     * Perform binary and with another value.
     *
     * @param other The other value.
     * @return The binary result.
     */
    public abstract boolean land(Value<?> other);

    /**
     * Perform binary or with another value.
     *
     * @param other The other value.
     * @return The binary result.
     */
    public abstract boolean lor(Value<?> other);

    @Override
    public java.lang.String toString() {
        return type + "(" + value + ")";
    }

    /**
     * A runtime boolean value.
     */
    public static final class Boolean extends Value<java.lang.Boolean> {
        /**
         * Construct a new runtime boolean value.
         *
         * @param value The boolean value.
         */
        public Boolean(boolean value) {
            super(TypeTokenVariant.BOOLEAN, value);
        }

        @Override
        public Value<?> add(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.ADD, getType(), other.getType());
        }

        @Override
        public Value<?> sub(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.SUB, getType(), other.getType());
        }

        @Override
        public Value<?> mul(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.MUL, getType(), other.getType());
        }

        @Override
        public Value<?> quo(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.QUO, getType(), other.getType());
        }

        @Override
        public Value<?> rem(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.REM, getType(), other.getType());
        }

        @Override
        public Value<?> not() {
            return new Value.Boolean(!getValue());
        }

        @Override
        public Value<?> negate() {
            throw new UnaryTypeMismatchException(SymbolTokenVariant.SUB, getType());
        }

        @Override
        public boolean eql(Value<?> other) {
            if (other instanceof Boolean) {
                return getValue().equals(((Boolean) other).getValue());
            }
            throw new BinaryTypeMismatchException(SymbolTokenVariant.EQL, getType(), other.getType());
        }

        @Override
        public boolean lss(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.LSS, getType(), other.getType());
        }

        @Override
        public boolean gtr(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.GTR, getType(), other.getType());
        }

        @Override
        public boolean land(Value<?> other) {
            if (other instanceof Boolean) {
                return getValue() && ((Boolean) other).getValue();
            }
            throw new BinaryTypeMismatchException(SymbolTokenVariant.LAND, getType(), other.getType());
        }

        @Override
        public boolean lor(Value<?> other) {
            if (other instanceof Boolean) {
                return getValue() || ((Boolean) other).getValue();
            }
            throw new BinaryTypeMismatchException(SymbolTokenVariant.LOR, getType(), other.getType());
        }
    }

    /**
     * A runtime integer value.
     */
    public static final class Int extends Value<Integer> {
        /**
         * Construct a new runtime integer value.
         *
         * @param value The integer value.
         */
        public Int(int value) {
            super(TypeTokenVariant.INT, value);
        }

        @Override
        public Value<?> add(Value<?> other) {
            if (other instanceof Int) {
                return new Value.Int(getValue() + ((Value.Int) other).getValue());
            }
            throw new BinaryTypeMismatchException(SymbolTokenVariant.ADD, getType(), other.getType());
        }

        @Override
        public Value<?> sub(Value<?> other) {
            if (other instanceof Value.Int) {
                return new Value.Int(getValue() - ((Value.Int) other).getValue());
            }
            throw new BinaryTypeMismatchException(SymbolTokenVariant.SUB, getType(), other.getType());
        }

        @Override
        public Value<?> mul(Value<?> other) {
            if (other instanceof Value.Int) {
                return new Value.Int(getValue() * ((Value.Int) other).getValue());
            }
            throw new BinaryTypeMismatchException(SymbolTokenVariant.MUL, getType(), other.getType());
        }

        @Override
        public Value<?> quo(Value<?> other) {
            if (other instanceof Value.Int) {
                return new Value.Int(getValue() / ((Value.Int) other).getValue());
            }
            throw new BinaryTypeMismatchException(SymbolTokenVariant.QUO, getType(), other.getType());
        }

        @Override
        public Value<?> rem(Value<?> other) {
            if (other instanceof Value.Int) {
                return new Value.Int(getValue() % ((Value.Int) other).getValue());
            }
            throw new BinaryTypeMismatchException(SymbolTokenVariant.REM, getType(), other.getType());
        }

        @Override
        public Value<?> not() {
            throw new UnaryTypeMismatchException(SymbolTokenVariant.NOT, getType());
        }

        @Override
        public Value<?> negate() {
            return new Value.Int(-getValue());
        }

        @Override
        public boolean eql(Value<?> other) {
            if (other instanceof Value.Int) {
                return getValue().equals(((Value.Int) other).getValue());
            }
            throw new BinaryTypeMismatchException(SymbolTokenVariant.EQL, getType(), other.getType());
        }

        @Override
        public boolean lss(Value<?> other) {
            if (other instanceof Value.Int) {
                return getValue() < ((Value.Int) other).getValue();
            }
            throw new BinaryTypeMismatchException(SymbolTokenVariant.LSS, getType(), other.getType());
        }

        @Override
        public boolean gtr(Value<?> other) {
            if (other instanceof Value.Int) {
                return getValue() > ((Value.Int) other).getValue();
            }
            throw new BinaryTypeMismatchException(SymbolTokenVariant.GTR, getType(), other.getType());
        }

        @Override
        public boolean land(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.LAND, getType(), other.getType());
        }

        @Override
        public boolean lor(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.LOR, getType(), other.getType());
        }
    }

    /**
     * A runtime double value.
     */
    public static final class Double extends Value<java.lang.Double> {
        /**
         * Construct a new runtime double value.
         *
         * @param value The double value.
         */
        public Double(double value) {
            super(TypeTokenVariant.DOUBLE, value);
        }

        @Override
        public Value<?> add(Value<?> other) {
            if (other instanceof Double) {
                return new Value.Double(getValue() + ((Value.Double) other).getValue());
            }
            throw new BinaryTypeMismatchException(SymbolTokenVariant.ADD, getType(), other.getType());
        }

        @Override
        public Value<?> sub(Value<?> other) {
            if (other instanceof Value.Double) {
                return new Value.Double(getValue() - ((Value.Double) other).getValue());
            }
            throw new BinaryTypeMismatchException(SymbolTokenVariant.SUB, getType(), other.getType());
        }

        @Override
        public Value<?> mul(Value<?> other) {
            if (other instanceof Value.Double) {
                return new Value.Double(getValue() * ((Value.Double) other).getValue());
            }
            throw new BinaryTypeMismatchException(SymbolTokenVariant.MUL, getType(), other.getType());
        }

        @Override
        public Value<?> quo(Value<?> other) {
            if (other instanceof Value.Double) {
                return new Value.Double(getValue() / ((Value.Double) other).getValue());
            }
            throw new BinaryTypeMismatchException(SymbolTokenVariant.QUO, getType(), other.getType());
        }

        @Override
        public Value<?> rem(Value<?> other) {
            if (other instanceof Value.Double) {
                return new Value.Double(getValue() % ((Value.Double) other).getValue());
            }
            throw new BinaryTypeMismatchException(SymbolTokenVariant.REM, getType(), other.getType());
        }

        @Override
        public Value<?> not() {
            throw new UnaryTypeMismatchException(SymbolTokenVariant.NOT, getType());
        }

        @Override
        public Value<?> negate() {
            return new Value.Double(-getValue());
        }

        @Override
        public boolean eql(Value<?> other) {
            if (other instanceof Value.Double) {
                return getValue().equals(((Value.Double) other).getValue());
            }
            throw new BinaryTypeMismatchException(SymbolTokenVariant.EQL, getType(), other.getType());
        }

        @Override
        public boolean lss(Value<?> other) {
            if (other instanceof Value.Double) {
                return getValue() < ((Value.Double) other).getValue();
            }
            throw new BinaryTypeMismatchException(SymbolTokenVariant.LSS, getType(), other.getType());
        }

        @Override
        public boolean gtr(Value<?> other) {
            if (other instanceof Value.Double) {
                return getValue() > ((Value.Double) other).getValue();
            }
            throw new BinaryTypeMismatchException(SymbolTokenVariant.GTR, getType(), other.getType());
        }

        @Override
        public boolean land(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.LAND, getType(), other.getType());
        }

        @Override
        public boolean lor(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.LOR, getType(), other.getType());
        }
    }

    /**
     * A runtime character value.
     */
    public static final class Char extends Value<Character> {
        /**
         * Construct a new runtime character value.
         *
         * @param value The character value.
         */
        public Char(char value) {
            super(TypeTokenVariant.CHAR, value);
        }

        @Override
        public Value<?> add(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.ADD, getType(), other.getType());
        }

        @Override
        public Value<?> sub(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.SUB, getType(), other.getType());
        }

        @Override
        public Value<?> mul(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.MUL, getType(), other.getType());
        }

        @Override
        public Value<?> quo(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.QUO, getType(), other.getType());
        }

        @Override
        public Value<?> rem(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.REM, getType(), other.getType());
        }

        @Override
        public Value<?> not() {
            throw new UnaryTypeMismatchException(SymbolTokenVariant.NOT, getType());
        }

        @Override
        public Value<?> negate() {
            throw new UnaryTypeMismatchException(SymbolTokenVariant.SUB, getType());
        }

        @Override
        public boolean eql(Value<?> other) {
            if (other instanceof Value.Char) {
                return getValue().equals(((Value.Char) other).getValue());
            }
            throw new BinaryTypeMismatchException(SymbolTokenVariant.EQL, getType(), other.getType());
        }

        @Override
        public boolean lss(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.LSS, getType(), other.getType());
        }

        @Override
        public boolean gtr(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.GTR, getType(), other.getType());
        }

        @Override
        public boolean land(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.LAND, getType(), other.getType());
        }

        @Override
        public boolean lor(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.LOR, getType(), other.getType());
        }
    }

    /**
     * A runtime character value.
     */
    public static final class String extends Value<java.lang.String> {
        /**
         * Construct a new runtime string value.
         *
         * @param value The string value.
         */
        public String(java.lang.String value) {
            super(TypeTokenVariant.STRING, value);
        }

        @Override
        public Value<?> add(Value<?> other) {
            if (other instanceof Value.String) {
                return new Value.String(getValue() + other.getValue());
            }
            throw new BinaryTypeMismatchException(SymbolTokenVariant.ADD, getType(), other.getType());
        }

        @Override
        public Value<?> sub(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.SUB, getType(), other.getType());
        }

        @Override
        public Value<?> mul(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.MUL, getType(), other.getType());
        }

        @Override
        public Value<?> quo(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.QUO, getType(), other.getType());
        }

        @Override
        public Value<?> rem(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.REM, getType(), other.getType());
        }

        @Override
        public Value<?> not() {
            throw new UnaryTypeMismatchException(SymbolTokenVariant.NOT, getType());
        }

        @Override
        public Value<?> negate() {
            throw new UnaryTypeMismatchException(SymbolTokenVariant.SUB, getType());
        }

        @Override
        public boolean eql(Value<?> other) {
            if (other instanceof Value.String) {
                return getValue().equals(((Value.String) other).getValue());
            }
            throw new BinaryTypeMismatchException(SymbolTokenVariant.EQL, getType(), other.getType());
        }

        @Override
        public boolean lss(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.LSS, getType(), other.getType());
        }

        @Override
        public boolean gtr(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.GTR, getType(), other.getType());
        }

        @Override
        public boolean land(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.LAND, getType(), other.getType());
        }

        @Override
        public boolean lor(Value<?> other) {
            throw new BinaryTypeMismatchException(SymbolTokenVariant.LOR, getType(), other.getType());
        }
    }
}