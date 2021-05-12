package info.andrewmin.dji.ast;

import info.andrewmin.dji.tokens.LiteralToken;
import info.andrewmin.dji.tokens.SymbolTokenType;

import java.util.List;

/**
 * A djava expression that resolves to a value.
 */
public abstract class ExpressionNode {

    private ExpressionNode() {
    }

    /**
     * A literal value expression.
     */
    public static class Literal extends ExpressionNode {
        private final LiteralToken<?> token;

        public Literal(LiteralToken<?> token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return ASTFormatter.format(this, new ASTProp("token", token));
        }
    }

    /**
     * A variable reference expression.
     */
    public static class VariableReference extends ExpressionNode {
        private final String var;

        public VariableReference(String var) {
            this.var = var;
        }

        @Override
        public String toString() {
            return ASTFormatter.format(this, new ASTProp("variable", var));
        }
    }

    /**
     * A function call site expression.
     */
    public static class FunctionCall extends ExpressionNode {
        private final String func;
        private final List<ExpressionNode> args;

        public FunctionCall(String func, List<ExpressionNode> args) {
            this.func = func;
            this.args = args;
        }

        @Override
        public String toString() {
            return ASTFormatter.format(
                    this,
                    new ASTProp("function", func),
                    new ASTProp("args", args.toArray())
            );
        }
    }

    /**
     * A mathematical binary expression.
     */
    public static class Binary extends ExpressionNode {
        private final SymbolTokenType operator;
        private final ExpressionNode leftExpr;
        private final ExpressionNode rightExpr;

        public Binary(SymbolTokenType operator, ExpressionNode leftExpr, ExpressionNode rightExpr) {
            this.operator = operator;
            this.leftExpr = leftExpr;
            this.rightExpr = rightExpr;
        }

        @Override
        public String toString() {
            return ASTFormatter.format(
                    this,
                    new ASTProp("operator", operator),
                    new ASTProp("left", leftExpr),
                    new ASTProp("right", rightExpr)
            );
        }
    }

    /**
     * A mathematical unary prefix expression.
     */
    public static class Unary extends ExpressionNode {
        private final SymbolTokenType operator;
        private final ExpressionNode expr;

        public Unary(SymbolTokenType operator, ExpressionNode expr) {
            this.operator = operator;
            this.expr = expr;
        }

        @Override
        public String toString() {
            return ASTFormatter.format(
                    this,
                    new ASTProp("operator", operator),
                    new ASTProp("expression", expr)
            );
        }
    }
}