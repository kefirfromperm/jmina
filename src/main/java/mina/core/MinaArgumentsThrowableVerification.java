package mina.core;

@FunctionalInterface
public interface MinaArgumentsThrowableVerification extends MinaVerification {
    void verify(Object[] arguments, Throwable throwable);

    @Override
    default void verify(int index, Object[] arguments, Throwable throwable) {
        verify(arguments, throwable);
    }
}
