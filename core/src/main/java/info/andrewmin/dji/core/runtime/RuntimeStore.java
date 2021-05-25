package info.andrewmin.dji.core.runtime;

import info.andrewmin.dji.core.exceptions.UnresolvedIdentifierException;

import java.util.*;
import java.util.logging.Logger;

/**
 * A runtime function variable store.
 */
final class RuntimeStore {
    private static final Logger LOGGER = Logger.getLogger(RuntimeStore.class.getName());

    private final Map<String, Value<?>> varValues;
    private final Stack<Set<String>> varStack;

    private final String name;

    /**
     * Construct a new runtime store.
     *
     * @param name The function name (for logging).
     */
    public RuntimeStore(String name) {
        this.varValues = new HashMap<>();
        this.varStack = new Stack<>();
        this.name = name;
        pushScope();
    }

    /**
     * Push a new variable scope onto the stack.
     */
    public void pushScope() {
        LOGGER.fine("New scope (" + name + "): #" + (varStack.size() + 1));
        varStack.push(new HashSet<>());
    }

    /**
     * Pop the most recent variable scope and delete its associated values.
     */
    public void popScope() {
        LOGGER.fine("Popped scope (" + name + ") #" + varStack.size());
        Set<String> popped = varStack.pop();
        for (String var : popped) {
            LOGGER.fine("Deleted variable: " + var);
            varValues.remove(var);
        }
    }

    /**
     * Put a variable into the stack.
     *
     * @param var   The variable name.
     * @param value The variable value.
     */
    public void put(String var, Value<?> value) {
        if (!varValues.containsKey(var)) {
            LOGGER.fine("New variable: " + var + " (" + name + ") #" + varStack.size());
            varStack.peek().add(var);
        } else {
            LOGGER.fine("Update variable: " + var + " (" + name + ")");
        }
        varValues.put(var, value);

    }

    /**
     * Get a variable's value from the stack.
     *
     * @param var The variable name.
     * @return The variable value.
     */
    public Value<?> get(String var) {
        LOGGER.fine("Get variable: " + var + " (" + name + ")");
        if (!varValues.containsKey(var)) {
            throw new UnresolvedIdentifierException(var);
        }
        return varValues.get(var);
    }
}
