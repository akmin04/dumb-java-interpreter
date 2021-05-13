package info.andrewmin.dji.tokens;

import info.andrewmin.dji.lexer.FileLoc;

/**
 * A symbol token.
 *
 * @see TypeTokenVariant
 */
public final class TypeToken extends Token {
    private final TypeTokenVariant type;

    /**
     * Construct a TypeToken.
     *
     * @param loc  the location of the first character of the token.
     * @param type the TypeTokenType.
     */
    public TypeToken(FileLoc startLoc, FileLoc endLoc, TypeTokenVariant type) {
        super("Type", startLoc, endLoc);
        this.type = type;
    }

    /**
     * Get the type variant.
     *
     * @return a TypeTokenVariant.
     */
    public TypeTokenVariant getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
//    @Override
//    public String toString() {
//        return super.toString() + ": " + type.name() + " " + type.type;
//    }
    @Override
    public String rawString() {
        return type.type;
    }
}
