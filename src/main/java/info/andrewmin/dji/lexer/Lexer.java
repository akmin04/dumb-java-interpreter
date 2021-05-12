package info.andrewmin.dji.lexer;

import info.andrewmin.dji.Peekable;
import info.andrewmin.dji.exceptions.ExpectedCharacterException;
import info.andrewmin.dji.exceptions.InternalCompilerException;
import info.andrewmin.dji.exceptions.InvalidNumberException;
import info.andrewmin.dji.exceptions.InvalidTokenException;
import info.andrewmin.dji.tokens.*;

import java.util.Iterator;

/**
 * A lexical analyzer that splits a given program into Tokens.
 *
 * @see Token
 * @see FileCharIterator
 */
public class Lexer implements Iterator<Token> {

    private final Peekable<FileChar> chars;

    /**
     * Initiailize a Lexer from a file name.
     *
     * @param iter the FileCharIterator
     */
    public Lexer(FileCharIterator iter) {
        this.chars = new Peekable<>(iter);
    }

    /**
     * Whether or not more tokens exist.
     *
     * @return if the end of the file has been reached or not.
     */
    @Override
    public boolean hasNext() {
        return chars.hasNext();
    }

    /**
     * Get the next Token.
     *
     * @return the next Token.
     */
    @Override
    public Token next() {
        // Get the first non-whitespace character
        FileChar firstFileChar = new FileChar(' ');
        while (Character.isWhitespace(firstFileChar.getC())) {
            if (!chars.hasNext()) {
                throw new InternalCompilerException("Attempted to get next token, but at the end.");
            }
            firstFileChar = chars.next();
        }

        Token t;
        if (Character.isDigit(firstFileChar.getC())) {
            t = nextNumberLiteralToken(firstFileChar);
        } else if (firstFileChar.getC() == '\'') {
            t = nextCharLiteralToken(firstFileChar);
        } else if (firstFileChar.getC() == '"') {
            t = nextStringLiteralToken(firstFileChar);
        } else if (isIdentifierCharacter(firstFileChar.getC())) {
            t = nextWordToken(firstFileChar);
        } else {
            t = nextSymbolToken(firstFileChar);
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
    private Token nextNumberLiteralToken(FileChar firstFileChar) {
        StringBuilder rawLiteralBuilder = new StringBuilder(Character.toString(firstFileChar.getC()));

        // Keep peeking next digits or decimal points (only allow one decimal point)
        boolean hasDecimal = false;

        while (chars.peek() != null
                && (Character.isDigit(chars.peek().getC()) || chars.peek().getC() == '.' && !hasDecimal)) {
            hasDecimal = hasDecimal || chars.peek().getC() == '.';
            rawLiteralBuilder.append(chars.next().getC());
        }

        String rawLiteral = rawLiteralBuilder.toString();

        try {
            if (hasDecimal) {
                return new LiteralToken.Double(firstFileChar.getLoc(), Double.parseDouble(rawLiteral));
            } else {
                return new LiteralToken.Int(firstFileChar.getLoc(), Integer.parseInt(rawLiteral));
            }
        } catch (NumberFormatException e) {
            throw new InvalidNumberException(firstFileChar, rawLiteral);
        }
    }

    /**
     * Construct the next character literal token.
     *
     * @return a LiteralToken.
     * @see LiteralToken.Char
     */
    private Token nextCharLiteralToken(FileChar firstFileChar) {
        if (!chars.hasNext()) {
            throw new ExpectedCharacterException("a char", new FileLoc(-1, -1));
        }
        char rawLiteral = chars.next().getC();
        if (chars.next().getC() != '\'') {
            throw new ExpectedCharacterException("'", new FileLoc(-1, -1));
        }

        return new LiteralToken.Char(firstFileChar.getLoc(), rawLiteral);
    }

    /**
     * Construct the next string literal token.
     *
     * @return a LiteralToken.
     * @see LiteralToken.String
     */
    private Token nextStringLiteralToken(FileChar firstFileChar) {
        StringBuilder rawLiteralBuilder = new StringBuilder();
        while (chars.peek() != null && chars.peek().getC() != '"') {
            rawLiteralBuilder.append(chars.peek().getC());
            chars.next();
        }

        // TODO log expected location
        if (chars.peek() == null) {
            throw new ExpectedCharacterException("\"", new FileLoc(-1, -1));
        }
        chars.next();

        return new LiteralToken.String(firstFileChar.getLoc(), rawLiteralBuilder.toString());
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
    private Token nextWordToken(FileChar firstFileChar) {
        StringBuilder rawIdentifierBuilder = new StringBuilder(Character.toString(firstFileChar.getC()));

        while (chars.hasNext() && isIdentifierCharacter(chars.peek().getC())) {
            rawIdentifierBuilder.append(chars.next().getC());
        }

        String rawIdentifier = rawIdentifierBuilder.toString();

        KeywordTokenType t = KeywordTokenType.map.get(rawIdentifier);
        // boolean literals are an exception
        // true/false are keywords, but the token is a LiteralToken.Boolean
        if (t != null) {
            if (t == KeywordTokenType.TRUE) {
                return new LiteralToken.Boolean(firstFileChar.getLoc(), true);
            } else if (t == KeywordTokenType.FALSE) {
                return new LiteralToken.Boolean(firstFileChar.getLoc(), false);
            }
            return new KeywordToken(firstFileChar.getLoc(), t);
        }
        return new IdentifierToken(firstFileChar.getLoc(), rawIdentifier);
    }

    /**
     * Construct the next symbol token.
     * <p>
     * Longer symbols have priority (e.g. >> would be identified instead of just >).
     *
     * @param firstChar the first character.
     * @return a SymbolToken, null if no valid symbol matches.
     */
    private Token nextSymbolToken(FileChar firstFileChar) {
        String rawSymbol = Character.toString(firstFileChar.getC());
        while (chars.hasNext()) {
            rawSymbol += chars.peek().getC();
            if (SymbolTokenType.map.containsKey(rawSymbol)) {
                chars.next();
            } else {
                rawSymbol = rawSymbol.substring(0, rawSymbol.length() - 1);
                break;
            }
        }

        SymbolTokenType t = SymbolTokenType.map.get(rawSymbol);
        return t != null ? new SymbolToken(firstFileChar.getLoc(), t) : null;
    }
}