package info.andrewmin.dji.core.runtime;

import info.andrewmin.dji.core.ast.ExpressionNode;
import info.andrewmin.dji.core.ast.FunctionNode;
import info.andrewmin.dji.core.ast.ProgramNode;
import info.andrewmin.dji.core.ast.StatementNode;
import info.andrewmin.dji.core.exceptions.*;
import info.andrewmin.dji.core.tokens.SymbolTokenVariant;
import info.andrewmin.dji.core.tokens.TypeTokenVariant;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * A runtime interpreter engine.
 *
 * @see RuntimeContext
 */
public final class Runtime {
    private static final Logger LOGGER = Logger.getLogger(Runtime.class.getName());

    private final RuntimeContext context;
    private final ProgramNode program;

    /**
     * Construct a new runtime interpreter engine.
     *
     * @param program The program node.
     */
    public Runtime(ProgramNode program) {
        this.context = new RuntimeContext();
        this.program = program;
    }

    /**
     * Run the program and get the return value of the main function.
     *
     * @return The return value of the main function.
     */
    public Value<?> runProgram() {
        FunctionNode main = program.getMain();
        if (main == null) {
            throw new NoMainException();
        }
        return run(main, List.of());
    }

    /**
     * Run a function with arguments.
     *
     * @param function The function node.
     * @param args     The arguments.
     * @return The function's return value.
     */
    private Value<?> run(FunctionNode function, List<Value<?>> args) {
        LOGGER.info("Running function " + function.getName());
        RuntimeStore store = new RuntimeStore(function.getName());

        // Push arguments
        if (function.getParameters().size() != args.size()) {
            throw new InvalidArgumentException(function.getName(), function.getParameters(), args);
        }
        for (int i = 0; i < function.getParameters().size(); i++) {
            if (function.getParameters().get(i).getType() != args.get(i).getType()) {
                throw new InvalidArgumentException(function.getName(), function.getParameters(), args);
            }
            store.put(function.getParameters().get(i).getName(), args.get(i));
        }

        // Run statements
        for (StatementNode statement : function.getStatements()) {
            run(store, statement);
            if (context.getReturnValue() != null) {
                Value<?> ret = context.getReturnValue();
                context.resetReturnValue();

                if (function.getReturnType() != ret.getType()) {
                    throw new TypeMismatchException(function.getReturnType(), ret.getType());
                }
                return ret;
            }
        }

        if (function.getReturnType() != TypeTokenVariant.VOID) {
            throw new MissingReturnException(function.getName());
        }
        return null;
    }

    /**
     * Run a statement.
     *
     * @param store     The variable store for the current function.
     * @param statement The statement node.
     */
    private void run(RuntimeStore store, StatementNode statement) {
        // Skip statements until end of loop if break or continue is called.
        if (context.getLoopState().getFlowState() != RuntimeLoopState.Flow.NONE) {
            LOGGER.fine("Skipping statement, flow state is " + context.getLoopState().getFlowState());
            return;
        }

        // Block
        if (statement instanceof StatementNode.Block) {
            StatementNode.Block block = (StatementNode.Block) statement;
            LOGGER.fine("Running block statement");

            for (StatementNode s : block.getStatements()) {
                run(store, s);
            }
        }
        // VariableDeclaration
        if (statement instanceof StatementNode.VariableDeclaration) {
            StatementNode.VariableDeclaration decl = (StatementNode.VariableDeclaration) statement;
            LOGGER.fine("Running variable declaration: " + decl.getName());

            Var var = new Var(decl.getType(), decl.getName());
            Value<?> value = run(store, decl.getExpr());

            if (var.getType() == TypeTokenVariant.VOID) {
                throw new VoidTypeException(var.getName());
            } else if (var.getType() != value.getType()) {
                throw new TypeMismatchException(var.getType(), value.getType());
            }
            store.put(var.getName(), value);
        }
        // If
        else if (statement instanceof StatementNode.If) {
            StatementNode.If _if = (StatementNode.If) statement;
            LOGGER.fine("Running if statement");

            if (run(store, _if.getCondition()).isTrue()) {
                LOGGER.fine("Pass condition");
                run(store, _if.getBody());
            } else {
                LOGGER.fine("Fail condition");
                run(store, _if.getElse());
            }
        }
        // For
        else if (statement instanceof StatementNode.For) {
            StatementNode.For _for = (StatementNode.For) statement;
            LOGGER.fine("Running for statement");

            context.getLoopState().loopStart();
            store.pushScope();
            for (run(store, _for.getInit()); run(store, _for.getCondition()).isTrue(); run(store, _for.getPost())) {
                if (context.getLoopState().getFlowState() == RuntimeLoopState.Flow.BREAK) {
                    LOGGER.fine("Breaking for loop");
                    break;
                } else if (context.getLoopState().getFlowState() == RuntimeLoopState.Flow.CONTINUE) {
                    LOGGER.fine("Continuing for loop");
                    context.getLoopState().setFlowState(RuntimeLoopState.Flow.NONE);
                }
                LOGGER.fine("Running for loop");
                run(store, _for.getBody());
            }
            context.getLoopState().loopEnd();
            store.popScope();
        }
        // While
        else if (statement instanceof StatementNode.While) {
            StatementNode.While _while = (StatementNode.While) statement;
            LOGGER.fine("Running while statement");

            context.getLoopState().loopStart();
            store.pushScope();
            while (run(store, _while.getCondition()).isTrue()) {
                if (context.getLoopState().getFlowState() == RuntimeLoopState.Flow.BREAK) {
                    LOGGER.fine("Breaking while loop");
                    break;
                } else if (context.getLoopState().getFlowState() == RuntimeLoopState.Flow.CONTINUE) {
                    LOGGER.fine("Continuing while loop");
                    context.getLoopState().setFlowState(RuntimeLoopState.Flow.NONE);
                }
                LOGGER.fine("Running while loop");
                run(store, _while.getBody());
            }
            context.getLoopState().loopEnd();
            store.popScope();
        }
        // Break
        else if (statement instanceof StatementNode.Break) {
            LOGGER.fine("Running break statement");
            if (!context.getLoopState().inLoop()) {
                throw new InvalidFlowStatementException("break");
            }
            context.getLoopState().setFlowState(RuntimeLoopState.Flow.BREAK);
        }
        // Continue
        else if (statement instanceof StatementNode.Continue) {
            LOGGER.fine("Running continue statement");
            if (!context.getLoopState().inLoop()) {
                throw new InvalidFlowStatementException("continue");
            }
            context.getLoopState().setFlowState(RuntimeLoopState.Flow.CONTINUE);
        }
        // Return
        else if (statement instanceof StatementNode.Return) {
            StatementNode.Return ret = (StatementNode.Return) statement;
            LOGGER.fine("Running return statement");

            context.setReturnValue(run(store, ret.getExpr()));
        }
        // Expression
        else if (statement instanceof StatementNode.Expression) {
            StatementNode.Expression expr = (StatementNode.Expression) statement;
            LOGGER.fine("Running expression statement: " + statement.getNodeName());

            run(store, expr.getExpr());
        }
    }

    /**
     * Run an expression and get it's value.
     *
     * @param store The variable store for the current function.
     * @param expr  The expression node.
     * @return The expression's value.
     */
    private Value<?> run(RuntimeStore store, ExpressionNode expr) {
        // Literal
        if (expr instanceof ExpressionNode.Literal) {
            ExpressionNode.Literal literal = (ExpressionNode.Literal) expr;
            LOGGER.fine("Running literal expression: " + literal.getValue());

            return literal.getValue();
        }
        // VariableReference
        else if (expr instanceof ExpressionNode.VariableReference) {
            ExpressionNode.VariableReference varRef = (ExpressionNode.VariableReference) expr;
            LOGGER.fine("Running variable reference: " + varRef.getName());

            return store.get(varRef.getName());
        }
        // FunctionCall
        else if (expr instanceof ExpressionNode.FunctionCall) {
            ExpressionNode.FunctionCall funcCall = (ExpressionNode.FunctionCall) expr;
            LOGGER.fine("Running function call: " + funcCall.getName());

            FunctionNode function = program.getFunctions().get(funcCall.getName());
            if (function == null) {
                throw new UnresolvedIdentifierException(funcCall.getName());
            }

            List<Value<?>> args = new ArrayList<>();
            for (int i = 0; i < funcCall.getArgs().size(); i++) {
                args.add(i, run(store, funcCall.getArgs().get(i)));
            }

            return run(function, args);
        }
        // Binary
        else if (expr instanceof ExpressionNode.Binary) {
            ExpressionNode.Binary binary = (ExpressionNode.Binary) expr;
            LOGGER.fine("Running binary: " + binary.getOperator());

            Value<?> leftValue = run(store, binary.getLeftExpr());
            Value<?> rightValue = run(store, binary.getRightExpr());
            SymbolTokenVariant op = binary.getOperator();

            // Left hand variable
            if (binary.getLeftExpr() instanceof ExpressionNode.VariableReference) {
                ExpressionNode.VariableReference varRef = (ExpressionNode.VariableReference) binary.getLeftExpr();
                Value<?> newValue = null;
                if (op == SymbolTokenVariant.ADD_ASSIGN) {
                    newValue = leftValue.add(rightValue);
                } else if (op == SymbolTokenVariant.SUB_ASSIGN) {
                    newValue = leftValue.sub(rightValue);
                } else if (op == SymbolTokenVariant.MUL_ASSIGN) {
                    newValue = leftValue.mul(rightValue);
                } else if (op == SymbolTokenVariant.QUO_ASSIGN) {
                    newValue = leftValue.quo(rightValue);
                } else if (op == SymbolTokenVariant.REM_ASSIGN) {
                    newValue = leftValue.rem(rightValue);
                } else if (op == SymbolTokenVariant.ASSIGN) {
                    newValue = rightValue;
                }

                if (newValue != null) {
                    store.put(varRef.getName(), newValue);
                    return newValue;
                }
            }
            // Non-left hand variable
            if (op == SymbolTokenVariant.ADD) {
                return leftValue.add(rightValue);
            } else if (op == SymbolTokenVariant.SUB) {
                return leftValue.sub(rightValue);
            } else if (op == SymbolTokenVariant.MUL) {
                return leftValue.mul(rightValue);
            } else if (op == SymbolTokenVariant.QUO) {
                return leftValue.quo(rightValue);
            } else if (op == SymbolTokenVariant.REM) {
                return leftValue.rem(rightValue);
            } else if (op == SymbolTokenVariant.EQL) {
                return new Value.Boolean(leftValue.eql(rightValue));
            } else if (op == SymbolTokenVariant.LSS) {
                return new Value.Boolean(leftValue.lss(rightValue));
            } else if (op == SymbolTokenVariant.GTR) {
                return new Value.Boolean(leftValue.gtr(rightValue));
            } else if (op == SymbolTokenVariant.NEQ) {
                return new Value.Boolean(leftValue.neq(rightValue));
            } else if (op == SymbolTokenVariant.LEQ) {
                return new Value.Boolean(leftValue.leq(rightValue));
            } else if (op == SymbolTokenVariant.GEQ) {
                return new Value.Boolean(leftValue.geq(rightValue));
            } else if (op == SymbolTokenVariant.LAND) {
                return new Value.Boolean(leftValue.land(rightValue));
            } else if (op == SymbolTokenVariant.LOR) {
                return new Value.Boolean(leftValue.lor(rightValue));
            } else if (
                    op.ordinal() >= SymbolTokenVariant.ADD_ASSIGN.ordinal()
                            && op.ordinal() <= SymbolTokenVariant.ASSIGN.ordinal()) {
                throw new ExpectedVariableException(op);
            } else {
                throw new InternalException("Unhandled binary: " + binary.getOperator());
            }
        }
        // Unary
        else if (expr instanceof ExpressionNode.Unary) {
            ExpressionNode.Unary unary = (ExpressionNode.Unary) expr;
            LOGGER.fine("Running unary: " + unary.getOperator());

            Value<?> value = run(store, unary.getExpr());
            SymbolTokenVariant op = unary.getOperator();

            if (op == SymbolTokenVariant.SUB) {
                return value.negate();
            } else if (op == SymbolTokenVariant.NOT) {
                return value.not();
            } else {
                throw new InternalException("Unhandled unary: " + unary.getOperator());
            }
        }
        // Unhandled
        else {
            throw new InternalException("Unhandled expression: " + expr.getNodeName());
        }
    }
}
