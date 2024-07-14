package mina.core;

import mina.exception.ArgumentsCountException;

@FunctionalInterface
public interface SingleArgumentCheck<T> extends Check {
    void verify(T argument);

    @Override
    @SuppressWarnings("unchecked")
    default void verify(int index, Object[] arguments, Throwable throwable) {
        if (arguments != null && arguments.length >= 1) {
            verify((T) arguments[0]);
        } else {
            if (throwable != null) {
                verify((T) throwable);
            } else {
                throw new ArgumentsCountException(index, 1, 0, false);
            }
        }
    }
}
