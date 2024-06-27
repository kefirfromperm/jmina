package mina.context;

import org.slf4j.Marker;
import org.slf4j.event.Level;

public class MinaCondition {
    private final String loggerName;
    private final Level level;
    private final Marker marker;
    private final String messagePattern;

    public MinaCondition(String loggerName, Level level, Marker marker, String messagePattern) {
        this.loggerName = loggerName;
        this.level = level;
        this.marker = marker;
        this.messagePattern = messagePattern;
    }

    public boolean match(String loggerName, Level level, Marker marker, String messagePattern) {
        return
                loggerNameMatch(loggerName) &&
                        (this.level == null || this.level.equals(level)) &&
                        (this.marker == null || (marker != null && marker.contains(this.marker))) &&
                        (this.messagePattern == null || this.messagePattern.equals(messagePattern));
    }

    private boolean loggerNameMatch(String loggerName) {
        if (this.loggerName == null) {
            return true;
        }

        if (loggerName == null) {
            return false;
        }

        if (this.loggerName.equals(loggerName)) {
            return true;
        }

        int argLength = loggerName.length();
        int patternLength = this.loggerName.length();
        return argLength > patternLength && loggerName.startsWith(this.loggerName) && loggerName.charAt(patternLength) == '.';
    }
}
