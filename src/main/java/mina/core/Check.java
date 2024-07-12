package mina.core;

@FunctionalInterface
public interface Check {
    void verify(int index, Object[] arguments, Throwable throwable);
}
