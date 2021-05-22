package info.andrewmin.dji.core.runtime;

import info.andrewmin.dji.core.exceptions.InvalidIdentifierException;
import info.andrewmin.dji.core.exceptions.UnresolvedIdentifierException;

import java.util.*;
import java.util.logging.Logger;

/**
 * A context container for the runtime engine.
 *
 * @see Runtime
 */
final class RuntimeContext {
    private static final Logger LOGGER = Logger.getLogger(RuntimeContext.class.getName());

    private final Map<String, Value<?>> varValues;
    private final Stack<Set<String>> varStack;
    private final RuntimeLoopState loopState;
    private Value<?> returnValue;

    /**
     * Construct a new runtime context.
     */
    public RuntimeContext() {
        this.varValues = new HashMap<>();
        this.varStack = new Stack<>();
        this.returnValue = null;
        this.loopState = new RuntimeLoopState();
    }

    /**
     * Get the return value of the current function.
     *
     * @return The return value.
     */
    public Value<?> getReturnValue() {
        return returnValue;
    }

    /**
     * Set the return value of the current function.
     *
     * @param value The return value.
     */
    public void setReturnValue(Value<?> value) {
        LOGGER.fine("Return value: " + value);
        returnValue = value;
    }

    /**
     * Reset the return value after the function ends.
     */
    public void resetReturnValue() {
        returnValue = null;
    }

    public RuntimeLoopState getLoopState() {
        return loopState;
    }

    /**
     * Push a new variable scope onto the stack.
     */
    public void pushVarScope() {
        LOGGER.fine("New var scope");
        varStack.push(new HashSet<>());
    }

    /**
     * Pop the most recent variable scope and delete its associated values.
     */
    public void popVarScope() {
        LOGGER.fine("Popped var scope");
        Set<String> popped = varStack.pop();
        for (String var : popped) {
            varValues.remove(var);
        }
    }

    /**
     * Put a new variable into the stack.
     *
     * @param var   The variable name.
     * @param value The variable value.
     */
    public void putVar(String var, Value<?> value) {
        if (varValues.containsKey(var)) {
            throw new InvalidIdentifierException(var);
        }
        varValues.put(var, value);
        varStack.peek().add(var);
    }

    /**
     * Get a variable's value from the stack.
     *
     * @param var The variable name.
     * @return The variable value.
     */
    public Value<?> getVar(String var) {
        if (!varValues.containsKey(var)) {
            throw new UnresolvedIdentifierException(var);
        }
        return varValues.get(var);
    }

    /**
     * Set the value of an existing variable.
     *
     * @param var   The variable name.
     * @param value The new variable value.
     */
    public void setVar(String var, Value<?> value) {
        if (!varValues.containsKey(var)) {
            throw new UnresolvedIdentifierException(var);
        }
        varValues.put(var, value);
    }
}
