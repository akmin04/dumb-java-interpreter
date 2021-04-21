package info.andrewmin.dji.lexer;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type of Token.
 * Names referenced from Go Lang (https://golang.org/pkg/go/token/#Token)
 *
 * @see Token
 */
public enum TokenType {
    EOF("EOF"),

    IDENT("IDENT"), // main
    INT("INT"), // 12345
    DOUBLE("DOUBLE"), // 123.45
    CHAR("CHAR"), // 'a'
    STRING("STRING"), //"abc"

    // Symbols

    ADD("+"), // +
    SUB("-"), // -
    MUL("*"), // *
    QUO("/"), // /
    REM("%"), // %

    ADD_ASSIGN("+="), // +=
    SUB_ASSIGN("-="), // -=
    MUL_ASSIGN("*="), // *=
    QUO_ASSIGN("/="), // /=
    REM_ASSIGN("%="); // %=

    /**
     * A map of raw symbols to their respective TokenTypes
     */
    public static Map<String, TokenType> symbolMap = Arrays.stream(new TokenType[]{
            ADD, SUB, MUL, QUO, REM,
            ADD_ASSIGN, SUB_ASSIGN, MUL_ASSIGN, QUO_ASSIGN, REM_ASSIGN
    })
            .collect(Collectors.toMap(e -> e.raw, e -> e));

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.name() + "(" + raw + ")";
    }

    final String raw;

    TokenType(String raw) {
        this.raw = raw;
    }
}

