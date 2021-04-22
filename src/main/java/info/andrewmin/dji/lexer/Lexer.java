package info.andrewmin.dji.lexer;

import info.andrewmin.dji.Logger;
import info.andrewmin.dji.tokens.LiteralToken;
import info.andrewmin.dji.tokens.SymbolToken;
import info.andrewmin.dji.tokens.SymbolTokenType;
import info.andrewmin.dji.tokens.Token;

import java.util.Iterator;

/**
 * A lexical analyzer that splits a given program into Tokens.
 *
 * @see Token
 * @see FileCharIterator
 */
public class Lexer implements Iterator<Token> {

    private final FileCharIterator iter;

    /**
     * Initiailize a Lexer from a file name.
     *
     * @param fileName the file name.
     */
    public Lexer(String fileName) {
        this.iter = new FileCharIterator(fileName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext() {
        return iter.peek() != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Token next() {
        // Get the first non-whitespace character
        FileChar firstFileChar = new FileChar(' ');
        while (Character.isWhitespace(firstFileChar.getC())) {
            firstFileChar = iter.next();
            if (firstFileChar == null) {
                Logger.getDefault().fatal(this, "Attempted to get next token, but at the end.");
                return null;
            }
        }

        Token t;
        if (Character.isDigit(firstFileChar.getC())) {
            t = nextNumberLiteralToken(firstFileChar.getC());
        } else if (firstFileChar.getC() == '\'') {
            t = nextCharLiteralToken();
        } else if (firstFileChar.getC() == '"') {
            t = nextStringLiteralToken();
        } else {
            t = nextSymbolToken(firstFileChar.getC());
        }

        if (t == null) {
            Logger.getDefault().error(this, "Invalid token " + firstFileChar);
            return null;
        }
        return t;
    }

    private Token nextNumberLiteralToken(char firstChar) {
        StringBuilder rawLiteralBuilder = new StringBuilder(Character.toString(firstChar));

        // Keep peeking next digits or decimal points (only allow one decimal point)
        boolean hasDecimal = false;

        while (iter.peek() != null
                && (Character.isDigit(iter.peek().getC()) || iter.peek().getC() == '.' && !hasDecimal)) {
            hasDecimal = hasDecimal || iter.peek().getC() == '.';
            rawLiteralBuilder.append(iter.next().getC());
        }

        String rawLiteral = rawLiteralBuilder.toString();

        // TODO try/catch out of range
        if (hasDecimal) {
            return new LiteralToken.Double(Double.parseDouble(rawLiteral));
        } else {
            return new LiteralToken.Int(Integer.parseInt(rawLiteral));
        }
    }

    private Token nextCharLiteralToken() {
        char rawLiteral = iter.next().getC();

        if (iter.next().getC() != '\'') {
            Logger.getDefault().error(this, "Expected '");
        }

        return new LiteralToken.Char(rawLiteral);
    }

    private Token nextStringLiteralToken() {
        StringBuilder rawLiteralBuilder = new StringBuilder();
        while (iter.peek() != null && iter.peek().getC() != '"') {
            rawLiteralBuilder.append(iter.peek().getC());
            iter.next();
        }

        // TODO log expected location
        if (iter.peek() == null) {
            Logger.getDefault().error(this, "Expected \"");
        }
        iter.next();

        return new LiteralToken.String(rawLiteralBuilder.toString());
    }

    private Token nextSymbolToken(char firstChar) {
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
