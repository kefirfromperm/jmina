package mina.context;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicReference;

public class GlobalContextStore implements ContextStore {
    private final AtomicReference<MinaContext> globalContext = new AtomicReference<>();
    private final AtomicReference<WeakReference<Thread>> ownerThread = new AtomicReference<>();

    @Override
    public synchronized MinaContext createOrGetContext() {
        assertParallelAccessToGlobalContext();
        return globalContext.updateAndGet(context -> context == null ? new MinaContext() : context);
    }

    @Override
    public MinaContext getContext() {
        return globalContext.get();
    }

    @Override
    public synchronized void removeContext() {
        assertParallelAccessToGlobalContext();
        globalContext.set(null);
        ownerThread.set(null);
    }

    @Override
    public void close() {
        removeContext();
    }

    private synchronized void assertParallelAccessToGlobalContext() {
        if (globalContext.get() == null) {
            return;
        }

        Thread currentThread = Thread.currentThread();

        Thread ownerThread = this.ownerThread.updateAndGet((weak) -> {
            Thread thread = weak != null ? weak.get() : null;
            if (thread == null || !thread.isAlive()) {
                return new WeakReference<>(currentThread);
            } else {
                return weak;
            }
        }).get();

        if (currentThread == ownerThread) {
            return;
        }

        throw new IllegalStateException(
                "Mina is configured to use GLOBAL context in a single thread tests but multi access to the context detected. " +
                        "Current thread is [" + currentThread.getName() + "] but the owner thread is [" + (ownerThread != null ? ownerThread.getName() : null) + "]"
        );
    }

}
