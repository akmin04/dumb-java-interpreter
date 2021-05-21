package info.andrewmin.dji.parser;

import info.andrewmin.dji.ast.ExpressionNode;
import info.andrewmin.dji.exceptions.ExpectedEntityException;
import info.andrewmin.dji.exceptions.UnexpectedCharacterException;
import info.andrewmin.dji.lexer.Lexer;
import info.andrewmin.dji.runtime.Value;
import info.andrewmin.dji.tokens.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Parse expression AST nodes.
 */
final class ExpressionParser {

    private final Lexer lexer;

    ExpressionParser(Lexer lexer) {
        this.lexer = lexer;
    }

    /**
     * Parse the next expression.
     *
     * @return an expression AST node.
     */
    ExpressionNode parse() {
        if (!lexer.hasNext()) {
            throw new ExpectedEntityException("an expression", lexer.current().getEndLoc());
        }
        return parseBinaryRightExpr(0, parseWithoutBinary());
    }

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
        else if (next instanceof SymbolToken && SymbolTokenVariant.unaryOps.contains(((SymbolToken) next).getType())) {
            return new ExpressionNode.Unary(((SymbolToken) next).getType(), parseWithoutBinary());
        }
        // Parenthesis
        else if (next.isSymbol(SymbolTokenVariant.LPAREN)) {
            ExpressionNode node = parse();
            lexer.next(SymbolTokenVariant.RPAREN);
            return node;
        }

        throw new UnexpectedCharacterException(next);
    }

    private ExpressionNode parseBinaryRightExpr(int prevPrecedence, ExpressionNode leftExpr) {
        while (true) {
            Token peek = lexer.peek();
            if (!(peek instanceof SymbolToken && SymbolTokenVariant.binaryOps.contains(((SymbolToken) peek).getType()))) {
                return leftExpr;
            }

            SymbolTokenVariant op = ((SymbolToken) peek).getType();
            int precedence = SymbolTokenVariant.binaryOpPrecedence(op);

            if (precedence < prevPrecedence) {
                return leftExpr;
            }

            lexer.next(); // eat operator token

            ExpressionNode rightExpr = parseWithoutBinary();

            peek = lexer.peek();
            int nextPrecedence = (peek instanceof SymbolToken)
                    ? SymbolTokenVariant.binaryOpPrecedence(((SymbolToken) peek).getType())
                    : -1;

            if (precedence < nextPrecedence) {
                rightExpr = parseBinaryRightExpr(precedence + 1, rightExpr);
            }

            leftExpr = new ExpressionNode.Binary(op, leftExpr, rightExpr);
        }
    }
}
