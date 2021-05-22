package info.andrewmin.dji.core.tokens;

import info.andrewmin.dji.core.exceptions.InternalException;
import info.andrewmin.dji.core.runtime.Value;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A type token variant.
 */
public enum TypeTokenVariant {
    /**
     * The boolean type.
     */
    BOOLEAN("boolean"),
    /**
     * The character type.
     */
    CHAR("char"),
    /**
     * The integer type.
     */
    INT("int"),
    /**
     * The double type.
     */
    DOUBLE("double"),
    /**
     * The string type.
     */
    STRING("String"),
    /**
     * The void type.
     */
    VOID("void"),
    ;

    /**
     * A map of raw keywords to their respective variant.
     */
    public static Map<String, TypeTokenVariant> map = Arrays.stream(new TypeTokenVariant[]{
            BOOLEAN, CHAR, INT, DOUBLE, STRING, VOID
    })
            .collect(Collectors.toMap(e -> e.type, e -> e));

    public final String type;


    /**
     * Get the default literal value Token for primitives.
     *
     * @param type The primitive type.
     * @return The default literal.
     */
    public static Value<?> defaultValue(TypeTokenVariant type) {
        switch (type) {
            case BOOLEAN:
                return new Value.Boolean(false);
            case CHAR:
                return new Value.Char('\u0000');
            case INT:
                return new Value.Int(0);
            case DOUBLE:
                return new Value.Double(0.0);
            case STRING:
                return new Value.String("");
            case VOID:
            default:
                throw new InternalException("void was used as a type");
        }

    }

    /**
     * Construct a new type token variant.
     *
     * @param type The raw type string.
     */
    TypeTokenVariant(String type) {
        this.type = type;
    }
}
