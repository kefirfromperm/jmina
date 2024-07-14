package mina.core;

import mina.exception.ArgumentsCountException;

@FunctionalInterface
public interface TwoArgumentsCheck<T1, T2> extends Check {
    void verify(T1 argument1, T2 argument2);

    @Override
    @SuppressWarnings("unchecked")
    default void verify(int index, Object[] arguments, Throwable throwable) {
        if (arguments == null) {
            throw new ArgumentsCountException(index, 2, 0, throwable != null);
        }

        if (arguments.length >= 2) {
            verify((T1) arguments[0], (T2) arguments[1]);
        } else if (arguments.length == 1 && throwable != null) {
            verify((T1) arguments[0], (T2) throwable);
        } else {
            throw new ArgumentsCountException(index, 2, arguments.length, throwable != null);
        }
    }
}
