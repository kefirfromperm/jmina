package mina.core;

import mina.context.MinaContext;
import mina.context.MinaContextHolder;
import org.slf4j.Marker;
import org.slf4j.event.Level;


public final class Mina {
    private Mina() {
    }

    public static MinaBuilder on(
            String loggerName,
            Level level,
            Marker marker,
            String messagePattern
    ) {
        return new MinaBuilder(
                MinaContextHolder.getContext(),
                new MinaCondition(loggerName, level, marker, messagePattern)
        );
    }

    public static MinaBuilder on(
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

    public static MinaBuilder on(
            Class<?> loggerClass,
            Level level,
            Marker marker
    ) {
        return on(loggerClass, level, marker, null);
    }

    public static MinaBuilder on(
            Class<?> loggerClass,
            Level level,
            String messagePattern
    ) {
        return on(loggerClass, level, null, messagePattern);
    }

    public static MinaBuilder on(
            Class<?> loggerClass,
            Level level
    ) {
        return on(loggerClass, level, null, null);
    }

    public static MinaBuilder on(
            Class<?> loggerClass,
            Marker marker,
            String messagePattern
    ) {
        return on(
                loggerClass, null, marker, messagePattern
        );
    }

    public static MinaBuilder on(
            Class<?> loggerClass,
            Marker marker
    ) {
        return on(loggerClass, null, marker, null);
    }

    public static MinaBuilder on(
            Class<?> loggerClass,
            String messagePattern
    ) {
        return on(loggerClass, null, null, messagePattern);
    }

    public static MinaBuilder on(
            Class<?> loggerClass
    ) {
        return on(loggerClass, null, null, null);
    }

    public static MinaBuilder on(
            Level level,
            Marker marker,
            String messagePattern
    ) {
        return on((String) null, level, marker, messagePattern);
    }

    public static MinaBuilder on(
            Level level,
            Marker marker
    ) {
        return on((String) null, level, marker, null);
    }

    public static MinaBuilder on(
            Level level,
            String messagePattern
    ) {
        return on((String) null, level, null, messagePattern);
    }

    public static MinaBuilder on(
            Level level
    ) {
        return on((String) null, level, null, null);
    }

    public static MinaBuilder on(
            Marker marker,
            String messagePattern
    ) {
        return on((String) null, null, marker, messagePattern);
    }

    public static MinaBuilder on(
            Marker marker
    ) {
        return on((String) null, null, marker, null);
    }

    public static MinaBuilder on(
            String messagePattern
    ) {
        return on((String) null, null, null, messagePattern);
    }

    public static MinaBuilder on() {
        return on((String) null, null, null, null);
    }

    public static void assertAllCalled() {
        MinaContextHolder.getContext().verifyLost();
    }

    public static void clean() {
        MinaContextHolder.removeContext();
    }

    public static class MinaBuilder {
        private final MinaContext context;
        private final MinaCondition condition;

        public MinaBuilder(MinaContext context, MinaCondition condition) {
            this.context = context;
            this.condition = condition;
        }

        public void check(MinaVerification verification) {
            context.addVerifyCall(condition, verification);
        }

        public void check(MinaArgumentVerification verification) {
            check((MinaVerification) verification);
        }

        public void check(MinaArgumentThrowableVerification verification) {
            check((MinaVerification) verification);
        }

        public void checkThrowable(MinaThrowableVerification verification) {
            check(verification);
        }

        public void exception() {
            context.addForbiddenCall(condition);
        }
    }
}
