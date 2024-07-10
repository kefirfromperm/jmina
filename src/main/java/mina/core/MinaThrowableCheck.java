package mina.core;

@FunctionalInterface
public interface MinaThrowableCheck extends MinaCheck {
    void verify(Throwable throwable);

    @Override
    default void verify(int index, Object[] arguments, Throwable throwable) {
        verify(throwable);
    }
}
