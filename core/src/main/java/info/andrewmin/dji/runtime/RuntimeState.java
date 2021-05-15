package info.andrewmin.dji.runtime;

public final class RuntimeState {
    private Value<?> returnValue;

    public RuntimeState() {
        returnValue = null;
    }

    public Value<?> getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Value<?> value) {
        returnValue = value;
    }
}
