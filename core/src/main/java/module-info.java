/**
 * Defines the core DJI interpreter API.
 */
module info.andrewmin.dji.core {
    requires java.logging;
    exports info.andrewmin.dji.core.ast;
    exports info.andrewmin.dji.core.exceptions;
    exports info.andrewmin.dji.core.lexer;
    exports info.andrewmin.dji.core.parser;
    exports info.andrewmin.dji.core.runtime;
    exports info.andrewmin.dji.core.tokens;
}