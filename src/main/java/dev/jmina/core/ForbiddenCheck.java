package dev.jmina.core;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

class ForbiddenCheck implements Check {
    private final Condition condition;

    public ForbiddenCheck(Condition condition) {
        this.condition = condition;
    }

    @Override
    public void verify(int index, Object[] arguments, Throwable throwable) {
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
        throw new AssertionError(stringWriter.toString());
    }
}
