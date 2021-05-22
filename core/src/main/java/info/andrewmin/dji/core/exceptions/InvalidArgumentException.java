package info.andrewmin.dji.core.exceptions;

import info.andrewmin.dji.core.runtime.Value;
import info.andrewmin.dji.core.runtime.Var;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A user error for invalid arguments during a function call.
 */
public final class InvalidArgumentException extends BaseUserException {
    /**
     * Construct a new invalid argument exception.
     *
     * @param func     The function name.
     * @param expected The excepted parameters.
     * @param actual   The actual arguments.
     */
    public InvalidArgumentException(String func, List<Var> expected, List<Value<?>> actual) {
        super(generateMessage(func, expected, actual));
    }

    private static String generateMessage(String func, List<Var> expected, List<Value<?>> actual) {
        String expectedStr = expected.stream()
                .map(Var::getType)
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        String actualStr = actual.stream()
                .map(Value::getType)
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        return func + " expected [" + expectedStr + "], but received [" + actualStr + "]";
    }
}
