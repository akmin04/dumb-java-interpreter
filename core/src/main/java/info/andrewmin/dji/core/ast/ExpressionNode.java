package info.andrewmin.dji.core.ast;

import info.andrewmin.dji.core.runtime.Value;
import info.andrewmin.dji.core.tokens.SymbolTokenVariant;

import java.util.List;

/**
 * An expression node that resolves to a value.
 */
public abstract class ExpressionNode extends Node {

    /**
     * Construct a new expression node.
     * <p>
     * To be invoked by subclass constructors only.
     *
     * @param nodeName The name of the expression node.
     * @param props    The node properties.
     */
    private ExpressionNode(String nodeName, NodeProp... props) {
        super("Expression." + nodeName, props);
    }

    /**
     * A literal value expression node.
     */
    public static final class Literal extends ExpressionNode {
        private final Value<?> value;

        /**
         * Construct a new literal expression node.
         *
         * @param value The literal value.
         */
        public Literal(Value<?> value) {
            super("Literal", new NodeProp("value", value));
            this.value = value;
        }

        /**
         * Get the literal value.
         *
         * @return The literal value.
         */
        public Value<?> getValue() {
            return value;
        }
    }

    /**
     * A variable reference expression node.
     */
    public static final class VariableReference extends ExpressionNode {
        private final String name;

        /**
         * Construct a new variable reference expression node.
         *
         * @param name The name of the variable.
         */
        public VariableReference(String name) {
            super("VariableReference", new NodeProp("name", name));
            this.name = name;
        }

        /**
         * Get the variable name.
         *
         * @return The variable name.
         */
        public String getName() {
            return name;
        }
    }

    /**
     * A function call site expression node.
     */
    public static final class FunctionCall extends ExpressionNode {
        private final String name;
        private final List<ExpressionNode> args;

        /**
         * Construct a new function call expression node.
         *
         * @param name The name of the function.
         * @param args The list of expressions to be passed as arguments.
         */
        public FunctionCall(String name, List<ExpressionNode> args) {
            super("FunctionCall",
                    new NodeProp("name", name),
                    new NodeProp("args", args.toArray())
            );
            this.name = name;
            this.args = args;
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
         * Get the function argument expressions.
         *
         * @return The function argument expressions.
         */
        public List<ExpressionNode> getArgs() {
            return args;
        }
    }

    /**
     * A binary expression node with an operation and two other expressions.
     */
    public static final class Binary extends ExpressionNode {
        private final SymbolTokenVariant operator;
        private final ExpressionNode leftExpr;
        private final ExpressionNode rightExpr;

        /**
         * Construct a new binary expression node.
         *
         * @param operator  The binary operator.
         * @param leftExpr  The left expression.
         * @param rightExpr The right expression.
         */
        public Binary(SymbolTokenVariant operator, ExpressionNode leftExpr, ExpressionNode rightExpr) {
            super("Binary",
                    new NodeProp("operator", operator.symbol),
                    new NodeProp("left", leftExpr),
                    new NodeProp("right", rightExpr)
            );
            this.operator = operator;
            this.leftExpr = leftExpr;
            this.rightExpr = rightExpr;
        }

        /**
         * Get the binary operator.
         *
         * @return The binary operator.
         */
        public SymbolTokenVariant getOperator() {
            return operator;
        }

        /**
         * Get the left expression.
         *
         * @return The left expression.
         */
        public ExpressionNode getLeftExpr() {
            return leftExpr;
        }

        /**
         * Get the right expression.
         *
         * @return The right expression.
         */
        public ExpressionNode getRightExpr() {
            return rightExpr;
        }
    }

    /**
     * An expression node with a unary prefix operator.
     */
    public static final class Unary extends ExpressionNode {
        private final SymbolTokenVariant operator;
        private final ExpressionNode expr;

        /**
         * Construct a new unary expression node.
         *
         * @param operator The unary operator.
         * @param expr     The expression.
         */
        public Unary(SymbolTokenVariant operator, ExpressionNode expr) {
            super("Unary",
                    new NodeProp("operator", operator.symbol),
                    new NodeProp("expression", expr)
            );
            this.operator = operator;
            this.expr = expr;
        }

        /**
         * Get the unary operator.
         *
         * @return The unary operator.
         */
        public SymbolTokenVariant getOperator() {
            return operator;
        }

        /**
         * Get the expression.
         *
         * @return The expression.
         */
        public ExpressionNode getExpr() {
            return expr;
        }
    }
}