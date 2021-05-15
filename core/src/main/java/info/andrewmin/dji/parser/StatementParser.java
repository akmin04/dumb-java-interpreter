package info.andrewmin.dji.parser;

import info.andrewmin.dji.ast.ExpressionNode;
import info.andrewmin.dji.ast.StatementNode;
import info.andrewmin.dji.lexer.Lexer;
import info.andrewmin.dji.tokens.*;

final class StatementParser {
    private final Lexer lexer;
    private final ExpressionParser expressionParser;

    StatementParser(Lexer lexer) {
        this.lexer = lexer;
        this.expressionParser = new ExpressionParser(lexer);
    }

    StatementNode parse() {
        Token peek = lexer.peek();
        if (peek instanceof TypeToken) {
            TypeTokenVariant type = lexer.nextType().getType();
            String var = lexer.nextIdentifier().getIdentifier();
            ExpressionNode expr;

            if (lexer.peek().isSymbol(SymbolTokenVariant.ASSIGN)) {
                lexer.next();
                expr = expressionParser.parse();
            } else {
                expr = new ExpressionNode.Literal(TypeTokenVariant.defaultValue(type));
            }

            lexer.next(SymbolTokenVariant.SEMICOLON);

            return new StatementNode.VariableDeclaration(var, expr);
        } else if (peek.isKeyword(KeywordTokenVariant.IF)) {
            lexer.next();
            // TODO
        } else if (peek.isKeyword(KeywordTokenVariant.RETURN)) {
            lexer.next();
            ExpressionNode expr = expressionParser.parse();
            lexer.next(SymbolTokenVariant.SEMICOLON);
            return new StatementNode.Return(expr);
        } else if (peek.isSymbol(SymbolTokenVariant.SEMICOLON)) {
            lexer.next();
            return parse();
        }

        ExpressionNode expr = expressionParser.parse();
        lexer.next(SymbolTokenVariant.SEMICOLON);
        return new StatementNode.Expression(expr);
    }
}
