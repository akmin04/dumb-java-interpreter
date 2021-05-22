package info.andrewmin.dji.core.exceptions;

/**
 * A user error for unresolved variable or function identifiers.
 */
public final class UnresolvedIdentifierException extends BaseUserException {
    /**
     * Construct a new unresolved identifier exception.
     *
     * @param identifier The unresolved identifier.
     */
    public UnresolvedIdentifierException(String identifier) {
        super("Unable to resolve identifier " + identifier);
    }
}
