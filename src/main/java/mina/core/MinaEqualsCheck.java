package mina.core;

import java.util.Objects;

class MinaEqualsCheck implements MinaCheck {
    private final Object[] expected;

    public MinaEqualsCheck(Object[] expected) {
        if (expected == null) {
            throw new IllegalArgumentException("Expected arguments can't be null.");
        }

        this.expected = expected;
    }

    @Override
    public void verify(int index, Object[] arguments, Throwable throwable) {
        if (expected.length != arguments.length) {
            String message = "Expected " + expected.length + " arguments, but got " +
                    arguments.length + "\n" +
                    "Expected arguments: " +
                    arrayToString(expected) +
                    "\n" +
                    "Actual arguments: " +
                    arrayToString(arguments);
            throw new AssertionError(message);
        }

        for (int i = 0; i < expected.length; i++) {
            if (!Objects.equals(expected[i], arguments[i])) {
                throw new AssertionError(
                        "Argument " + i + " is not equal. Expected: " + expected[i] + ", Actual: " + arguments[i]
                );
            }
        }
    }

    private String arrayToString(Object[] arguments) {
        StringBuilder b = new StringBuilder();
        b.append("[");
        for (int i = 0; i < arguments.length; i++) {
            if (i != 0) {
                b.append(", ");
            }
            b.append(arguments[i]);
        }
        b.append("]");
        return b.toString();
    }
}
