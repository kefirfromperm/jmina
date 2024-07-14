package mina.core;

import org.opentest4j.AssertionFailedError;
import org.opentest4j.MultipleFailuresError;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class EqualsCheck implements Check {
    private final Object[] expected;

    public EqualsCheck(Object[] expected) {
        if (expected == null) {
            throw new IllegalArgumentException("Expected arguments can't be null.");
        }

        this.expected = expected;
    }

    @Override
    public void verify(int index, Object[] arguments, Throwable throwable) {
        if (expected.length != arguments.length) {
            throw new AssertionFailedError(
                    "Expected " + expected.length + " arguments, but got " + arguments.length,
                    arrayToString(expected), arrayToString(arguments)
            );
        }

        List<AssertionError> errors = new ArrayList<>();
        for (int i = 0; i < expected.length; i++) {
            if (!Objects.equals(expected[i], arguments[i])) {
                errors.add(
                        new AssertionFailedError(
                                "Arguments in position " + i + " are not equal.",
                                expected[i], arguments[i]
                        )
                );
            }
        }

        if (errors.size() == 1) {
            throw errors.get(0);
        } else if (errors.size() > 1) {
            throw new MultipleFailuresError("Multiple arguments are not equal.", errors);
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
