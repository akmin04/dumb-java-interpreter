package info.andrewmin.dji.tokens;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * All possible KeywordToken types.
 *
 * @see KeywordToken
 */
public enum KeywordTokenVariant {
    IF("if"),
    ELSE("else"),
    FOR("for"),
    WHILE("while"),
    BREAK("break"),
    CONTINUE("continue"),
    RETURN("return"),

    TRUE("true"),
    FALSE("false"),
    ;

    /**
     * A map of raw keywords to their respective KeywordTokenType
     */
    public static Map<String, KeywordTokenVariant> map = Arrays.stream(new KeywordTokenVariant[]{
            IF, ELSE, FOR, WHILE, BREAK, CONTINUE, RETURN,
            TRUE, FALSE
    })
            .collect(Collectors.toMap(e -> e.keyword, e -> e));

    public final String keyword;

    KeywordTokenVariant(String keyword) {
        this.keyword = keyword;
    }
}
