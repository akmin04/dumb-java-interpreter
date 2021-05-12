package info.andrewmin.dji.tokens;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * All possible KeywordToken types.
 *
 * @see KeywordToken
 */
public enum KeywordTokenType {
    IF("if"),
    ELSE("else"),
    FOR("for"),
    WHILE("while"),
    BREAK("break"),
    CONTINUE("continue"),
    RETURN("return"),

    BOOLEAN("boolean"),
    CHAR("char"),
    INT("int"),
    DOUBLE("double"),
    STRING("String"),
    VOID("void"),

    TRUE("true"),
    FALSE("false"),
    ;

    /**
     * A map of raw keywords to their respective KeywordTokenType
     */
    public static Map<String, KeywordTokenType> map = Arrays.stream(new KeywordTokenType[]{
            IF, ELSE, FOR, WHILE, BREAK, CONTINUE, RETURN,
            BOOLEAN, CHAR, INT, DOUBLE, STRING, VOID,
            TRUE, FALSE
    })
            .collect(Collectors.toMap(e -> e.keyword, e -> e));

    public static List<KeywordTokenType> primitives = List.of(BOOLEAN, CHAR, INT, DOUBLE, STRING, VOID);

    public static LiteralToken<?> defaultLiteral(KeywordTokenType type) {
        if (type == BOOLEAN)
            return new LiteralToken.Boolean(null, false);
        else if (type == CHAR)
            return new LiteralToken.Char(null, '\u0000');
        else if (type == INT)
            return new LiteralToken.Int(null, 0);
        else if (type == DOUBLE)
            return new LiteralToken.Double(null, 0.0);
        else if (type == STRING)
            return new LiteralToken.String(null, null);
        return null;
    }

    final String keyword;

    KeywordTokenType(String keyword) {
        this.keyword = keyword;
    }
}
