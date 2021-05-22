package info.andrewmin.dji.core.runtime;

import info.andrewmin.dji.core.tokens.TypeTokenVariant;

/**
 * A runtime variable.
 *
 * @see Value
 */
public final class Var {
    private final TypeTokenVariant type;
    private final String name;

    /**
     * Construct a new runtime variable
     *
     * @param type The variable type.
     * @param name The variable name.
     */
    public Var(TypeTokenVariant type, String name) {
        this.type = type;
        this.name = name;
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
     * Get the variable name.
     *
     * @return The variable name.
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + "(" + type.type + ")";
    }
}
