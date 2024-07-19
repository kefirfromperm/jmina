package mina.core;

import mina.exception.ArgumentsCountException;

@FunctionalInterface
public interface SixArgumentsCheck<T1, T2, T3, T4, T5, T6> extends Check {
    void verify(T1 argument1, T2 argument2, T3 argument3, T4 argument4, T5 argument5, T6 argument6);

    @Override
    @SuppressWarnings("unchecked")
    default void verify(int index, Object[] arguments, Throwable throwable) {
        if (arguments == null) {
            throw new ArgumentsCountException(index, 6, 0, throwable != null);
        }

        if (arguments.length >= 6) {
            verify(
                    (T1) arguments[0], (T2) arguments[1], (T3) arguments[2], (T4) arguments[3], (T5) arguments[4],
                    (T6) arguments[5]
            );
        } else if (arguments.length == 5 && throwable != null) {
            verify(
                    (T1) arguments[0], (T2) arguments[1], (T3) arguments[2], (T4) arguments[3], (T5) arguments[4],
                    (T6) throwable
            );
        } else {
            throw new ArgumentsCountException(index, 6, arguments.length, throwable != null);
        }
    }
}
