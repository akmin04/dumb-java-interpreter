package info.andrewmin.dji.tokens;

import info.andrewmin.dji.lexer.FileLoc;

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
    private final FileLoc loc;

    protected Token(String name, FileLoc loc) {
        this.name = name;
        this.loc = loc;
    }

    public FileLoc getLoc() {
        return loc;
    }

    public boolean isKeyword(KeywordTokenType type) {
        if (!(this instanceof KeywordToken)) {
            return false;
        }
        return ((KeywordToken) this).getType() == type;
    }

    public boolean isSymbol(SymbolTokenType type) {
        if (!(this instanceof SymbolToken)) {
            return false;
        }
        return ((SymbolToken) this).getType() == type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Token(" + name + ")";
    }
}
