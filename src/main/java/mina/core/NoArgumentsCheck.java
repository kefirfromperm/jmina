package mina.core;

@FunctionalInterface
public interface NoArgumentsCheck extends Check {
    void verify();

    @Override
    default void verify(int index, Object[] arguments, Throwable throwable) {
        verify();
    }
}
