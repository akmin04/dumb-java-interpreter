package info.andrewmin.dji.ast;

import java.util.List;

public abstract class StatementNode extends Node {

    private StatementNode(String nodeName, NodeProp... props) {
        super("Statement." + nodeName, props);
    }

    public static class Block extends StatementNode {
        private final List<StatementNode> statements;

        public Block(List<StatementNode> statements) {
            super("Block", new NodeProp("statements", statements.toArray()));
            this.statements = statements;
        }

        public List<StatementNode> getStatements() {
            return statements;
        }
    }

    /**
     * A variable declaration statement.
     */
    public static class VariableDeclaration extends StatementNode {
        private final String name;
        private final ExpressionNode expr;

        public VariableDeclaration(String name, ExpressionNode expr) {
            super("VariableDeclaration",
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
        private final StatementNode body;
        private final StatementNode _else;

        public If(ExpressionNode condition, StatementNode body, StatementNode _else) {
            super("If",
                    new NodeProp("condition", condition),
                    new NodeProp("body", body),
                    new NodeProp("else", _else)
            );
            this.condition = condition;
            this.body = body;
            this._else = _else;
        }

        public ExpressionNode getCondition() {
            return condition;
        }

        public StatementNode getBody() {
            return body;
        }

        public StatementNode getElse() {
            return _else;
        }
    }

    public static class For extends StatementNode {
        private final StatementNode init;
        private final ExpressionNode condition;
        private final ExpressionNode post;
        private final StatementNode body;


        public For(StatementNode init, ExpressionNode condition, ExpressionNode post, StatementNode body) {
            super("For",
                    new NodeProp("init", init),
                    new NodeProp("condition", condition),
                    new NodeProp("post", post),
                    new NodeProp("body", body)
            );
            this.init = init;
            this.condition = condition;
            this.post = post;
            this.body = body;
        }

        public StatementNode getInit() {
            return init;
        }

        public ExpressionNode getCondition() {
            return condition;
        }

        public ExpressionNode getPost() {
            return post;
        }

        public StatementNode getBody() {
            return body;
        }
    }

    public static class While extends StatementNode {
        private final ExpressionNode condition;
        private final StatementNode body;

        public While(ExpressionNode condition, StatementNode body) {
            super("If",
                    new NodeProp("condition", condition),
                    new NodeProp("body", body)
            );
            this.condition = condition;
            this.body = body;
        }

        public ExpressionNode getCondition() {
            return condition;
        }

        public StatementNode getBody() {
            return body;
        }
    }

    public static class Break extends StatementNode {
        public Break() {
            super("Break");
        }
    }

    public static class Continue extends StatementNode {
        public Continue() {
            super("Continue");
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

    /**
     * An expression statement.
     */
    public static class Expression extends StatementNode {
        private final ExpressionNode expr;

        public Expression(ExpressionNode expr) {
            super("Expression", new NodeProp("expr", expr));
            this.expr = expr;
        }

        public ExpressionNode getExpr() {
            return expr;
        }
    }
}
