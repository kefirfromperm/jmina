package dev.jmina.core;

@FunctionalInterface
public interface ThrowableCheck extends Check {
    void verify(Throwable throwable);

    @Override
    default void verify(int index, Object[] arguments, Throwable throwable) {
        verify(throwable);
    }
}
