package info.andrewmin.dji.lexer;

import info.andrewmin.dji.exceptions.BaseUserException;
import info.andrewmin.dji.exceptions.ExpectedCharacterException;
import info.andrewmin.dji.exceptions.InternalCompilerError;
import info.andrewmin.dji.exceptions.InvalidTokenException;
import info.andrewmin.dji.tokens.*;

/**
 * A lexical analyzer that splits a given program into Tokens.
 *
 * @see Token
 * @see FileCharIterator
 */
public class Lexer {

    private final FileCharIterator iter;

    /**
     * Initiailize a Lexer from a file name.
     *
     * @param fileName the file name.
     */
    public Lexer(String fileName) throws BaseUserException {
        this.iter = new FileCharIterator(fileName);
    }

    /**
     * Whether or not more tokens exist.
     *
     * @return if the end of the file has been reached or not.
     */
    public boolean hasNext() {
        return iter.peek() != null;
    }

    /**
     * Get the next Token.
     *
     * @return the next Token.
     */
    public Token next() throws BaseUserException {
        // Get the first non-whitespace character
        FileChar firstFileChar = new FileChar(' ');
        while (Character.isWhitespace(firstFileChar.getC())) {
            firstFileChar = iter.next();
            if (firstFileChar == null) {
                throw new InternalCompilerError("Attempted to get next token, but at the end.");
            }
        }

        Token t;
        if (Character.isDigit(firstFileChar.getC())) {
            t = nextNumberLiteralToken(firstFileChar.getC());
        } else if (firstFileChar.getC() == '\'') {
            t = nextCharLiteralToken();
        } else if (firstFileChar.getC() == '"') {
            t = nextStringLiteralToken();
        } else if (isIdentifierCharacter(firstFileChar.getC())) {
            t = nextWordToken(firstFileChar.getC());
        } else {
            t = nextSymbolToken(firstFileChar.getC());
        }

        if (t == null) {
            throw new InvalidTokenException(firstFileChar);
        }
        return t;
    }

    /**
     * Check if a character is a valid Java identifier character.
     * <p>
     * https://docs.oracle.com/cd/E19798-01/821-1841/bnbuk/index.html
     *
     * @param c the character.
     * @return a boolean.
     */
    private boolean isIdentifierCharacter(char c) {
        return Character.isAlphabetic(c) || Character.isDigit(c) || c == '_' || c == '$';
    }

    /**
     * Construct the next number literal token (integer or double literal).
     *
     * @param firstChar the first character.
     * @return a LiteralToken.
     * @see LiteralToken.Int
     * @see LiteralToken.Double
     */
    private Token nextNumberLiteralToken(char firstChar) throws BaseUserException {
        StringBuilder rawLiteralBuilder = new StringBuilder(Character.toString(firstChar));

        // Keep peeking next digits or decimal points (only allow one decimal point)
        boolean hasDecimal = false;

        while (iter.peek() != null
                && (Character.isDigit(iter.peek().getC()) || iter.peek().getC() == '.' && !hasDecimal)) {
            hasDecimal = hasDecimal || iter.peek().getC() == '.';
            rawLiteralBuilder.append(iter.next().getC());
        }

        String rawLiteral = rawLiteralBuilder.toString();

        // TODO try/catch out of range literals
        if (hasDecimal) {
            return new LiteralToken.Double(Double.parseDouble(rawLiteral));
        } else {
            return new LiteralToken.Int(Integer.parseInt(rawLiteral));
        }
    }

    /**
     * Construct the next character literal token.
     *
     * @return a LiteralToken.
     * @see LiteralToken.Char
     */
    private Token nextCharLiteralToken() throws BaseUserException {
        char rawLiteral = iter.next().getC();

        if (iter.next().getC() != '\'') {
            throw new ExpectedCharacterException("'", new FileLoc(-1, -1));
        }

        return new LiteralToken.Char(rawLiteral);
    }

    /**
     * Construct the next string literal token.
     *
     * @return a LiteralToken.
     * @see LiteralToken.String
     */
    private Token nextStringLiteralToken() throws BaseUserException {
        StringBuilder rawLiteralBuilder = new StringBuilder();
        while (iter.peek() != null && iter.peek().getC() != '"') {
            rawLiteralBuilder.append(iter.peek().getC());
            iter.next();
        }

        // TODO log expected location
        if (iter.peek() == null) {
            throw new ExpectedCharacterException("\"", new FileLoc(-1, -1));
        }
        iter.next();

        return new LiteralToken.String(rawLiteralBuilder.toString());
    }

    /**
     * Construct the next "word" token (identifier or keyword).
     * <p>
     * First constructs an identifier, then checks if the text is a keyword instead.
     * If the keyword is a boolean literal (true/false), return a boolean literal instead.
     *
     * @param firstChar the first character.
     * @return a LiteralToken, KeywordToken, or IdentifierToken.
     */
    private Token nextWordToken(char firstChar) throws BaseUserException {
        StringBuilder rawIdentifierBuilder = new StringBuilder(Character.toString(firstChar));

        while (iter.peek() != null && isIdentifierCharacter(iter.peek().getC())) {
            rawIdentifierBuilder.append(iter.next().getC());
        }

        String rawIdentifier = rawIdentifierBuilder.toString();

        KeywordTokenType t = KeywordTokenType.map.get(rawIdentifier);
        // boolean literals are a corner case
        // true/false are keywords, but the token is a LiteralToken.Boolean
        if (t != null) {
            if (t == KeywordTokenType.TRUE) {
                return new LiteralToken.Boolean(true);
            } else if (t == KeywordTokenType.FALSE) {
                return new LiteralToken.Boolean(false);
            }
            return new KeywordToken(t);
        }
        return new IdentifierToken(rawIdentifier);
    }

    /**
     * Construct the next symbol token.
     * <p>
     * Longer symbols have priority (e.g. >> would be identified instead of just >).
     *
     * @param firstChar the first character.
     * @return a SymbolToken, null if no valid symbol matches.
     */
    private Token nextSymbolToken(char firstChar) throws BaseUserException {
        String rawSymbol = Character.toString(firstChar);
        while (iter.peek() != null) {
            rawSymbol += iter.peek().getC();
            if (SymbolTokenType.map.containsKey(rawSymbol)) {
                iter.next();
            } else {
                rawSymbol = rawSymbol.substring(0, rawSymbol.length() - 1);
                break;
            }
        }

        SymbolTokenType t = SymbolTokenType.map.get(rawSymbol);
        return t != null ? new SymbolToken(t) : null;
    }
}
