package mina.core;

@FunctionalInterface
public interface MinaVerification {
    void verify(Object[] arguments, Throwable throwable);
}
