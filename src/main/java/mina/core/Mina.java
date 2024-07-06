package mina.core;

import mina.context.MinaContext;
import mina.context.MinaContextHolder;
import org.slf4j.Marker;
import org.slf4j.event.Level;


public final class Mina {
    private Mina() {
    }

    public static MinaCallBuilder when(
            String loggerName,
            Level level,
            Marker marker,
            String messagePattern
    ) {
        return new MinaCallBuilder(
                MinaContextHolder.getContext(),
                new MinaCondition(loggerName, level, marker, messagePattern)
        );
    }

    public static MinaCallBuilder when(Class<?> loggerClass, Level level, String messagePattern) {
        return when(
                loggerClass != null ? loggerClass.getName() : null,
                level, null, messagePattern
        );
    }

    public static void forbid(
            String loggerName,
            Level level,
            Marker marker,
            String messagePattern
    ) {
        MinaContextHolder.getContext().addForbiddenCall(
                new MinaCondition(loggerName, level, marker, messagePattern)
        );
    }

    public static void assertAllCalled() {
        MinaContextHolder.getContext().verifyLost();
    }

    public static void clean() {
        MinaContextHolder.removeContext();
    }

    public static class MinaCallBuilder {
        private final MinaContext context;
        private final MinaCondition condition;

        public MinaCallBuilder(MinaContext context, MinaCondition condition) {
            this.context = context;
            this.condition = condition;
        }

        public void then(MinaVerification verification) {
            context.addVerifyCall(condition, verification);
        }

        public void then(MinaArgumentVerification verification) {
            then((MinaVerification) verification);
        }

        public void then(MinaArgumentThrowableVerification verification) {
            then((MinaVerification) verification);
        }

        public void thenThrowable(MinaThrowableVerification verification) {
            then(verification);
        }
    }
}
