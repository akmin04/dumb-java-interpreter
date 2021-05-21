package info.andrewmin.dji.core.tokens;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A symbol token variant.
 *
 * @see SymbolToken
 */
public enum SymbolTokenVariant {

    // Math symbols.

    /**
     * The add symbol.
     */
    ADD("+"),
    /**
     * The subtract symbol.
     */
    SUB("-"),
    /**
     * The multiplication symbol.
     */
    MUL("*"),
    /**
     * The division symbol.
     */
    QUO("/"),
    /**
     * The remainder symbol.
     */
    REM("%"),
    /**
     * The add assign symbol.
     */
    ADD_ASSIGN("+="),
    /**
     * The subtract assign symbol.
     */
    SUB_ASSIGN("-="),
    /**
     * The multiplication assign symbol.
     */
    MUL_ASSIGN("*="),
    /**
     * The division assign symbol.
     */
    QUO_ASSIGN("/="),
    /**
     * The remainder assign symbol.
     */
    REM_ASSIGN("%="),

    // Binary operators.

    /**
     * The assign symbol.
     */
    ASSIGN("="),
    /**
     * The not symbol.
     */
    NOT("!"),
    /**
     * The increment symbol.
     */
    INC("++"),
    /**
     * The decrement symbol.
     */
    DEC("--"),
    /**
     * The equality symbol.
     */
    EQL("=="),
    /**
     * The less than symbol.
     */
    LSS("<"),
    /**
     * The greater than symbol.
     */
    GTR(">"),
    /**
     * The not equal symbol.
     */
    NEQ("!="),
    /**
     * The less than or equal to symbol.
     */
    LEQ("<="),
    /**
     * The greater than or equal to symbol.
     */
    GEQ(">="),
    /**
     * The boolean and symbol.
     */
    LAND("&&"),
    /**
     * The boolean or symbol.
     */
    LOR("||"),

    // Various symbols.

    /**
     * The left parenthesis symbol.
     */
    LPAREN("("),
    /**
     * The right parenthesis symbol.
     */
    RPAREN(")"),
    /**
     * The left bracket symbol.
     */
    LBRACK("["),
    /**
     * The right bracket symbol.
     */
    RBRACK("]"),
    /**
     * The left brace symbol.
     */
    LBRACE("{"),
    /**
     * The right brace symbol.
     */
    RBRACE("}"),
    /**
     * The comma symbol.
     */
    COMMA(","),
    /**
     * The period symbol.
     */
    PERIOD("."),
    /**
     * The semicolon symbol.
     */
    SEMICOLON(";"),
    /**
     * The colon symbol.
     */
    COLON(":"),
    ;

    /**
     * The raw symbol string.
     */
    public final String symbol;

    /**
     * THe map of raw symbols to their respective variant.
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
     * The list of valid binary operators.
     */
    public static List<SymbolTokenVariant> binaryOps = List.of(
            ADD, SUB, MUL, QUO, REM,
            ADD_ASSIGN, SUB_ASSIGN, MUL_ASSIGN, QUO_ASSIGN, REM_ASSIGN,
            ASSIGN, NOT, INC, DEC,
            EQL, LSS, GTR, NEQ, LEQ, GEQ, LAND, LOR
    );

    /**
     * The list of valid unary operators.
     */
    public static List<SymbolTokenVariant> unaryOps = List.of(SUB, NOT);

    /**
     * Get the precedence of a binary operator (higher value is higher precedence).
     *
     * @param op The operator.
     * @return The precedence (-1 if the operator is not valid)
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

    /**
     * Construct a new symbol token variant
     *
     * @param symbol The raw symbol string.
     */
    SymbolTokenVariant(String symbol) {
        this.symbol = symbol;
    }
}
