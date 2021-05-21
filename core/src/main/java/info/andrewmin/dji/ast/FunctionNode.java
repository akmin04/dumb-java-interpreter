package info.andrewmin.dji.ast;

import info.andrewmin.dji.runtime.Var;
import info.andrewmin.dji.tokens.TypeTokenVariant;

import java.util.List;

public class FunctionNode extends Node {
    private final String name;
    private final TypeTokenVariant returnType;
    private final List<Var> parameters;
    private final StatementNode.Block block;

    public FunctionNode(String name, TypeTokenVariant returnType, List<Var> parameters, StatementNode.Block block) {
        super("Function",
                new NodeProp("name", name),
                new NodeProp("return", returnType.type),
                new NodeProp("parameters", parameters.toArray()),
                new NodeProp("statements", block.getStatements().toArray())
        );
        this.name = name;
        this.returnType = returnType;
        this.parameters = parameters;
        this.block = block;
    }

    public String getName() {
        return name;
    }

    public TypeTokenVariant getReturnType() {
        return returnType;
    }

    public List<Var> getParameters() {
        return parameters;
    }

    public List<StatementNode> getStatements() {
        return block.getStatements();
    }
}
