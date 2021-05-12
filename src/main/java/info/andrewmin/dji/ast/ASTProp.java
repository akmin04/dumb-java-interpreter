package info.andrewmin.dji.ast;

import java.util.Arrays;
import java.util.List;

public class ASTProp {
    private final String key;
    private final List<Object> values;

    public ASTProp(String key, Object... values) {
        this.key = key;
        this.values = Arrays.asList(values);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(key);
        builder.append(":");
        if (values.size() == 1) {
            builder.append(" ");
            builder.append(values.get(0));
        } else {
            for (Object value : values) {
                builder.append("\n\t");
                builder.append(value);
            }
        }
        return builder.toString();
    }
}
