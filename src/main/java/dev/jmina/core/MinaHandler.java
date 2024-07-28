package dev.jmina.core;

import dev.jmina.context.MinaContext;
import dev.jmina.context.MinaContextHolder;
import org.opentest4j.IncompleteExecutionException;
import org.slf4j.Marker;
import org.slf4j.event.Level;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class MinaHandler {
    public static void handle(
            String loggerName, Level level, Marker marker, String messagePattern, Object[] arguments,
            Throwable throwable
    ) {
        MinaContext context = MinaContextHolder.getContext();
        if (context == null) {
            return;
        }


        for (Map.Entry<Condition, Check> entry : context.getVerifyCalls().entrySet()) {
            Condition condition = entry.getKey();
            Check verification = entry.getValue();
            if (condition.match(loggerName, level, marker, messagePattern)) {
                int index = context.incrementAndGetIndex(condition);
                verification.verify(index, arguments, throwable);
            }
        }

        for (Condition condition : context.getForbidden()) {
            if (condition.match(loggerName, level, marker, messagePattern)) {
                String message = buildForbiddenMessage(arguments, throwable, condition);
                throw new AssertionError(message);
            }
        }
    }

    private static String buildForbiddenMessage(Object[] arguments, Throwable throwable, Condition condition) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        writer.println(MessageFormat.format("A forbidden log was caught on a condition [{0}].", condition));
        if (arguments != null) {
            writer.println(
                    MessageFormat.format(
                            "arguments = [{0}]",
                            Arrays.stream(arguments).map(String::valueOf).collect(Collectors.joining(","))
                    )
            );
        }
        if (throwable != null) {
            writer.print("Exception: ");
            writer.println(throwable.getMessage());
            throwable.printStackTrace(writer);
        }
        return stringWriter.toString();
    }

    static void verifyLost() {
        MinaContext context = MinaContextHolder.getContext();
        if (context == null) {
            return;
        }

        Set<Condition> reportConditions = new HashSet<>();
        for (Condition condition : context.getVerifyCalls().keySet()) {
            if (!context.isCalled(condition)) {
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
}
