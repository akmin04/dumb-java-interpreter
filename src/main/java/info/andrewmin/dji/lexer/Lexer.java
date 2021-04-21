package info.andrewmin.dji.lexer;

import info.andrewmin.dji.Logger;

import java.util.Iterator;

/**
 * A lexical analyzer that splits a given program into Tokens.
 *
 * @see Token
 * @see FileCharIterator
 */
public class Lexer implements Iterator<Token<?>> {

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
    public Token<?> next() {
        // Get the first non-whitespace character
        // Also checks for the end of file
        FileChar firstChar = new FileChar(' ');
        while (Character.isWhitespace(firstChar.getC())) {
            firstChar = iter.next();
            if (firstChar == null) {
                return new Token<Void>(TokenType.EOF);
            }
        }

        // If token starts with a digit, create a double/int literal
        if (Character.isDigit(firstChar.getC())) {
            StringBuilder rawLiteralBuilder = new StringBuilder(Character.toString(firstChar.getC()));

            // Keep peeking next digits or decimal points (only allow one decimal point)
            boolean hasDecimal = false;

            while (iter.peek() != null
                    && (Character.isDigit(iter.peek().getC()) || iter.peek().getC() == '.' && !hasDecimal)) {
                hasDecimal = hasDecimal || iter.peek().getC() == '.';
                rawLiteralBuilder.append(iter.next().getC());
            }

            String rawLiteral = rawLiteralBuilder.toString();
            if (hasDecimal) {
                return new Token<>(TokenType.DOUBLE, Double.parseDouble(rawLiteral));
            } else {
                return new Token<>(TokenType.INT, Integer.parseInt(rawLiteral));
            }
        }

        // Else, identify the symbol token (longer tokens have higher priority)
        else {
            String rawSymbol = Character.toString(firstChar.getC());
            while (iter.peek() != null) {
                rawSymbol += iter.peek().getC();
                if (TokenType.symbolMap.containsKey(rawSymbol)) {
                    iter.next();
                } else {
                    rawSymbol = rawSymbol.substring(0, rawSymbol.length() - 1);
                    break;
                }
            }

            TokenType t = TokenType.symbolMap.get(rawSymbol);
            if (t != null) {
                return new Token<>(t);
            }

            Logger.getDefault().error(this, "Invalid token " + firstChar);
            return null;
        }
    }
}
