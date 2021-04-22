package info.andrewmin.dji.tokens;

import java.util.Arrays;
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
    DO("do"),
    BREAK("break"),
    CONTINUE("continue"),
    RETURN("return"),

    BOOLEAN("boolean"),
    CHAR("char"),
    INT("int"),
    DOUBLE("double"),
    VOID("void"),
    ;

    /**
     * A map of raw keywords to their respective KeywordTokenType
     */
    public static Map<String, KeywordTokenType> map = Arrays.stream(new KeywordTokenType[]{
            IF, ELSE, FOR, WHILE, DO, BREAK, CONTINUE, RETURN,
            BOOLEAN, CHAR, INT, DOUBLE, VOID
    })
            .collect(Collectors.toMap(e -> e.keyword, e -> e));

    final String keyword;

    KeywordTokenType(String keyword) {
        this.keyword = keyword;
    }
}
