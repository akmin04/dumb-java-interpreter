package info.andrewmin.dji.core.ast;

import java.util.Arrays;
import java.util.List;

/**
 * A node in the program abstract syntax tree.
 */
abstract class Node {
    private final String nodeName;
    private final List<NodeProp> props;

    /**
     * Construct a new node.
     *
     * @param nodeName The node name.
     * @param props    The node properties for logging.
     */
    public Node(String nodeName, NodeProp... props) {
        this.nodeName = nodeName;
        this.props = Arrays.asList(props);
    }

    /**
     * Get the node name.
     *
     * @return The node name.
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * The string representation given the current indent level.
     *
     * @param indentLevel The indent level of the node in the tree output.
     * @return The string representation.
     */
    String toStringIndented(int indentLevel) {
        StringBuilder builder = new StringBuilder().append(nodeName);
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