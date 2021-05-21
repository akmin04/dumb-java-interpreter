package info.andrewmin.dji.ast;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class NodeProp {
    private final String key;
    private final List<?> objs;

    public NodeProp(String key, Object... objs) {
        this.key = key;
        this.objs = Arrays.asList(objs);
    }

    public String toStringIndented(int indentLevel) {
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

    private String indent(String str, int n) {
        Stream<String> stream = str.lines();
        String spaces = " ".repeat(4 * n);
        stream = stream.map(s -> spaces + s);
        return stream.collect(Collectors.joining("\n", "", ""));
    }
}
