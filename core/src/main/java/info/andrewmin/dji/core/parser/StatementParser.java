package info.andrewmin.dji.core.parser;

import info.andrewmin.dji.core.ast.ExpressionNode;
import info.andrewmin.dji.core.ast.StatementNode;
import info.andrewmin.dji.core.exceptions.ExpectedEntityException;
import info.andrewmin.dji.core.lexer.Lexer;
import info.andrewmin.dji.core.tokens.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * A statement node parser.
 */
final class StatementParser {
    private static final Logger LOGGER = Logger.getLogger(StatementParser.class.getName());

    private final Lexer lexer;
    private final ExpressionParser expressionParser;

    /**
     * Construct a new statement node parser.
     *
     * @param lexer The lexer.
     */
    StatementParser(Lexer lexer) {
        this.lexer = lexer;
        this.expressionParser = new ExpressionParser(lexer);
    }

    /**
     * Parse the next statement.
     *
     * @return The next statement.
     */
    StatementNode parse() {
        Token peek = lexer.peek();
        // Block
        if (peek.isSymbol(SymbolTokenVariant.LBRACE)) {
            LOGGER.fine("Block statement");
            lexer.next();
            List<StatementNode> statements = new ArrayList<>();
            while (true) {
                statements.add(parse());
                if (!lexer.hasNext()) {
                    throw new ExpectedEntityException("}", lexer.current().getEndLoc());
                } else if (lexer.peek().isSymbol(SymbolTokenVariant.RBRACE)) {
                    lexer.next();
                    break;
                }
            }

            return new StatementNode.Block(statements);
        }
        // VariableDeclaration
        else if (peek instanceof TypeToken) {
            LOGGER.fine("Variable declaration statement");
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
        }
        // If
        else if (peek.isKeyword(KeywordTokenVariant.IF)) {
            LOGGER.fine("If statement");
            lexer.next();
            lexer.next(SymbolTokenVariant.LPAREN);
            ExpressionNode condition = expressionParser.parse();
            lexer.next(SymbolTokenVariant.RPAREN);
            StatementNode body = parse();
            StatementNode _else = null;

            if (lexer.peek().isKeyword(KeywordTokenVariant.ELSE)) {
                lexer.next();
                _else = parse();
            }

            return new StatementNode.If(condition, body, _else);
        }
        // For
        else if (peek.isKeyword(KeywordTokenVariant.FOR)) {
            LOGGER.fine("For statement");
            lexer.next();
            lexer.next(SymbolTokenVariant.LPAREN);
            StatementNode init = parse();
            ExpressionNode condition = expressionParser.parse();
            lexer.next(SymbolTokenVariant.SEMICOLON);
            ExpressionNode post = expressionParser.parse();
            lexer.next(SymbolTokenVariant.RPAREN);
            StatementNode body = parse();

            return new StatementNode.For(init, condition, post, body);
        }
        // While
        else if (peek.isKeyword(KeywordTokenVariant.WHILE)) {
            LOGGER.fine("While statement");
            lexer.next();
            lexer.next(SymbolTokenVariant.LPAREN);
            ExpressionNode condition = expressionParser.parse();
            lexer.next(SymbolTokenVariant.RPAREN);
            StatementNode body = parse();

            return new StatementNode.While(condition, body);
        }
        // Break
        else if (peek.isKeyword(KeywordTokenVariant.BREAK)) {
            LOGGER.fine("Break statement");
            lexer.next();
            lexer.next(SymbolTokenVariant.SEMICOLON);

            return new StatementNode.Break();
        }
        // Continue
        else if (peek.isKeyword(KeywordTokenVariant.CONTINUE)) {
            LOGGER.fine("Continue statement");
            lexer.next();
            lexer.next(SymbolTokenVariant.SEMICOLON);

            return new StatementNode.Continue();
        }
        // Return
        else if (peek.isKeyword(KeywordTokenVariant.RETURN)) {
            LOGGER.fine("Return statement");
            lexer.next();
            ExpressionNode expr = expressionParser.parse();
            lexer.next(SymbolTokenVariant.SEMICOLON);

            return new StatementNode.Return(expr);
        }
        // No-op
        else if (peek.isSymbol(SymbolTokenVariant.SEMICOLON)) {
            LOGGER.fine("Semicolon statement");
            lexer.next();
            return parse();
        }
        // Expression
        LOGGER.fine("Expression statement");
        ExpressionNode expr = expressionParser.parse();
        lexer.next(SymbolTokenVariant.SEMICOLON);

        return new StatementNode.Expression(expr);
    }
}
