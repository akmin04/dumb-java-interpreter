package info.andrewmin.dji.core.tokens;

import info.andrewmin.dji.core.lexer.FileLoc;

/**
 * A base DJava token.
 * <pre>
 * Token
 * ├── IdentifierToken (e.g. function names, variable names)
 * ├── KeywordToken (a subset of Java keywords)
 * │   ├── if else for while do break continue return
 * │   └── boolean char int double void
 * ├── SymbolToken (a subset of Java symbols and operators)
 * │   ├── + - * / % += -= *= /= %=
 * │   ├── = ! ++ -- == &lt; &gt; != &lt;= &gt;= &amp;&amp; ||
 * │   └── ( ) [ ] { } , . ; :
 * └── LiteralToken (a literal value token)
 *     └── Boolean, Int, Double, Char, String
 * </pre>
 */
public abstract class Token {
    private final String name;
    private final FileLoc startLoc;
    private final FileLoc endLoc;

    protected Token(String name, FileLoc startLoc, FileLoc endLoc) {
        this.name = name;
        this.startLoc = startLoc;
        this.endLoc = endLoc;
    }

    public FileLoc getStartLoc() {
        return startLoc;
    }

    public FileLoc getEndLoc() {
        return endLoc;
    }

    public int length() {
        return rawString().length();
    }

    public boolean isKeyword(KeywordTokenVariant type) {
        if (!(this instanceof KeywordToken)) {
            return false;
        }
        return ((KeywordToken) this).getVariant() == type;
    }

    public boolean isSymbol(SymbolTokenVariant type) {
        if (!(this instanceof SymbolToken)) {
            return false;
        }
        return ((SymbolToken) this).getVariant() == type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return name + "(" + rawString() + ")";
    }

    abstract public String rawString();
}
