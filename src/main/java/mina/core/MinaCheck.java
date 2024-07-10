package mina.core;

@FunctionalInterface
public interface MinaCheck {
    void verify(int index, Object[] arguments, Throwable throwable);
}
