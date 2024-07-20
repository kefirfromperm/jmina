package dev.jmina.core;

import dev.jmina.exception.ArgumentsCountException;

@FunctionalInterface
public interface FourArgumentsCheck<T1, T2, T3, T4> extends Check {
    void verify(T1 argument1, T2 argument2, T3 argument3, T4 argument4);

    @Override
    @SuppressWarnings("unchecked")
    default void verify(int index, Object[] arguments, Throwable throwable) {
        if (arguments == null) {
            throw new ArgumentsCountException(index, 4, 0, throwable != null);
        }

        if (arguments.length >= 4) {
            verify((T1) arguments[0], (T2) arguments[1], (T3) arguments[2], (T4) arguments[3]);
        } else if (arguments.length == 3 && throwable != null) {
            verify((T1) arguments[0], (T2) arguments[1], (T3) arguments[2], (T4) throwable);
        } else {
            throw new ArgumentsCountException(index, 4, arguments.length, throwable != null);
        }
    }
}
