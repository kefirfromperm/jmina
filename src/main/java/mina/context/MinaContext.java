package mina.context;

import mina.core.MinaCheck;
import mina.core.MinaCondition;
import org.slf4j.Marker;
import org.slf4j.event.Level;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MinaContext {
    private final Map<MinaCondition, MinaCheck> verifyCalls = new ConcurrentHashMap<>();
    private final Map<MinaCondition, AtomicInteger> counters = new ConcurrentHashMap<>();

    public MinaContext() {
    }

    public void handle(
            String loggerName, Level level, Marker marker, String messagePattern, Object[] arguments,
            Throwable throwable
    ) {
        for (Map.Entry<MinaCondition, MinaCheck> entry : verifyCalls.entrySet()) {
            MinaCondition condition = entry.getKey();
            MinaCheck verification = entry.getValue();
            if (condition.match(loggerName, level, marker, messagePattern)) {
                int index = counters.computeIfAbsent(condition, ignore -> new AtomicInteger()).incrementAndGet();
                verification.verify(index, arguments, throwable);
            }
        }
    }

    public void verifyLost() {
        Set<MinaCondition> reportConditions = new HashSet<>();
        for (MinaCondition condition : verifyCalls.keySet()) {
            if (counters.get(condition) == null) {
                reportConditions.add(condition);
            }
        }

        if (!reportConditions.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append("Some of mandatory logs were not called. Not called conditions:");
            for (MinaCondition condition : reportConditions) {
                builder.append("\n\t").append(condition);
            }
            throw new AssertionError(builder.toString());
        }
    }

    public void addVerifyCall(MinaCondition condition, MinaCheck verification) {
        verifyCalls.put(condition, verification);
    }
}
