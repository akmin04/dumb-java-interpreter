package info.andrewmin.dji.ast;

import info.andrewmin.dji.ast.formatting.Node;
import info.andrewmin.dji.ast.formatting.NodeProp;

import java.util.Map;

public class ProgramNode extends Node {
    private static final String MAIN_FUNC = "main";

    private final Map<String, FunctionNode> functions;

    public ProgramNode(Map<String, FunctionNode> functions) {
        super(
                "Program",
                new NodeProp("functions", functions.values().toArray())
        );
        this.functions = functions;
    }

    public boolean hasMain() {
        return functions.get(MAIN_FUNC) != null;
    }

    public Map<String, FunctionNode> getFunctions() {
        return functions;
    }
}
