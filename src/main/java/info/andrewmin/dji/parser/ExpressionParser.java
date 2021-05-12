package info.andrewmin.dji.parser;

import info.andrewmin.dji.Peekable;
import info.andrewmin.dji.ast.ExpressionNode;
import info.andrewmin.dji.exceptions.ExpectedCharacterException;
import info.andrewmin.dji.lexer.FileLoc;
import info.andrewmin.dji.tokens.*;

import java.util.ArrayList;
import java.util.List;

public class ExpressionParser {
    private final Peekable<Token> tokens;

    public ExpressionParser(Peekable<Token> tokens) {
        this.tokens = tokens;
    }

    public ExpressionNode parse() {
        return parseBinaryRightExpr(0, parseWithoutBinary());
    }

    private ExpressionNode parseWithoutBinary() {
        Token next = tokens.next();
        if (next instanceof LiteralToken) {
            return new ExpressionNode.Literal((LiteralToken<?>) next);
        } else if (next instanceof IdentifierToken) {
            String name = ((IdentifierToken) next).getIdentifier();

            if (tokens.peek().isSymbol(SymbolTokenType.LPAREN)) {
                tokens.next();
                List<ExpressionNode> args = new ArrayList<>();

                // Get argument expressions, unless the function call has no args
                if (!tokens.peek().isSymbol(SymbolTokenType.RPAREN)) {
                    while (true) {
                        args.add(parse());
                        if (tokens.peek().isSymbol(SymbolTokenType.RPAREN)) {
                            tokens.next();
                            break;
                        } else if (tokens.peek().isSymbol(SymbolTokenType.COMMA)) {
                            tokens.next();
                        } else {
                            throw new ExpectedCharacterException(") or ,", tokens.peek().getLoc());
                        }
                    }
                } else {
                    tokens.next();
                }

                return new ExpressionNode.FunctionCall(name, args);
            }
            return new ExpressionNode.VariableReference(name);
        } else if (next instanceof SymbolToken) {
            SymbolTokenType symbol = ((SymbolToken) next).getType();
            if (symbol == SymbolTokenType.LPAREN) {
                ExpressionNode node = parse();

                // eat closing RPAREN
                if (tokens.hasNext()) {
                    Token rparen = tokens.next();
                    if (!rparen.isSymbol(SymbolTokenType.RPAREN)) {
                        throw new ExpectedCharacterException(")", rparen.getLoc());
                    }
                } else {
                    throw new ExpectedCharacterException(")", new FileLoc(-1, -1));
                }

                return node;
            } else if (SymbolTokenType.unaryOps.contains(symbol)) {
                return new ExpressionNode.Unary(symbol, parseWithoutBinary());
            }
        }
        return null;
    }

    private ExpressionNode parseBinaryRightExpr(int prevPrecedence, ExpressionNode leftExpr) {
        while (true) {
            Token peek = tokens.peek();
            if (!(peek instanceof SymbolToken)) {
                return leftExpr;
            }

            SymbolTokenType op = ((SymbolToken) peek).getType();
            int precedence = SymbolTokenType.binaryOpPrecedence(op);

            if (precedence < prevPrecedence) {
                return leftExpr;
            }

            tokens.next(); // eat operator token

            ExpressionNode rightExpr = parseWithoutBinary();

            peek = tokens.peek();
            int nextPrecedence = (peek instanceof SymbolToken)
                    ? SymbolTokenType.binaryOpPrecedence(((SymbolToken) peek).getType())
                    : -1;

            if (precedence < nextPrecedence) {
                rightExpr = parseBinaryRightExpr(precedence + 1, rightExpr);
            }

            leftExpr = new ExpressionNode.Binary(op, leftExpr, rightExpr);
        }
    }
}
