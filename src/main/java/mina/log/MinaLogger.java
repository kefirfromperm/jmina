package mina.log;

import mina.context.MinaCall;
import mina.context.MinaCondition;
import mina.context.MinaContext;
import mina.context.MinaContextHolder;
import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.helpers.AbstractLogger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MinaLogger extends AbstractLogger {
    public MinaLogger(String name) {
        this.name = name;
    }

    @Override
    protected String getFullyQualifiedCallerName() {
        return null;
    }

    @Override
    protected void handleNormalizedLoggingCall(Level level, Marker marker, String messagePattern, Object[] arguments, Throwable throwable) {
        MinaContext context = MinaContextHolder.getContext();

        for (MinaCall minaCall : context.getExpectedCalls()) {
            if (minaCall.getCondition().match(name, level, marker, messagePattern)) {
                minaCall.getExpression().verify(arguments, throwable);
            }
        }

        for (MinaCondition condition : context.getForbiddenCalls()) {
            if (condition.match(name, level, marker, messagePattern)) {
                StringWriter stringWriter = new StringWriter();
                PrintWriter writer = new PrintWriter(stringWriter);
                writer.println(MessageFormat.format("A forbidden log was caught on condition [{0}].", condition));
                writer.println(MessageFormat.format("logger name = [{0}]", name));
                writer.println(MessageFormat.format("level = [{0}]", level));
                writer.println(MessageFormat.format("marker = [{0}]", marker));
                writer.println(MessageFormat.format("message pattern = [{0}]", messagePattern));
                writer.println(MessageFormat.format("arguments = [{0}]", Arrays.stream(arguments).map(String::valueOf).collect(Collectors.joining(","))));
                if (throwable != null) {
                    writer.print("Exception: ");
                    writer.println(throwable.getMessage());
                    throwable.printStackTrace(writer);
                }
                throw new AssertionError(stringWriter.toString());
            }
        }
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return true;
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return true;
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return true;
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return true;
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return true;
    }
}
