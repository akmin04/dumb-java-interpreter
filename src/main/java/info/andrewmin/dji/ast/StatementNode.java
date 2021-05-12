package info.andrewmin.dji.ast;

import java.util.List;

public abstract class StatementNode {

    private StatementNode() {
    }

    /**
     * A compound of statements listed in curly braces.
     */
    public static class Compound extends StatementNode {
        private final List<StatementNode> statements;

        public Compound(List<StatementNode> statements) {
            this.statements = statements;
        }

        @Override
        public String toString() {
            return ASTFormatter.format(this, new ASTProp("statements", statements.toArray()));
        }
    }

    /**
     * An expression statement.
     */
    public static class Expression extends StatementNode {
        private final ExpressionNode expr;

        public Expression(ExpressionNode expr) {
            this.expr = expr;
        }

        @Override
        public String toString() {
            return ASTFormatter.format(this, new ASTProp("expression", expr));
        }
    }

    /**
     * A variable declaration statement.
     */
    public static class VariableDeclaration extends StatementNode {
        private final String var;
        private final ExpressionNode expr;

        public VariableDeclaration(String var, ExpressionNode expr) {
            this.var = var;
            this.expr = expr;
        }

        @Override
        public String toString() {
            return ASTFormatter.format(this, new ASTProp("variable", var), new ASTProp("expression", expr));
        }
    }
}
