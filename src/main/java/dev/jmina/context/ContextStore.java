package dev.jmina.context;

public interface ContextStore {
    MinaContext createOrGetContext();

    MinaContext getContext();

    void removeContext();

    void close();
}
