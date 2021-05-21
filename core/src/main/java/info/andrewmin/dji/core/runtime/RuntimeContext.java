package info.andrewmin.dji.core.runtime;

import java.util.*;
import java.util.logging.Logger;

public final class RuntimeContext {
    private static final Logger LOGGER = Logger.getLogger(RuntimeContext.class.getName());

    private final Map<Var, Value<?>> varValues;
    private final Stack<Set<Var>> varStack;

    private Value<?> returnValue;

    public RuntimeContext() {
        this.varValues = new HashMap<>();
        this.varStack = new Stack<>();
        this.returnValue = null;
    }

    public Value<?> getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Value<?> value) {
        LOGGER.info("Return value: " + value);
        returnValue = value;
    }

    public void resetReturnValue() {
        returnValue = null;
    }

    public void pushVarScope() {
        LOGGER.info("New var scope");
        varStack.push(new HashSet<>());
    }

    public void popVarScope() {
        LOGGER.info("Popped var scope");
        Set<Var> popped = varStack.pop();
        for (Var var : popped) {
            varValues.remove(var);
        }
    }

    public void putVar(Var var, Value<?> value) {
        varValues.put(var, value);
        varStack.peek().add(var);
    }

    public Value<?> getVar(Var var) {
        return varValues.get(var);
    }
}
