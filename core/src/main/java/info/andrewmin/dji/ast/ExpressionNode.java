package info.andrewmin.dji.ast;

import info.andrewmin.dji.ast.formatting.Node;
import info.andrewmin.dji.ast.formatting.NodeProp;
import info.andrewmin.dji.tokens.LiteralToken;
import info.andrewmin.dji.tokens.SymbolTokenVariant;

import java.util.List;

/**
 * A djava expression that resolves to a value.
 */
public abstract class ExpressionNode extends Node {

    private ExpressionNode(String nodeName, NodeProp... props) {
        super("Expression." + nodeName, props);
    }

    /**
     * A literal value expression.
     */
    public static class Literal extends ExpressionNode {
        private final LiteralToken<?> value;

        public Literal(LiteralToken<?> token) {
            super(
                    "Literal",
                    new NodeProp("value", token)
            );
            this.value = token;
        }

        public LiteralToken<?> getValue() {
            return value;
        }
    }

    /**
     * A variable reference expression.
     */
    public static class VariableReference extends ExpressionNode {
        private final String name;

        public VariableReference(String name) {
            super(
                    "VariableReference",
                    new NodeProp("name", name)
            );
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * A function call site expression.
     */
    public static class FunctionCall extends ExpressionNode {
        private final String name;
        private final List<ExpressionNode> args;

        public FunctionCall(String name, List<ExpressionNode> args) {
            super(
                    "FunctionCall",
                    new NodeProp("name", name),
                    new NodeProp("args", args.toArray())
            );
            this.name = name;
            this.args = args;
        }

        public String getName() {
            return name;
        }

        public List<ExpressionNode> getArgs() {
            return args;
        }
    }

    /**
     * A mathematical binary expression.
     */
    public static class Binary extends ExpressionNode {
        private final SymbolTokenVariant operator;
        private final ExpressionNode leftExpr;
        private final ExpressionNode rightExpr;

        public Binary(SymbolTokenVariant operator, ExpressionNode leftExpr, ExpressionNode rightExpr) {
            super(
                    "Binary",
                    new NodeProp("operator", operator.symbol),
                    new NodeProp("left", leftExpr),
                    new NodeProp("right", rightExpr)
            );
            this.operator = operator;
            this.leftExpr = leftExpr;
            this.rightExpr = rightExpr;
        }

        public SymbolTokenVariant getOperator() {
            return operator;
        }

        public ExpressionNode getLeftExpr() {
            return leftExpr;
        }

        public ExpressionNode getRightExpr() {
            return rightExpr;
        }
    }

    /**
     * A mathematical unary prefix expression.
     */
    public static class Unary extends ExpressionNode {
        private final SymbolTokenVariant operator;
        private final ExpressionNode expr;

        public Unary(SymbolTokenVariant operator, ExpressionNode expr) {
            super(
                    "Unary",
                    new NodeProp("operator", operator.symbol),
                    new NodeProp("expression", expr)
            );
            this.operator = operator;
            this.expr = expr;
        }

        public SymbolTokenVariant getOperator() {
            return operator;
        }

        public ExpressionNode getExpr() {
            return expr;
        }
    }
}