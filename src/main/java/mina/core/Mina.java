package mina.core;

import mina.context.MinaContextHolder;
import org.slf4j.Marker;
import org.slf4j.event.Level;


public final class Mina {
    private Mina() {
    }

    public static void useGlobalContext() {
        MinaContextHolder.useGlobalContext();
    }

    public static void useThreadLocalContext() {
        MinaContextHolder.assertParallelAccessToGlobalContext();
        MinaContextHolder.useThreadLocalContext();
    }

    public static CheckStep on(
            String loggerName,
            Level level,
            Marker marker,
            String messagePattern
    ) {
        MinaContextHolder.assertParallelAccessToGlobalContext();
        return new CheckStep(
                MinaContextHolder.getContext(),
                new Condition(loggerName, level, marker, messagePattern)
        );
    }

    public static CheckStep on(
            String loggerName,
            Level level,
            Marker marker
    ) {
        return on(loggerName, level, marker, null);
    }

    public static CheckStep on(
            String loggerName,
            Level level,
            String messagePattern
    ) {
        return on(loggerName, level, null, messagePattern);
    }

    public static CheckStep on(
            String loggerName,
            Level level
    ) {
        return on(loggerName, level, null, null);
    }

    public static CheckStep on(
            String loggerName,
            Marker marker,
            String messagePattern
    ) {
        return on(loggerName, null, marker, messagePattern);
    }

    public static CheckStep on(
            String loggerName,
            Marker marker
    ) {
        return on(loggerName, null, marker, null);
    }

    public static CheckStep on(
            String loggerName,
            String messagePattern
    ) {
        return on(loggerName, null, null, messagePattern);
    }

    public static CheckStep onLoggerName(
            String loggerName
    ) {
        return on(loggerName, null, null, null);
    }

    public static CheckStep on(
            Class<?> loggerClass,
            Level level,
            Marker marker,
            String messagePattern
    ) {
        return on(
                loggerClass != null ? loggerClass.getName() : null,
                level, marker, messagePattern
        );
    }

    public static CheckStep on(
            Class<?> loggerClass,
            Level level,
            Marker marker
    ) {
        return on(loggerClass, level, marker, null);
    }

    public static CheckStep on(
            Class<?> loggerClass,
            Level level,
            String messagePattern
    ) {
        return on(loggerClass, level, null, messagePattern);
    }

    public static CheckStep on(
            Class<?> loggerClass,
            Level level
    ) {
        return on(loggerClass, level, null, null);
    }

    public static CheckStep on(
            Class<?> loggerClass,
            Marker marker,
            String messagePattern
    ) {
        return on(
                loggerClass, null, marker, messagePattern
        );
    }

    public static CheckStep on(
            Class<?> loggerClass,
            Marker marker
    ) {
        return on(loggerClass, null, marker, null);
    }

    public static CheckStep on(
            Class<?> loggerClass,
            String messagePattern
    ) {
        return on(loggerClass, null, null, messagePattern);
    }

    public static CheckStep on(
            Class<?> loggerClass
    ) {
        return on(loggerClass, null, null, null);
    }

    public static CheckStep on(
            Level level,
            Marker marker,
            String messagePattern
    ) {
        return on((String) null, level, marker, messagePattern);
    }

    public static CheckStep on(
            Level level,
            Marker marker
    ) {
        return on((String) null, level, marker, null);
    }

    public static CheckStep on(
            Level level,
            String messagePattern
    ) {
        return on((String) null, level, null, messagePattern);
    }

    public static CheckStep on(
            Level level
    ) {
        return on((String) null, level, null, null);
    }

    public static CheckStep on(
            Marker marker,
            String messagePattern
    ) {
        return on((String) null, null, marker, messagePattern);
    }

    public static CheckStep on(
            Marker marker
    ) {
        return on((String) null, null, marker, null);
    }

    public static CheckStep on(
            String messagePattern
    ) {
        return on((String) null, null, null, messagePattern);
    }

    public static CheckStep on() {
        return on((String) null, null, null, null);
    }

    public static void assertAllCalled() {
        MinaContextHolder.getContext().verifyLost();
    }

    public static void clean() {
        MinaContextHolder.removeContext();
    }
}
