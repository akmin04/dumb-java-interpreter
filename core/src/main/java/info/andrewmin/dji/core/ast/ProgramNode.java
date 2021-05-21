package info.andrewmin.dji.core.ast;

import java.util.Map;

/**
 * A program node containing all the program functions which is also the root of the program abstract syntax tree.
 *
 * @see FunctionNode
 */
public final class ProgramNode extends Node {
    /**
     * The name of the main function.
     */
    private static final String MAIN_FUNC = "main";

    private final Map<String, FunctionNode> functions;

    /**
     * Construct a new program node.
     *
     * @param functions the program functions.
     */
    public ProgramNode(Map<String, FunctionNode> functions) {
        super("Program", new NodeProp("functions", functions.values().toArray()));
        this.functions = functions;
    }

    /**
     * Get the main function.
     *
     * @return The main function, null if it does not exist.
     */
    public FunctionNode getMain() {
        return functions.get(MAIN_FUNC);
    }

    /**
     * Get all the program functions.
     *
     * @return The program functions.
     */
    public Map<String, FunctionNode> getFunctions() {
        return functions;
    }
}
