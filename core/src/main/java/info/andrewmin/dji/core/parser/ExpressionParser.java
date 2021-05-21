package info.andrewmin.dji.core.parser;

import info.andrewmin.dji.core.ast.ExpressionNode;
import info.andrewmin.dji.core.exceptions.ExpectedEntityException;
import info.andrewmin.dji.core.exceptions.UnexpectedCharacterException;
import info.andrewmin.dji.core.lexer.Lexer;
import info.andrewmin.dji.core.runtime.Value;
import info.andrewmin.dji.core.tokens.*;

import java.util.ArrayList;
import java.util.List;

/**
 * An expression node parser.
 */
final class ExpressionParser {
    private final Lexer lexer;

    /**
     * Construct a new expression node parser.
     *
     * @param lexer The lexer.
     */
    ExpressionParser(Lexer lexer) {
        this.lexer = lexer;
    }

    /**
     * Parse the next expression.
     *
     * @return The next expression.
     */
    ExpressionNode parse() {
        if (!lexer.hasNext()) {
            throw new ExpectedEntityException("an expression", lexer.current().getEndLoc());
        }
        return parseBinaryRightExpr(0, parseWithoutBinary());
    }

    /**
     * Parse the next expression ignoring binary operations.
     *
     * @return The next expression.
     */
    private ExpressionNode parseWithoutBinary() {
        Token next = lexer.next();

        // Literal
        if (next instanceof LiteralToken) {
            return new ExpressionNode.Literal(Value.fromToken((LiteralToken<?>) next));
        }
        // VariableReference or FunctionCall
        else if (next instanceof IdentifierToken) {
            String name = ((IdentifierToken) next).getIdentifier();

            if (lexer.hasNext() && lexer.peek().isSymbol(SymbolTokenVariant.LPAREN)) {
                lexer.next();
                List<ExpressionNode> args = new ArrayList<>();

                // Get argument expressions, unless the function call has no args
                if (!lexer.hasNext()) {
                    throw new ExpectedEntityException(")", lexer.current().getEndLoc());
                } else if (lexer.peek().isSymbol(SymbolTokenVariant.RPAREN)) {
                    lexer.next();
                } else {
                    while (true) {
                        args.add(parse());
                        if (!lexer.hasNext()) {
                            throw new ExpectedEntityException(") or ,", lexer.current().getEndLoc());
                        } else if (lexer.peek().isSymbol(SymbolTokenVariant.RPAREN)) {
                            lexer.next();
                            break;
                        } else if (lexer.peek().isSymbol(SymbolTokenVariant.COMMA)) {
                            lexer.next();
                        } else {
                            throw new UnexpectedCharacterException(lexer.peek());
                        }
                    }
                }

                return new ExpressionNode.FunctionCall(name, args);
            }
            return new ExpressionNode.VariableReference(name);
        }
        // Unary
        else if (next instanceof SymbolToken && SymbolTokenVariant.unaryOps.contains(((SymbolToken) next).getVariant())) {
            return new ExpressionNode.Unary(((SymbolToken) next).getVariant(), parseWithoutBinary());
        }
        // Parenthesis
        else if (next.isSymbol(SymbolTokenVariant.LPAREN)) {
            ExpressionNode node = parse();
            lexer.next(SymbolTokenVariant.RPAREN);
            return node;
        }

        throw new UnexpectedCharacterException(next);
    }

    /**
     * Parse any binary operations using the shunting-yard algorithm.
     *
     * @param prevPrecedence The previous binary operation precedence
     * @param leftExpr       The previously parsed expression node.
     * @return The full expression.
     */
    private ExpressionNode parseBinaryRightExpr(int prevPrecedence, ExpressionNode leftExpr) {
        while (true) {
            Token peek = lexer.peek();
            if (!(peek instanceof SymbolToken && SymbolTokenVariant.binaryOps.contains(((SymbolToken) peek).getVariant()))) {
                return leftExpr;
            }

            SymbolTokenVariant op = ((SymbolToken) peek).getVariant();
            int precedence = SymbolTokenVariant.binaryOpPrecedence(op);

            if (precedence < prevPrecedence) {
                return leftExpr;
            }

            lexer.next(); // eat operator token

            ExpressionNode rightExpr = parseWithoutBinary();

            peek = lexer.peek();
            int nextPrecedence = (peek instanceof SymbolToken)
                    ? SymbolTokenVariant.binaryOpPrecedence(((SymbolToken) peek).getVariant())
                    : -1;

            if (precedence < nextPrecedence) {
                rightExpr = parseBinaryRightExpr(precedence + 1, rightExpr);
            }

            leftExpr = new ExpressionNode.Binary(op, leftExpr, rightExpr);
        }
    }
}
