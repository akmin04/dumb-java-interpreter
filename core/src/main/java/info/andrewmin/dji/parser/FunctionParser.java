package info.andrewmin.dji.parser;

import info.andrewmin.dji.ast.FunctionNode;
import info.andrewmin.dji.ast.StatementNode;
import info.andrewmin.dji.exceptions.ExpectedCharacterException;
import info.andrewmin.dji.exceptions.UnexpectedCharacterException;
import info.andrewmin.dji.lexer.Lexer;
import info.andrewmin.dji.runtime.Var;
import info.andrewmin.dji.tokens.SymbolTokenVariant;
import info.andrewmin.dji.tokens.TypeTokenVariant;

import java.util.ArrayList;
import java.util.List;

final class FunctionParser {
    private final Lexer lexer;
    private final StatementParser statementParser;

    FunctionParser(Lexer lexer) {
        this.lexer = lexer;
        this.statementParser = new StatementParser(lexer);
    }

    FunctionNode parse() {
        TypeTokenVariant type = lexer.nextType().getType();
        String func = lexer.nextIdentifier().getIdentifier();
        lexer.next(SymbolTokenVariant.LPAREN);

        List<Var> parameters = new ArrayList<>();
        if (!lexer.hasNext()) {
            throw new ExpectedCharacterException(")", lexer.current().getEndLoc());
        } else if (lexer.peek().isSymbol(SymbolTokenVariant.RPAREN)) {
            lexer.next();
        } else {
            while (true) {
                parameters.add(new Var(
                        lexer.nextType().getType(),
                        lexer.nextIdentifier().getIdentifier()
                ));
                if (!lexer.hasNext()) {
                    throw new ExpectedCharacterException(") or ,", lexer.current().getEndLoc());
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

        lexer.next(SymbolTokenVariant.LBRACE);
        List<StatementNode> statements = new ArrayList<>();
        while (true) {
            statements.add(statementParser.parse());
            if (!lexer.hasNext()) {
                throw new ExpectedCharacterException("}", lexer.current().getEndLoc());
            } else if (lexer.peek().isSymbol(SymbolTokenVariant.RBRACE)) {
                lexer.next();
                break;
            }
        }
        return new FunctionNode(func, type, parameters, statements);
    }

}
