package info.andrewmin.dji.core.runtime;

import info.andrewmin.dji.core.ast.ExpressionNode;
import info.andrewmin.dji.core.ast.FunctionNode;
import info.andrewmin.dji.core.ast.ProgramNode;
import info.andrewmin.dji.core.ast.StatementNode;

import java.util.logging.Logger;

public class Runtime {
    private static final Logger LOGGER = Logger.getLogger(Runtime.class.getName());

    private final RuntimeContext context;
    private final ProgramNode program;

    public Runtime(ProgramNode program) {
        this.context = new RuntimeContext();
        this.program = program;
    }

    public Value<?> runProgram() {
        FunctionNode main = program.getMain();
        if (main == null) {
            // TODO
            return null;
        }
        return run(main);
    }

    private Value<?> run(FunctionNode function) {
        context.pushVarScope();
        for (StatementNode statement : function.getStatements()) {
            run(statement);
            if (context.getReturnValue() != null) {
                Value<?> ret = context.getReturnValue();
                context.resetReturnValue();
                context.popVarScope();
                return ret;
            }
        }
        context.popVarScope();
        return null;
    }

    private void run(StatementNode statement) {
        if (statement instanceof StatementNode.Expression) {
            LOGGER.fine("Running expression statement: " + statement.getName());
            StatementNode.Expression expr = (StatementNode.Expression) statement;
            run(expr.getExpr());
        } else if (statement instanceof StatementNode.Return) {
            LOGGER.fine("Running return statement: " + statement.getName());
            StatementNode.Return ret = (StatementNode.Return) statement;
            context.setReturnValue(run(ret.getExpr()));
        }
    }

    private Value<?> run(ExpressionNode expr) {
        if (expr instanceof ExpressionNode.Literal) {
            LOGGER.fine("Running literal expression: " + expr.getName());
            return ((ExpressionNode.Literal) expr).getValue();
        }
        return null;
    }
}
