package info.andrewmin.dji.core.parser;

import info.andrewmin.dji.core.ast.FunctionNode;
import info.andrewmin.dji.core.ast.ProgramNode;
import info.andrewmin.dji.core.lexer.Lexer;

import java.util.HashMap;
import java.util.Map;

/**
 * A program node parser.
 * <p>
 * Parses tokens into a full abstract syntax tree and calls any sub-parsers.
 *
 * @see FunctionParser
 * @see StatementParser
 * @see ExpressionParser
 */
public final class ProgramParser {
    private final Lexer lexer;
    private final FunctionParser functionParser;

    /**
     * Construct a new program node parser.
     *
     * @param lexer The lexer.
     */
    public ProgramParser(Lexer lexer) {
        this.lexer = lexer;
        this.functionParser = new FunctionParser(lexer);
    }

    /**
     * Parse the entire program abstract syntax tree.
     *
     * @return The entire program abstract syntax tree.
     */
    public ProgramNode parse() {
        Map<String, FunctionNode> functions = new HashMap<>();
        while (lexer.hasNext()) {
            FunctionNode function = functionParser.parse();
            functions.put(function.getName(), function);
        }
        return new ProgramNode(functions);
    }
}
