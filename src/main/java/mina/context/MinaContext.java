package mina.context;

import mina.core.Check;
import mina.core.Condition;
import org.opentest4j.IncompleteExecutionException;
import org.slf4j.Marker;
import org.slf4j.event.Level;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MinaContext {
    private final Map<Condition, Check> verifyCalls = new ConcurrentHashMap<>();
    private final Map<Condition, AtomicInteger> counters = new ConcurrentHashMap<>();

    public MinaContext() {
    }

    public void handle(
            String loggerName, Level level, Marker marker, String messagePattern, Object[] arguments,
            Throwable throwable
    ) {
        for (Map.Entry<Condition, Check> entry : verifyCalls.entrySet()) {
            Condition condition = entry.getKey();
            Check verification = entry.getValue();
            if (condition.match(loggerName, level, marker, messagePattern)) {
                int index = counters.computeIfAbsent(condition, ignore -> new AtomicInteger()).incrementAndGet();
                verification.verify(index, arguments, throwable);
            }
        }
    }

    public void verifyLost() {
        Set<Condition> reportConditions = new HashSet<>();
        for (Condition condition : verifyCalls.keySet()) {
            if (counters.get(condition) == null) {
                reportConditions.add(condition);
            }
        }

        if (!reportConditions.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append("Some of mandatory logs were not called. Not called conditions:");
            for (Condition condition : reportConditions) {
                builder.append("\n\t").append(condition);
            }
            throw new IncompleteExecutionException(builder.toString());
        }
    }

    public void addVerifyCall(Condition condition, Check verification) {
        verifyCalls.put(condition, verification);
    }
}
