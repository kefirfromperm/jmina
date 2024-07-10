package mina.core;

@FunctionalInterface
public interface MinaArgumentsCheck extends MinaCheck {
    void verify(Object[] arguments);

    @Override
    default void verify(int index, Object[] arguments, Throwable throwable) {
        verify(arguments);
    }
}
