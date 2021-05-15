package info.andrewmin.dji.runtime;

import java.util.*;

public class RuntimeStore {
    private final Map<Var, Value<?>> varValues;
    private final Stack<Set<Var>> varStack;

    public RuntimeStore() {
        this.varValues = new HashMap<>();
        this.varStack = new Stack<>();
    }

    public void pushScope() {
        varStack.push(new HashSet<>());
    }

    public void popScope() {
        Set<Var> popped = varStack.pop();
        for (Var var : popped) {
            varValues.remove(var);
        }
    }

    public void addVar(Var var, Value<?> value) {
        varValues.put(var, value);
        varStack.peek().add(var);
    }

    public Value<?> getVar(Var var) {
        return varValues.get(var);
    }
}
