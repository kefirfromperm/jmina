package mina.core;

@FunctionalInterface
public interface MinaThrowableVerification extends MinaVerification {
    @Override
    default void verify(Object[] arguments, Throwable throwable) {
        verify(throwable);
    }

    void verify(Throwable throwable);
}
