package mina.core;

@FunctionalInterface
public interface MinaVerification {
    void verify(int index, Object[] arguments, Throwable throwable);
}
