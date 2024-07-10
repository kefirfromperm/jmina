package mina.core;

@FunctionalInterface
public interface MinaArgumentsVerification extends MinaVerification {
    void verify(Object[] arguments);

    @Override
    default void verify(int index, Object[] arguments, Throwable throwable) {
        verify(arguments);
    }
}
