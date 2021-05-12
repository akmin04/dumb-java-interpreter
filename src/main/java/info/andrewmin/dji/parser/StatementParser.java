package info.andrewmin.dji.parser;

import info.andrewmin.dji.Peekable;
import info.andrewmin.dji.ast.ExpressionNode;
import info.andrewmin.dji.ast.StatementNode;
import info.andrewmin.dji.exceptions.ExpectedCharacterException;
import info.andrewmin.dji.lexer.Lexer;
import info.andrewmin.dji.tokens.*;

import java.util.ArrayList;
import java.util.List;

public class StatementParser {
    private final Peekable<Token> tokens;
    private final ExpressionParser expressionParser;

    public StatementParser(Lexer lexer) {
        this.tokens = new Peekable<>(lexer);
        this.expressionParser = new ExpressionParser(tokens);
    }

    public StatementNode parse() {
        Token peek = tokens.peek();
        if (peek.isSymbol(SymbolTokenType.LBRACE)) {
            tokens.next();
            List<StatementNode> statements = new ArrayList<>();
            while (!tokens.peek().isSymbol(SymbolTokenType.RBRACE)) {
                statements.add(parse());
            }

            return new StatementNode.Compound(statements);
        } else if (peek instanceof KeywordToken && KeywordTokenType.primitives.contains(((KeywordToken) peek).getType())) {
            KeywordTokenType type = ((KeywordToken) peek).getType();
            tokens.next();
            if (!(tokens.peek() instanceof IdentifierToken)) {
                throw new ExpectedCharacterException("an identifier", tokens.peek().getLoc());
            }

            String var = ((IdentifierToken) tokens.next()).getIdentifier();
            ExpressionNode expr;

            if (tokens.peek().isSymbol(SymbolTokenType.ASSIGN)) {
                tokens.next();
                expr = expressionParser.parse();
            } else {
                expr = new ExpressionNode.Literal(KeywordTokenType.defaultLiteral(type));
            }

            Token semicolon = tokens.next();
            if (!semicolon.isSymbol(SymbolTokenType.SEMICOLON)) {
                throw new ExpectedCharacterException(";", semicolon.getLoc());
            }

            return new StatementNode.VariableDeclaration(var, expr);

        } else if (peek.isSymbol(SymbolTokenType.SEMICOLON)) {
            tokens.next();

            return parse();
        }

        ExpressionNode expr = expressionParser.parse();

        Token semicolon = tokens.next();
        if (!semicolon.isSymbol(SymbolTokenType.SEMICOLON)) {
            throw new ExpectedCharacterException(";", semicolon.getLoc());
        }

        return new StatementNode.Expression(expr);
    }
}
