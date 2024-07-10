package mina.core;

@FunctionalInterface
public interface MinaThrowableVerification extends MinaVerification {
    void verify(Throwable throwable);

    @Override
    default void verify(int index, Object[] arguments, Throwable throwable) {
        verify(throwable);
    }
}
