package info.andrewmin.dji.core.runtime;

import java.util.logging.Logger;

/**
 * A context container for the runtime engine.
 *
 * @see Runtime
 */
final class RuntimeContext {
    private static final Logger LOGGER = Logger.getLogger(RuntimeContext.class.getName());

    private final RuntimeLoopState loopState;
    private Value<?> returnValue;

    /**
     * Construct a new runtime context.
     */
    public RuntimeContext() {
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
}
