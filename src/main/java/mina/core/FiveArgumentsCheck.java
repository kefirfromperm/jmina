package mina.core;

import mina.exception.ArgumentsCountException;

@FunctionalInterface
public interface FiveArgumentsCheck<T1, T2, T3, T4, T5> extends Check {
    void verify(T1 argument1, T2 argument2, T3 argument3, T4 argument4, T5 argument5);

    @Override
    @SuppressWarnings("unchecked")
    default void verify(int index, Object[] arguments, Throwable throwable) {
        if (arguments == null) {
            throw new ArgumentsCountException(index, 5, 0, throwable != null);
        }

        if (arguments.length >= 5) {
            verify((T1) arguments[0], (T2) arguments[1], (T3) arguments[2], (T4) arguments[3], (T5) arguments[4]);
        } else if (arguments.length == 4 && throwable != null) {
            verify((T1) arguments[0], (T2) arguments[1], (T3) arguments[2], (T4) arguments[3], (T5) throwable);
        } else {
            throw new ArgumentsCountException(index, 5, arguments.length, throwable != null);
        }
    }
}
