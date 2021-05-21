package info.andrewmin.dji.core.tokens;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A keyword token variant.
 *
 * @see KeywordToken
 */
public enum KeywordTokenVariant {
    /**
     * The if statement keyword.
     */
    IF("if"),
    /**
     * The else statement keyword.
     */
    ELSE("else"),
    /**
     * The for loop statement keyword.
     */
    FOR("for"),
    /**
     * The while loop statement keyword.
     */
    WHILE("while"),
    /**
     * The break statement keyword.
     */
    BREAK("break"),
    /**
     * The continue statement keyword.
     */
    CONTINUE("continue"),
    /**
     * The return statement keyword.
     */
    RETURN("return"),
    /**
     * The true boolean literal keyword.
     */
    TRUE("true"),
    /**
     * The false boolean literal keyword.
     */
    FALSE("false"),
    ;

    /**
     * The raw keyword string.
     */
    public final String keyword;

    /**
     * The map of raw keywords to their respective variant.
     */
    public static Map<String, KeywordTokenVariant> map = Arrays.stream(new KeywordTokenVariant[]{
            IF, ELSE, FOR, WHILE, BREAK, CONTINUE, RETURN,
            TRUE, FALSE
    })
            .collect(Collectors.toMap(e -> e.keyword, e -> e));

    /**
     * Construct a new keyword token variant.
     *
     * @param keyword The raw keyword string.
     */
    KeywordTokenVariant(String keyword) {
        this.keyword = keyword;
    }
}
