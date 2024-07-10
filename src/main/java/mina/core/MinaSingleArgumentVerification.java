package mina.core;

public interface MinaSingleArgumentVerification<T> extends MinaVerification {
    void verify(T argument);

    @Override
    @SuppressWarnings("unchecked")
    default void verify(int index, Object[] arguments, Throwable throwable) {
        if (arguments.length < 1) {
            throw new IllegalArgumentException("The verification awaits at least one argument but found 0 arguments.");
        }

        verify((T) arguments[0]);
    }
}
