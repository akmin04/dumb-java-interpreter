package info.andrewmin.dji.parser;

import info.andrewmin.dji.ast.FunctionNode;
import info.andrewmin.dji.ast.StatementNode;
import info.andrewmin.dji.exceptions.ExpectedEntityException;
import info.andrewmin.dji.exceptions.UnexpectedCharacterException;
import info.andrewmin.dji.lexer.FileLoc;
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
            throw new ExpectedEntityException(")", lexer.current().getEndLoc());
        } else if (lexer.peek().isSymbol(SymbolTokenVariant.RPAREN)) {
            lexer.next();
        } else {
            while (true) {
                parameters.add(new Var(
                        lexer.nextType().getType(),
                        lexer.nextIdentifier().getIdentifier()
                ));
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

        FileLoc loc = lexer.current().getEndLoc();
        StatementNode statement = statementParser.parse();
        if (!(statement instanceof StatementNode.Block)) {
            throw new ExpectedEntityException("a block", loc);
        }
        return new FunctionNode(func, type, parameters, (StatementNode.Block) statement);
    }

}
