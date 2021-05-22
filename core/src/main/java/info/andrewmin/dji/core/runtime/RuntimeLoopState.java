package info.andrewmin.dji.core.runtime;

/**
 * A runtime context loop state used for proper loop control flow with breaks and continues.
 */
final class RuntimeLoopState {
    private int nestedLoopCount;
    private Flow flowState;

    /**
     * Construct a new runtime loop state.
     */
    RuntimeLoopState() {
        nestedLoopCount = 0;
        flowState = Flow.NONE;
    }

    /**
     * Setup for a loop start.
     */
    void loopStart() {
        nestedLoopCount++;
    }

    /**
     * Cleanup after a loop ends.
     */
    void loopEnd() {
        nestedLoopCount--;
        flowState = Flow.NONE;
    }

    /**
     * Check if the program is currently inside a loop.
     *
     * @return If the program is currently inside a loop/
     */
    boolean inLoop() {
        return nestedLoopCount > 0;
    }

    /**
     * Get the control flow state.
     *
     * @return The control flow state.
     */
    Flow getFlowState() {
        return flowState;
    }

    /**
     * Set the control flow state.
     *
     * @param state The new state.
     */
    void setFlowState(Flow state) {
        flowState = state;
    }

    /**
     * A control state flow.
     */
    enum Flow {
        BREAK,
        CONTINUE,
        NONE,
    }
}


