package dev.jmina.context;

import java.lang.ref.WeakReference;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GlobalContextStore implements ContextStore {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private MinaContext globalContext = null;
    private WeakReference<Thread> ownerThread = null;

    @Override
    public MinaContext createOrGetContext() {
        lock.writeLock().lock();
        try {
            assertParallelAccessToGlobalContext();
            if (globalContext == null) {
                globalContext = new MinaContext();
                ownerThread = new WeakReference<>(Thread.currentThread());
            }
            return globalContext;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public MinaContext getContext() {
        lock.readLock().lock();
        try {
            return globalContext;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void removeContext() {
        lock.writeLock().lock();
        try {
            assertParallelAccessToGlobalContext();
            globalContext = null;
            ownerThread = null;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void close() {
        removeContext();
    }

    private void assertParallelAccessToGlobalContext() {
        if (globalContext == null) {
            return;
        }

        Thread ownerThread = this.ownerThread.get();
        if (ownerThread == null || !ownerThread.isAlive()) {
            return;
        }

        Thread currentThread = Thread.currentThread();
        if (currentThread == ownerThread) {
            return;
        }

        throw new IllegalStateException(
                "Mina is configured to use GLOBAL context in a single thread tests but multi access to the context detected. " +
                        "Current thread is [" + currentThread.getName() + "] but the owner thread is [" + ownerThread.getName() + "]"
        );
    }
}
