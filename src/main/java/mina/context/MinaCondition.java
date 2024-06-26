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
                (this.loggerName == null || this.loggerName.equals(loggerName)) &&
                        (this.level == null || this.level.equals(level)) &&
                        (this.marker == null || this.marker.equals(marker)) &&
                        (this.messagePattern == null || this.messagePattern.equals(messagePattern));
    }
}
