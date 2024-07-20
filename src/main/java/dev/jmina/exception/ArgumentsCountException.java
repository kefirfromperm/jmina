package dev.jmina.exception;

public class ArgumentsCountException extends AssertionError {
    public ArgumentsCountException(
            int index, int expectedArgumentCount, int actualArgumentCount, boolean throwable
    ) {
        super(
                "Call #" + index + ": The verification awaits at least " + expectedArgumentCount + " arguments or " +
                        (expectedArgumentCount - 1) + " and a throwable but found " +
                        actualArgumentCount + " arguments and " + (throwable ? "a" : "no") + " throwable."
        );
    }
}
