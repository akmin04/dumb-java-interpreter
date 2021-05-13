package info.andrewmin.dji.runtime;

import info.andrewmin.dji.tokens.TypeTokenVariant;

public final class Var {
    private final TypeTokenVariant type;
    private final String name;

    public Var(TypeTokenVariant type, String name) {
        this.type = type;
        this.name = name;
    }

    public TypeTokenVariant getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + "(" + type.type + ")";
    }
}
