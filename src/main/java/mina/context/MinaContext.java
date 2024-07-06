package mina.context;

import mina.core.MinaCondition;
import mina.core.MinaVerification;
import org.slf4j.Marker;
import org.slf4j.event.Level;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MinaContext {
    private final Map<MinaCondition, MinaVerification> verifyCalls = new ConcurrentHashMap<>();
    private final Set<MinaCondition> forbiddenCalls = new CopyOnWriteArraySet<>();
    private final Map<MinaCondition, AtomicInteger> counters = new ConcurrentHashMap<>();

    public MinaContext() {
    }

    public void handle(
            String loggerName, Level level, Marker marker, String messagePattern, Object[] arguments,
            Throwable throwable
    ) {
        for (Map.Entry<MinaCondition, MinaVerification> entry : verifyCalls.entrySet()) {
            MinaCondition condition = entry.getKey();
            MinaVerification verification = entry.getValue();
            if (condition.match(loggerName, level, marker, messagePattern)) {
                int index = counters.computeIfAbsent(condition, ignore -> new AtomicInteger()).incrementAndGet();
                verification.verify(index, arguments, throwable);
            }
        }

        for (MinaCondition condition : forbiddenCalls) {
            if (condition.match(loggerName, level, marker, messagePattern)) {
                StringWriter stringWriter = new StringWriter();
                PrintWriter writer = new PrintWriter(stringWriter);
                writer.println(MessageFormat.format("A forbidden log was caught on condition [{0}].", condition));
                writer.println(MessageFormat.format("logger name = [{0}]", loggerName));
                writer.println(MessageFormat.format("level = [{0}]", level));
                writer.println(MessageFormat.format("marker = [{0}]", marker));
                writer.println(MessageFormat.format("message pattern = [{0}]", messagePattern));
                writer.println(
                        MessageFormat.format("arguments = [{0}]", Arrays.stream(arguments).map(String::valueOf).collect(
                                Collectors.joining(","))));
                if (throwable != null) {
                    writer.print("Exception: ");
                    writer.println(throwable.getMessage());
                    throwable.printStackTrace(writer);
                }
                throw new AssertionError(stringWriter.toString());
            }
        }
    }

    public void addVerifyCall(MinaCondition condition, MinaVerification verification) {
        verifyCalls.put(condition, verification);
    }

    public void addForbiddenCall(MinaCondition condition) {
        forbiddenCalls.add(condition);
    }
}
