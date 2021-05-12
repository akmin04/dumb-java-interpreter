package info.andrewmin.dji.ast;

import java.util.Map;

public class ProgramNode {
    private final Map<String, FunctionNode> functions;

    public ProgramNode(Map<String, FunctionNode> functions) {
        this.functions = functions;
    }
}
