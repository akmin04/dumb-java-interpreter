package info.andrewmin.dji.tokens;

/**
 * A base DJava token.
 * <p>
 * Token
 * ├── IdentifierToken (e.g. function names, variable names)
 * ├── KeywordToken (a subset of Java keywords https://docs.oracle.com/javase/tutorial/java/nutsandbolts/_keywords.html)
 * │   ├── if else for while do break continue return
 * │   └── boolean char int double void
 * ├── SymbolToken (a subset of Java symbols and operators)
 * │   ├── + - * / % += -= *= /= %=
 * │   ├── = ! ++ -- == < > != <= >= && ||
 * │   └── ( ) [ ] { } , . ; ;
 * └── LiteralToken (a literal value token)
 * └── Boolean, Int, Double, Char, String
 */
public abstract class Token {
    private final String name;

    protected Token(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Token(" + name + ")";
    }
}
