package mina.context;

public class ThreadLocalContextStore implements ContextStore {
    private final ThreadLocal<MinaContext> threadLocalContext = new InheritableThreadLocal<>();

    @Override
    public MinaContext createOrGetContext() {
        MinaContext minaContext = threadLocalContext.get();
        if (minaContext == null) {
            minaContext = new MinaContext();
            threadLocalContext.set(minaContext);
        }
        return minaContext;

    }

    @Override
    public MinaContext getContext() {
        return threadLocalContext.get();
    }

    @Override
    public void removeContext() {
        threadLocalContext.remove();

    }

    @Override
    public void close() {
        // Nothing
    }
}
