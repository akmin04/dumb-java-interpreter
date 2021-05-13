package info.andrewmin.dji.tokens;

import info.andrewmin.dji.exceptions.InternalCompilerException;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum TypeTokenVariant {
    BOOLEAN("boolean"),
    CHAR("char"),
    INT("int"),
    DOUBLE("double"),
    STRING("String"),
    VOID("void"),
    ;

    /**
     * A map of raw keywords to their respective KeywordTokenType
     */
    public static Map<String, TypeTokenVariant> map = Arrays.stream(new TypeTokenVariant[]{
            BOOLEAN, CHAR, INT, DOUBLE, STRING, VOID
    })
            .collect(Collectors.toMap(e -> e.type, e -> e));

    public final String type;


    /**
     * Get the default literal value Token for primitives.
     *
     * @param type the primitive type.
     * @return the default literal.
     */
    public static LiteralToken<?> defaultLiteral(TypeTokenVariant type) {
        switch (type) {
            case BOOLEAN:
                return new LiteralToken.Boolean(null, null, false);
            case CHAR:
                return new LiteralToken.Char(null, null, '\u0000');
            case INT:
                return new LiteralToken.Int(null, null, 0);
            case DOUBLE:
                return new LiteralToken.Double(null, null, 0.0);
            case STRING:
                return new LiteralToken.String(null, null, null);
            case VOID:
            default:
                throw new InternalCompilerException("void was used as a type");
        }
    }

    TypeTokenVariant(String type) {
        this.type = type;
    }
}
