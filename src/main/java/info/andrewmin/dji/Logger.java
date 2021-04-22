package info.andrewmin.dji;

/**
 * Program logger.
 */
public class Logger {

    private static Logger defaultInstance;

    /**
     * Get default Logger instance.
     *
     * @return default Logger
     */
    public static Logger getDefault() {
        if (defaultInstance != null) {
            return defaultInstance;
        }

        System.err.println("Logger not initialized");
        System.exit(1);
        return null;
    }

    /**
     * Construct Logger parameters.
     *
     * @param enableVerbose show or hide verbose log messages.
     */
    public static void init(boolean enableVerbose) {
        defaultInstance = new Logger(enableVerbose);
    }

    private final boolean enableVerbose;

    private Logger(boolean enableVerbose) {
        this.enableVerbose = enableVerbose;
    }

    /**
     * Log a debug message (hidden if enableVerbose is off).
     *
     * @param sender the sending object.
     * @param msg    the message.
     */
    public void debug(Object sender, String msg) {
        if (enableVerbose) {
            System.out.println(formatMessage(sender, msg, "DEBUG"));
        }
    }

    /**
     * Log a warning message.
     *
     * @param sender the sending object.
     * @param msg    the message.
     */
    public void warn(Object sender, String msg) {
        System.out.println(formatMessage(sender, msg, "WARN "));
    }

    /**
     * Log an error message and exit the process.
     *
     * @param sender the sending object
     * @param msg    the message.
     */
    public void error(Object sender, String msg) {
        System.err.println(formatMessage(sender, msg, "ERROR"));
        System.exit(1);
    }

    /**
     * Log a fatal message and exit the process.
     *
     * @param sender the sending object
     * @param msg    the message.
     */
    public void fatal(Object sender, String msg) {
        System.err.println(formatMessage(sender, msg, "FATAL"));
        System.exit(1);
    }

    private String formatMessage(Object sender, String msg, String level) {
        String className = sender != null ? sender.getClass().getSimpleName() + " - " : "";
        return "[" + level + "] " + className + msg;
    }
}
