package info.andrewmin.dji.core.ast;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A node property used for logging.
 */
final class NodeProp {
    private final String key;
    private final List<?> objs;

    /**
     * Construct a new node property.
     *
     * @param key  The property name.
     * @param objs The property value(s).
     */
    public NodeProp(String key, Object... objs) {
        this.key = key;
        this.objs = Arrays.asList(objs);
    }

    /**
     * The string representation given the current indent level.
     *
     * @param indentLevel The indent level of the property in the tree output.
     * @return The string representation.
     */
    String toStringIndented(int indentLevel) {
        StringBuilder builder = new StringBuilder()
                .append(key)
                .append(": ");

        if (objs.isEmpty()) {
            builder.append("[]");
        } else if (objs.size() == 1) {
            builder.append(objs.get(0));
        } else {
            builder
                    .append("[")
                    .append("\n");
            for (Object o : objs) {
                String objStr = (o instanceof Node) ? ((Node) o).toStringIndented(indentLevel - 1) : o.toString();
                objStr = indent(objStr, indentLevel);
                builder
                        .append(objStr)
                        .append("\n");
            }
            builder.append("]");
        }
        return indent(builder.toString(), indentLevel);
    }

    /**
     * Indent a given string a certain level.
     * <p>
     * The indent will be the indent level * 4 spaces.
     *
     * @param str The string to indent.
     * @param n   The indent level.
     * @return The indented string.
     */
    private String indent(String str, int n) {
        Stream<String> stream = str.lines();
        String spaces = " ".repeat(4 * n);
        stream = stream.map(s -> spaces + s);
        return stream.collect(Collectors.joining("\n", "", ""));
    }
}
