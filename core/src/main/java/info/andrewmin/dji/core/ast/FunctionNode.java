package info.andrewmin.dji.core.ast;

import info.andrewmin.dji.core.runtime.Var;
import info.andrewmin.dji.core.tokens.TypeTokenVariant;

import java.util.List;

/**
 * A function node that contains the function's information (name, return type, parameters) and a body.
 *
 * @see StatementNode
 */
public final class FunctionNode extends Node {
    private final String name;
    private final TypeTokenVariant returnType;
    private final List<Var> parameters;
    private final StatementNode.Block body;

    /**
     * Construct a new function node.
     *
     * @param name       The function name.
     * @param returnType The function return type.
     * @param parameters The function parameters.
     * @param body       The function block statement.
     */
    public FunctionNode(String name, TypeTokenVariant returnType, List<Var> parameters, StatementNode.Block body) {
        super("Function",
                new NodeProp("name", name),
                new NodeProp("return", returnType.type),
                new NodeProp("parameters", parameters.toArray()),
                new NodeProp("body", body.getStatements().toArray())
        );
        this.name = name;
        this.returnType = returnType;
        this.parameters = parameters;
        this.body = body;
    }

    /**
     * Get the function name.
     *
     * @return The function name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the function return type.
     *
     * @return The return type.
     */
    public TypeTokenVariant getReturnType() {
        return returnType;
    }

    /**
     * Get the function parameters.
     *
     * @return The function parameters.
     */
    public List<Var> getParameters() {
        return parameters;
    }

    /**
     * Get the function body statements.
     *
     * @return The function body statements.
     */
    public List<StatementNode> getStatements() {
        return body.getStatements();
    }
}
