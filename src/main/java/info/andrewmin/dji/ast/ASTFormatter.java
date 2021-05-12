package info.andrewmin.dji.ast;

/**
 * Abstract syntax tree print formatter helper.
 */
public final class ASTFormatter {
    private ASTFormatter() {
    }

    /**
     * Format an AST node to a string.
     *
     * @param self  the node.
     * @param props the node's properties.
     * @return a formatted String.
     */
    public static String format(Object self, ASTProp... props) {
        StringBuilder builder = new StringBuilder()
                .append(self.getClass().getSimpleName());
        for (ASTProp prop : props) {
            builder
                    .append("\n\t")
                    .append(prop.toString().replaceAll("(?m)^", "\t")); // indent nested nodes

        }
        return builder.toString();
    }
}


