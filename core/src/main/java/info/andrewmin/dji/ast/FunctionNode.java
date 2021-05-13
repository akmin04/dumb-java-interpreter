package info.andrewmin.dji.ast;

import info.andrewmin.dji.ast.formatting.Node;
import info.andrewmin.dji.ast.formatting.NodeProp;
import info.andrewmin.dji.runtime.Var;
import info.andrewmin.dji.tokens.TypeTokenVariant;

import java.util.List;

public class FunctionNode extends Node {
    private final String name;
    private final TypeTokenVariant returnType;
    private final List<Var> parameters;
    private final List<StatementNode> statements;

    public FunctionNode(String name, TypeTokenVariant returnType, List<Var> parameters, List<StatementNode> statements) {
        super(
                "Function",
                new NodeProp("name", name),
                new NodeProp("return", returnType.type),
                new NodeProp("parameters", parameters.toArray()),
                new NodeProp("statements", statements.toArray())
        );
        this.name = name;
        this.returnType = returnType;
        this.parameters = parameters;
        this.statements = statements;
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
        return statements;
    }
}
