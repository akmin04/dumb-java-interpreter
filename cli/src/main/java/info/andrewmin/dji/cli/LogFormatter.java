package info.andrewmin.dji.cli;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

class LogFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        return "[" +
                record.getLevel() + " ".repeat(7 - record.getLevel().toString().length()) +
                "] " +
                record.getSourceClassName().replace("info.andrewmin.dji.core.", "") +
                " - " +
                formatMessage(record)
                + "\n";
    }
}
