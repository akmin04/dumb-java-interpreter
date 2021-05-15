package info.andrewmin.dji.ast;

import info.andrewmin.dji.ast.formatting.Node;
import info.andrewmin.dji.ast.formatting.NodeProp;

import java.util.List;

public abstract class StatementNode extends Node {

    private StatementNode(String nodeName, NodeProp... props) {
        super("Statement." + nodeName, props);
    }

    /**
     * An expression statement.
     */
    public static class Expression extends StatementNode {
        private final ExpressionNode expr;

        public Expression(ExpressionNode expr) {
            super(
                    "Expression",
                    new NodeProp("expr", expr)
            );
            this.expr = expr;
        }

        public ExpressionNode getExpr() {
            return expr;
        }
    }

    /**
     * A variable declaration statement.
     */
    public static class VariableDeclaration extends StatementNode {
        private final String name;
        private final ExpressionNode expr;

        public VariableDeclaration(String name, ExpressionNode expr) {
            super(
                    "VariableDeclaration",
                    new NodeProp("name", name),
                    new NodeProp("value", expr)
            );
            this.name = name;
            this.expr = expr;
        }

        public String getName() {
            return name;
        }

        public ExpressionNode getExpr() {
            return expr;
        }
    }

    public static class If extends StatementNode {
        private final ExpressionNode condition;
        private final List<StatementNode> statements;

        public If(ExpressionNode condition, List<StatementNode> statements) {
            super(
                    "If",
                    new NodeProp("condition", condition),
                    new NodeProp("statements", statements.toArray())
            );
            this.condition = condition;
            this.statements = statements;
        }

        public ExpressionNode getCondition() {
            return condition;
        }

        public List<StatementNode> getStatements() {
            return statements;
        }
    }

    public static class Return extends StatementNode {
        private final ExpressionNode expr;

        public Return(ExpressionNode expr) {
            super("Return", new NodeProp("expr", expr));
            this.expr = expr;
        }

        public ExpressionNode getExpr() {
            return expr;
        }
    }
}
