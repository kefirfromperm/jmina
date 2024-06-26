package mina.core;

import mina.context.MinaCall;
import mina.context.MinaCondition;
import mina.context.MinaContextHolder;
import org.slf4j.Marker;
import org.slf4j.event.Level;


public final class Mina {
    private Mina() {
    }

    public static void verify(
            String loggerName,
            Level level,
            Marker marker,
            String messagePattern,
            MinaVerification verification
    ) {
        MinaContextHolder.getContext().addExpectedCall(
                new MinaCall(
                        new MinaCondition(loggerName, level, marker, messagePattern),
                        verification
                )
        );
    }

    public static void clean() {
        MinaContextHolder.removeContext();
    }
}
