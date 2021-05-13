package info.andrewmin.dji.parser;

import info.andrewmin.dji.ast.FunctionNode;
import info.andrewmin.dji.ast.ProgramNode;
import info.andrewmin.dji.lexer.Lexer;

import java.util.HashMap;
import java.util.Map;

public class ProgramParser {
    private final Lexer lexer;
    private final FunctionParser functionParser;

    public ProgramParser(Lexer lexer) {
        this.lexer = lexer;
        this.functionParser = new FunctionParser(lexer);
    }

    public ProgramNode parse() {
        Map<String, FunctionNode> functions = new HashMap<>();
        while (lexer.hasNext()) {
            FunctionNode function = functionParser.parse();
            functions.put(function.getName(), function);
        }
        return new ProgramNode(functions);
    }
}
