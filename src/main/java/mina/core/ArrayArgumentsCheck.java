package mina.core;

@FunctionalInterface
public interface ArrayArgumentsCheck extends Check {
    void verify(Object[] arguments);

    @Override
    default void verify(int index, Object[] arguments, Throwable throwable) {
        verify(arguments);
    }
}
