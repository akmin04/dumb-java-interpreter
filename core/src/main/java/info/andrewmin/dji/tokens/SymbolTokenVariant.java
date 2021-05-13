package info.andrewmin.dji.tokens;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * All possible SymbolToken types.
 *
 * @see SymbolToken
 */
public enum SymbolTokenVariant {
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
    public static Map<String, SymbolTokenVariant> map = Arrays.stream(new SymbolTokenVariant[]{
            ADD, SUB, MUL, QUO, REM,
            ADD_ASSIGN, SUB_ASSIGN, MUL_ASSIGN, QUO_ASSIGN, REM_ASSIGN,
            ASSIGN, NOT, INC, DEC,
            EQL, LSS, GTR, NEQ, LEQ, GEQ, LAND, LOR,
            LPAREN, LBRACK, LBRACE, COMMA, PERIOD, RPAREN, RBRACK, RBRACE, SEMICOLON, COLON
    })
            .collect(Collectors.toMap(e -> e.symbol, e -> e));

    /**
     * All valid unary operators.
     */
    public static List<SymbolTokenVariant> unaryOps = List.of(SUB);

    /**
     * Get the precedence of a binary operator (higher value is higher precedence).
     *
     * @param op the operator.
     * @return the precedence (-1 if the operator is not valid)
     */
    public static int binaryOpPrecedence(SymbolTokenVariant op) {
        switch (op) {
            case MUL:
            case QUO:
                return 30;
            case ADD:
            case SUB:
                return 20;
            case EQL:
            case NEQ:
            case LSS:
            case GTR:
            case LEQ:
            case GEQ:
                return 10;
            case ASSIGN:
                return 0;
            default:
                return -1;
        }
    }

    public final String symbol;

    SymbolTokenVariant(String symbol) {
        this.symbol = symbol;
    }
}
