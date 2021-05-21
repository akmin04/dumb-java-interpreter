package info.andrewmin.dji.runtime;

import info.andrewmin.dji.ast.ExpressionNode;
import info.andrewmin.dji.ast.FunctionNode;
import info.andrewmin.dji.ast.ProgramNode;
import info.andrewmin.dji.ast.StatementNode;

public class Runtime {

    private final RuntimeStore store;
    private final RuntimeState state;
    private final ProgramNode program;

    public Runtime(ProgramNode program) {
        this.store = new RuntimeStore();
        this.state = new RuntimeState();
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
        store.pushScope();
        for (StatementNode statement : function.getStatements()) {
            run(statement);
            if (state.getReturnValue() != null) {
                Value<?> ret = state.getReturnValue();
                state.setReturnValue(null);
                store.popScope();
                return ret;
            }
        }
        store.popScope();
        return null;
    }

    private void run(StatementNode statement) {
        if (statement instanceof StatementNode.Expression) {
            StatementNode.Expression expr = (StatementNode.Expression) statement;
            run(expr.getExpr());
        } else if (statement instanceof StatementNode.Return) {
            StatementNode.Return ret = (StatementNode.Return) statement;
            state.setReturnValue(run(ret.getExpr()));
        }
    }

    private Value<?> run(ExpressionNode expr) {
        if (expr instanceof ExpressionNode.Literal) {
            return ((ExpressionNode.Literal) expr).getValue();
        }
        return null;
    }
}
