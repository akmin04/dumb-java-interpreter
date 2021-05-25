package info.andrewmin.dji.core.parser;

import info.andrewmin.dji.core.ast.FunctionNode;
import info.andrewmin.dji.core.ast.StatementNode;
import info.andrewmin.dji.core.exceptions.ExpectedEntityException;
import info.andrewmin.dji.core.exceptions.UnexpectedCharacterException;
import info.andrewmin.dji.core.lexer.FileLoc;
import info.andrewmin.dji.core.lexer.Lexer;
import info.andrewmin.dji.core.runtime.Var;
import info.andrewmin.dji.core.tokens.SymbolTokenVariant;
import info.andrewmin.dji.core.tokens.TypeTokenVariant;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * A function node parser.
 */
final class FunctionParser {
    private static final Logger LOGGER = Logger.getLogger(FunctionParser.class.getName());

    private final Lexer lexer;
    private final StatementParser statementParser;

    /**
     * Construct a new function node parser.
     *
     * @param lexer The lexer.
     */
    FunctionParser(Lexer lexer) {
        this.lexer = lexer;
        this.statementParser = new StatementParser(lexer);
    }

    /**
     * Parse the next function with its name, return type, parameters, and body.
     *
     * @return The next function.
     */
    FunctionNode parse() {
        LOGGER.fine("Parsing function");
        TypeTokenVariant type = lexer.nextType().getVariant();
        String func = lexer.nextIdentifier().getIdentifier();
        LOGGER.fine("Name: " + func);
        lexer.next(SymbolTokenVariant.LPAREN);

        List<Var> parameters = new ArrayList<>();
        if (!lexer.hasNext()) {
            throw new ExpectedEntityException(")", lexer.current().getEndLoc());
        } else if (lexer.peek().isSymbol(SymbolTokenVariant.RPAREN)) {
            lexer.next();
        } else {
            while (true) {
                Var parameter = new Var(
                        lexer.nextType().getVariant(),
                        lexer.nextIdentifier().getIdentifier()
                );
                parameters.add(parameter);
                LOGGER.fine("Parameter: " + parameter);

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

        LOGGER.info("Parsed function " + func);
        return new FunctionNode(func, type, parameters, (StatementNode.Block) statement);
    }

}
