package info.andrewmin.dji.core.lexer;

import info.andrewmin.dji.core.exceptions.ExpectedEntityException;
import info.andrewmin.dji.core.exceptions.InvalidNumberException;
import info.andrewmin.dji.core.exceptions.InvalidTokenException;
import info.andrewmin.dji.core.exceptions.UnexpectedCharacterException;
import info.andrewmin.dji.core.tokens.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A lexical analyzer that splits a given program into tokens.
 *
 * @see Token
 * @see FileCharIterator
 */
public final class Lexer implements Iterator<Token> {
    private final FileCharIterator chars;
    private Token buffer;
    private Token current;

    /**
     * Construct a new lexer from a [FileCharIterator].
     *
     * @param iter The [FileCharIterator].
     */
    public Lexer(FileCharIterator iter) {
        this.chars = iter;
        updateBuffer();
        this.current = null;
    }

    /**
     * Check if more tokens exist or not.
     *
     * @return If more tokens exist.
     */
    @Override
    public boolean hasNext() {
        return buffer != null;
    }

    /**
     * Consume and return the next token.
     *
     * @return The next token.
     */
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

    /**
     * Get the next identifier token.
     *
     * @return The next identifier token
     * @see IdentifierToken
     */
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

    /**
     * Get the next keyword token.
     *
     * @return The next keyword token
     * @see KeywordToken
     */
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

    /**
     * Get the next symbol token.
     *
     * @return The next symbol token
     * @see SymbolToken
     */
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

    /**
     * Get the next type token.
     *
     * @return The next type token
     * @see TypeToken
     */
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

    /**
     * Consume the next keyword.
     *
     * @param keyword The keyword to expect.
     */
    public void next(KeywordTokenVariant keyword) {
        KeywordToken next = nextKeyword();
        if (!next.isKeyword(keyword)) {
            throw new UnexpectedCharacterException(next);
        }
    }

    /**
     * Consume the next symbol.
     *
     * @param symbol The symbol to expect.
     */
    public void next(SymbolTokenVariant symbol) {
        SymbolToken next = nextSymbol();
        if (!next.isSymbol(symbol)) {
            throw new UnexpectedCharacterException(next);
        }
    }

    /**
     * Get the next token without consuming it.
     *
     * @return The next token.
     */
    public Token peek() {
        if (buffer == null) {
            throw new NoSuchElementException();
        }
        return buffer;
    }

    /**
     * Get the current token (the most recently consumed).
     *
     * @return The current token.
     */
    public Token current() {
        return current;
    }

    /**
     * Read the next token and update the buffer.
     */
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
     * Checks if a character is a valid Java identifier character.
     * <p>
     * https://docs.oracle.com/cd/E19798-01/821-1841/bnbuk/index.html
     *
     * @param c The character.
     * @return If the character is a valid identifier.
     */
    private boolean isIdentifierCharacter(char c) {
        return Character.isAlphabetic(c) || Character.isDigit(c) || c == '_' || c == '$';
    }

    /**
     * Create the next number literal token (integer or double literal).
     *
     * @param firstFileChar The first character.
     * @return The next number literal token.
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
     * @param firstFileChar The first character.
     * @return The next character literal token.
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
     * @param firstFileChar The first character.
     * @return The next string literal token.
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
     * @param firstFileChar The first character.
     * @return The next keyword, identifier, or literal token.
     * @see KeywordToken
     * @see IdentifierToken
     * @see LiteralToken.Boolean
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
     *
     * @param firstFileChar The first character.
     * @return The next symbol token.
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

    /**
     * Get the end location of the previous token.
     *
     * @return The end location.
     */
    private FileLoc endLoc() {
        return chars.current().getLoc().addCol(1);
    }
}