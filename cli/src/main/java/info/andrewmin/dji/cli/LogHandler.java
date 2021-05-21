package info.andrewmin.dji.cli;

import java.util.logging.*;

public class LogHandler extends StreamHandler {

    private final ConsoleHandler stderrHandler = new ConsoleHandler();

    public LogHandler() {
        super(System.out, new SimpleFormatter());
    }

    @Override
    public void publish(LogRecord record) {
        if (record.getLevel().intValue() <= Level.INFO.intValue()) {
            super.publish(record);
            super.flush();
        } else {
            stderrHandler.publish(record);
            stderrHandler.flush();
        }
    }
}

