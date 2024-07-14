package mina.core;

import mina.exception.ArgumentsCountException;

@FunctionalInterface
public interface ThreeArgumentsCheck<T1, T2, T3> extends Check {
    void verify(T1 argument1, T2 argument2, T3 argument3);

    @Override
    @SuppressWarnings("unchecked")
    default void verify(int index, Object[] arguments, Throwable throwable) {
        if (arguments == null) {
            throw new ArgumentsCountException(index, 3, 0, throwable != null);
        }

        if (arguments.length >= 3) {
            verify((T1) arguments[0], (T2) arguments[1], (T3) arguments[2]);
        } else if (arguments.length == 2 && throwable != null) {
            verify((T1) arguments[0], (T2) arguments[1], (T3) throwable);
        } else {
            throw new ArgumentsCountException(index, 3, arguments.length, throwable != null);

        }
    }
}
