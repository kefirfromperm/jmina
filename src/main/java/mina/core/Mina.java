package mina.core;

import mina.context.MinaCall;
import mina.context.MinaCondition;
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

    public static void clean() {
        MinaContextHolder.removeContext();
    }

    public static class MinaCallBuilder {
        private final MinaContext context;
        private final MinaCondition minaCondition;

        public MinaCallBuilder(MinaContext context, MinaCondition minaCondition) {
            this.context = context;
            this.minaCondition = minaCondition;
        }

        public void then(MinaVerification verification) {
            context.addExpectedCall(new MinaCall(minaCondition, verification));
        }

        public void then(MinaArgumentVerification verification) {
            then((MinaVerification) verification);
        }

        public void thenThrowable(MinaThrowableVerification verification) {
            then(verification);
        }
    }
}
