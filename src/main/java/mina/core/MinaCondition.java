package mina.core;

import org.slf4j.Marker;
import org.slf4j.event.Level;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MinaCondition condition = (MinaCondition) o;
        return Objects.equals(loggerName, condition.loggerName) &&
                level == condition.level &&
                Objects.equals(marker, condition.marker) &&
                Objects.equals(messagePattern, condition.messagePattern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loggerName, level, marker, messagePattern);
    }

    @Override
    public String toString() {
        return "MinaCondition{" +
                "loggerName='" + loggerName + '\'' +
                ", level=" + level +
                ", marker=" + marker +
                ", messagePattern='" + messagePattern + '\'' +
                '}';
    }
}
