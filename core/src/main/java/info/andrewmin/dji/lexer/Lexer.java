package info.andrewmin.dji.lexer;

import info.andrewmin.dji.exceptions.ExpectedEntityException;
import info.andrewmin.dji.exceptions.InvalidNumberException;
import info.andrewmin.dji.exceptions.InvalidTokenException;
import info.andrewmin.dji.exceptions.UnexpectedCharacterException;
import info.andrewmin.dji.tokens.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A lexical analyzer that splits a given program into Tokens.
 *
 * @see Token
 * @see FileCharIterator
 */
public class Lexer implements Iterator<Token> {

    private final FileCharIterator chars;
    private Token buffer;
    private Token current;

    /**
     * Initiailize a Lexer from a file name.
     *
     * @param iter the FileCharIterator
     */
    public Lexer(FileCharIterator iter) {
        this.chars = iter;
        updateBuffer();
        this.current = null;
    }

    @Override
    public boolean hasNext() {
        return buffer != null;
    }

    @Override
    public Token next() {
        if (!chars.hasNext() && buffer == null) {
            throw new NoSuchElementException();
        }
        Token next = buffer;
        current = buffer;
        updateBuffer();
        return next;
    }

    public IdentifierToken nextIdentifier() {
        if (!hasNext()) {
            throw new ExpectedEntityException("an identifier", current().getEndLoc());
        }
        Token next = next();
        if (!(next instanceof IdentifierToken)) {
            throw new UnexpectedCharacterException(next);
        }
        return (IdentifierToken) next;
    }

    public KeywordToken nextKeyword() {
        if (!hasNext()) {
            throw new ExpectedEntityException("a keyword", current().getEndLoc());
        }
        Token next = next();
        if (!(next instanceof KeywordToken)) {
            throw new UnexpectedCharacterException(next);
        }
        return (KeywordToken) next;
    }

    public SymbolToken nextSymbol() {
        if (!hasNext()) {
            throw new ExpectedEntityException("a symbol", current().getEndLoc());
        }
        Token next = next();
        if (!(next instanceof SymbolToken)) {
            throw new UnexpectedCharacterException(next);
        }
        return (SymbolToken) next;
    }

    public TypeToken nextType() {
        if (!hasNext()) {
            throw new ExpectedEntityException("a type", current().getEndLoc());
        }
        Token next = next();
        if (!(next instanceof TypeToken)) {
            System.err.println(next);
            throw new UnexpectedCharacterException(next);
        }
        return (TypeToken) next;
    }

    public void next(KeywordTokenVariant keyword) {
        KeywordToken next = nextKeyword();
        if (!next.isKeyword(keyword)) {
            throw new UnexpectedCharacterException(next);
        }
    }

    public void next(SymbolTokenVariant symbol) {
        SymbolToken next = nextSymbol();
        if (!next.isSymbol(symbol)) {
            throw new UnexpectedCharacterException(next);
        }
    }

    public Token peek() {
        if (buffer == null) {
            throw new NoSuchElementException();
        }
        return buffer;
    }

    public Token current() {
        return current;
    }

    private void updateBuffer() {
        // Get the first non-whitespace character
        FileChar firstFileChar = new FileChar(' ');
        while (Character.isWhitespace(firstFileChar.getC())) {
            if (!chars.hasNext()) {
                buffer = null;
                return;
            }
            firstFileChar = chars.next();
        }

        if (Character.isDigit(firstFileChar.getC())) {
            buffer = nextNumberLiteralToken(firstFileChar);
        } else if (firstFileChar.getC() == '\'') {
            buffer = nextCharLiteralToken(firstFileChar);
        } else if (firstFileChar.getC() == '"') {
            buffer = nextStringLiteralToken(firstFileChar);
        } else if (isIdentifierCharacter(firstFileChar.getC())) {
            buffer = nextWordToken(firstFileChar);
        } else {
            buffer = nextSymbolToken(firstFileChar);
        }

        if (buffer == null) {
            throw new InvalidTokenException(firstFileChar);
        }
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
                return new LiteralToken.Double(firstFileChar.getLoc(), endLoc(), Double.parseDouble(rawLiteral));
            } else {
                return new LiteralToken.Int(firstFileChar.getLoc(), endLoc(), Integer.parseInt(rawLiteral));
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
            throw new ExpectedEntityException("a char", endLoc());
        }
        char rawLiteral = chars.next().getC();
        if (!chars.hasNext()) {
            throw new ExpectedEntityException("'", endLoc());
        }
        if (chars.next().getC() != '\'') {
            throw new UnexpectedCharacterException(Character.toString(chars.current().getC()), chars.current().getLoc());
        }

        return new LiteralToken.Char(firstFileChar.getLoc(), endLoc(), rawLiteral);
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

        if (chars.peek() == null) {
            throw new ExpectedEntityException("\"", endLoc());
        }
        chars.next();

        return new LiteralToken.String(firstFileChar.getLoc(), endLoc(), rawLiteralBuilder.toString());
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

        KeywordTokenVariant keyword = KeywordTokenVariant.map.get(rawIdentifier);
        // boolean literals are an exception
        // true/false are keywords, but the token is a LiteralToken.Boolean
        if (keyword != null) {
            if (keyword == KeywordTokenVariant.TRUE) {
                return new LiteralToken.Boolean(firstFileChar.getLoc(), endLoc(), true);
            } else if (keyword == KeywordTokenVariant.FALSE) {
                return new LiteralToken.Boolean(firstFileChar.getLoc(), endLoc(), false);
            }
            return new KeywordToken(firstFileChar.getLoc(), endLoc(), keyword);
        }

        TypeTokenVariant type = TypeTokenVariant.map.get(rawIdentifier);
        if (type != null) {
            return new TypeToken(firstFileChar.getLoc(), endLoc(), type);
        }

        return new IdentifierToken(firstFileChar.getLoc(), endLoc(), rawIdentifier);
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
            if (SymbolTokenVariant.map.containsKey(rawSymbol)) {
                chars.next();
            } else {
                rawSymbol = rawSymbol.substring(0, rawSymbol.length() - 1);
                break;
            }
        }

        SymbolTokenVariant t = SymbolTokenVariant.map.get(rawSymbol);
        return t != null ? new SymbolToken(firstFileChar.getLoc(), endLoc(), t) : null;
    }

    private FileLoc endLoc() {
        return chars.current().getLoc().addCol(1);
    }
}