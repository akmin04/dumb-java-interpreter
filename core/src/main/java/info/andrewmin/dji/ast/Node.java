package info.andrewmin.dji.ast;

import java.util.Arrays;
import java.util.List;

abstract class Node {
    private final String nodeName;
    private final List<NodeProp> props;

    public Node(String nodeName, NodeProp... props) {
        this.nodeName = nodeName;
        this.props = Arrays.asList(props);
    }

    public String toStringIndented(int indentLevel) {
        StringBuilder builder = new StringBuilder()
                .append(nodeName);
        if (!props.isEmpty()) {
            builder
                    .append(" {")
                    .append("\n");
            for (NodeProp prop : props) {
                builder
                        .append(prop.toStringIndented(indentLevel + 1))
                        .append("\n");
            }
            builder.append("}");
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return toStringIndented(0);
    }

}