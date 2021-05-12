package info.andrewmin.dji.ast;

import java.util.List;

public class FunctionNode {
    private final String name;
    private final List<String> args;
    private final StatementNode statement;

    public FunctionNode(String name, List<String> args, StatementNode statement) {
        this.name = name;
        this.args = args;
        this.statement = statement;
    }
}
