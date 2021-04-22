package info.andrewmin.dji.tokens;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * All possible SymbolToken types.
 *
 * @see SymbolToken
 */
public enum SymbolTokenType {
    ADD("+"),
    SUB("-"),
    MUL("*"),
    QUO("/"),
    REM("%"),
    ADD_ASSIGN("+="),
    SUB_ASSIGN("-="),
    MUL_ASSIGN("*="),
    QUO_ASSIGN("/="),
    REM_ASSIGN("%="),

    ASSIGN("="),
    NOT("!"),
    INC("++"),
    DEC("--"),
    EQL("=="),
    LSS("<"),
    GTR(">"),
    NEQ("!="),
    LEQ("<="),
    GEQ(">="),
    LAND("&&"),
    LOR("||"),

    LPAREN("("),
    RPAREN(")"),
    LBRACK("["),
    RBRACK("]"),
    LBRACE("{"),
    RBRACE("}"),
    COMMA(","),
    PERIOD("."),
    SEMICOLON(";"),
    COLON(":"),
    ;

    /**
     * A map of raw symbols to their respective SymbolTokenType.
     */
    public static Map<String, SymbolTokenType> map = Arrays.stream(new SymbolTokenType[]{
            ADD, SUB, MUL, QUO, REM,
            ADD_ASSIGN, SUB_ASSIGN, MUL_ASSIGN, QUO_ASSIGN, REM_ASSIGN,
            ASSIGN, NOT, INC, DEC,
            EQL, LSS, GTR, NEQ, LEQ, GEQ, LAND, LOR,
            LPAREN, LBRACK, LBRACE, COMMA, PERIOD, RPAREN, RBRACK, RBRACE, SEMICOLON, COLON
    })
            .collect(Collectors.toMap(e -> e.symbol, e -> e));

    final String symbol;

    SymbolTokenType(String symbol) {
        this.symbol = symbol;
    }
}
