package info.andrewmin.dji.core.ast;

import info.andrewmin.dji.core.tokens.TypeTokenVariant;

import java.util.List;

/**
 * A statement node that generally represents a line of code or a control structure.
 */
public abstract class StatementNode extends Node {

    /**
     * Construct a new statement node.
     * <p>
     * To be invoked by subclass constructors only.
     *
     * @param nodeName The name of the statement node.
     * @param props    The node properties
     */
    private StatementNode(String nodeName, NodeProp... props) {
        super("Statement." + nodeName, props);
    }

    /**
     * A block statement node consisting of a list of other statement nodes.
     * <p>
     * Block statements are created with curly brackets.
     */
    public static class Block extends StatementNode {
        private final List<StatementNode> statements;

        /**
         * Construct a new block statement node.
         *
         * @param statements The list of statements
         */
        public Block(List<StatementNode> statements) {
            super("Block", new NodeProp("statements", statements.toArray()));
            this.statements = statements;
        }

        /**
         * Get the list of statements.
         *
         * @return The list of statements.
         */
        public List<StatementNode> getStatements() {
            return statements;
        }
    }

    /**
     * A variable declaration statement node.
     */
    public static class VariableDeclaration extends StatementNode {
        private final String name;
        private final TypeTokenVariant type;
        private final ExpressionNode expr;

        /**
         * Construct a new variable declaration statement node.
         *
         * @param name The name of the variable.
         * @param type The variable type.
         * @param expr The variable value expression.
         */
        public VariableDeclaration(String name, TypeTokenVariant type, ExpressionNode expr) {
            super("VariableDeclaration",
                    new NodeProp("name", name),
                    new NodeProp("type", type),
                    new NodeProp("value", expr)
            );
            this.name = name;
            this.type = type;
            this.expr = expr;
        }

        /**
         * Get the variable name.
         *
         * @return The variable name.
         */
        public String getName() {
            return name;
        }

        /**
         * Get the variable type.
         *
         * @return The variable type.
         */
        public TypeTokenVariant getType() {
            return type;
        }

        /**
         * Get the variable value expression.
         *
         * @return The variable value expression.
         */
        public ExpressionNode getExpr() {
            return expr;
        }
    }

    /**
     * An if statement node.
     */
    public static class If extends StatementNode {
        private final ExpressionNode condition;
        private final StatementNode body;
        private final StatementNode _else;

        /**
         * Construct a new if statement node.
         *
         * @param condition The if condition.
         * @param body      The body statement (usually a block statement).
         * @param _else     The else statement (usually a block statement).
         */
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

        /**
         * Get the if condition.
         *
         * @return The if condition.
         */
        public ExpressionNode getCondition() {
            return condition;
        }

        /**
         * Get the if body statement.
         *
         * @return The if body statement.
         */
        public StatementNode getBody() {
            return body;
        }

        /**
         * Get the else body statement.
         *
         * @return The else body statement.
         */
        public StatementNode getElse() {
            return _else;
        }
    }

    /**
     * A for loop statement node.
     */
    public static class For extends StatementNode {
        private final StatementNode init;
        private final ExpressionNode condition;
        private final ExpressionNode post;
        private final StatementNode body;

        /**
         * Construct a new for loop statement node.
         *
         * @param init      The for loop initial statement.
         * @param condition The for loop condition.
         * @param post      The for loop post expression.
         * @param body      The body statement.
         */
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

        /**
         * Get the for loop initial statement.
         *
         * @return The for loop initial statement.
         */
        public StatementNode getInit() {
            return init;
        }

        /**
         * Get the for loop condition.
         *
         * @return The for loop condition.
         */
        public ExpressionNode getCondition() {
            return condition;
        }

        /**
         * Get the for loop post statement.
         *
         * @return The for loop post statement.
         */
        public ExpressionNode getPost() {
            return post;
        }

        /**
         * Get the body statement.
         *
         * @return The body statement.
         */
        public StatementNode getBody() {
            return body;
        }
    }

    /**
     * A while loop statement node.
     */
    public static class While extends StatementNode {
        private final ExpressionNode condition;
        private final StatementNode body;

        /**
         * Construct a new while statement node.
         *
         * @param condition The while condition.
         * @param body      The body statement (usually a block statement).
         */
        public While(ExpressionNode condition, StatementNode body) {
            super("If",
                    new NodeProp("condition", condition),
                    new NodeProp("body", body)
            );
            this.condition = condition;
            this.body = body;
        }

        /**
         * Get the while condition.
         *
         * @return The while condition.
         */
        public ExpressionNode getCondition() {
            return condition;
        }

        /**
         * Get the body statement.
         *
         * @return The body statement.
         */
        public StatementNode getBody() {
            return body;
        }
    }

    /**
     * A break statement node.
     */
    public static class Break extends StatementNode {
        /**
         * Construct a new break statement node.
         */
        public Break() {
            super("Break");
        }
    }

    /**
     * A continue statement node.
     */
    public static class Continue extends StatementNode {
        /**
         * Construct a new continue statement node.
         */
        public Continue() {
            super("Continue");
        }
    }

    /**
     * A return statement node.
     */
    public static class Return extends StatementNode {
        private final ExpressionNode expr;

        /**
         * Construct a new return statement node.
         *
         * @param expr The return expression.
         */
        public Return(ExpressionNode expr) {
            super("Return", new NodeProp("expr", expr));
            this.expr = expr;
        }

        /**
         * Get the return expression.
         *
         * @return The return expression.
         */
        public ExpressionNode getExpr() {
            return expr;
        }
    }

    /**
     * A statement node consisting of a stand-alone expression.
     *
     * @see ExpressionNode
     */
    public static class Expression extends StatementNode {
        private final ExpressionNode expr;

        /**
         * Construct a new expression statement node.
         *
         * @param expr The stand-alone expression.
         */
        public Expression(ExpressionNode expr) {
            super("Expression", new NodeProp("expr", expr));
            this.expr = expr;
        }

        /**
         * Get the stand-alone expression.
         *
         * @return The stand-alone expression.
         */
        public ExpressionNode getExpr() {
            return expr;
        }
    }
}
