package mina.context;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class MinaContextHolder {
    public static final String GLOBAL_CONTEXT_PROPERTY_KEY = "mina.context.global";

    private static final ThreadLocal<MinaContext> THREAD_LOCAL_CONTEXT = new InheritableThreadLocal<>();

    private static final AtomicBoolean useGlobalContext = new AtomicBoolean(false);
    private static final AtomicReference<MinaContext> GLOBAL_CONTEXT = new AtomicReference<>();
    private static final AtomicReference<WeakReference<Thread>> GLOBAL_CONTEXT_THREAD_OWNER = new AtomicReference<>();

    static {
        useGlobalContext.set(Boolean.parseBoolean(System.getProperty(GLOBAL_CONTEXT_PROPERTY_KEY)));
    }

    private MinaContextHolder() {
    }

    public static MinaContext createOrGetContext() {
        assertParallelAccessToGlobalContext();
        if (useGlobalContext.get()) {
            return GLOBAL_CONTEXT.updateAndGet(context -> context == null ? new MinaContext() : context);
        } else {
            return createOrGetThreadLocalContext();
        }
    }

    public static MinaContext getContext() {
        if (useGlobalContext.get()) {
            return GLOBAL_CONTEXT.get();
        } else {
            return THREAD_LOCAL_CONTEXT.get();
        }
    }

    private static MinaContext createOrGetThreadLocalContext() {
        MinaContext minaContext = THREAD_LOCAL_CONTEXT.get();
        if (minaContext == null) {
            minaContext = new MinaContext();
            THREAD_LOCAL_CONTEXT.set(minaContext);
        }
        return minaContext;
    }

    public static void removeContext() {
        assertParallelAccessToGlobalContext();
        if (useGlobalContext.get()) {
            GLOBAL_CONTEXT.set(null);
            GLOBAL_CONTEXT_THREAD_OWNER.set(null);
        } else {
            THREAD_LOCAL_CONTEXT.remove();
        }
    }

    public static void useGlobalContext() {
        useGlobalContext.set(true);
    }

    public static void useThreadLocalContext() {
        assertParallelAccessToGlobalContext();
        useGlobalContext.set(false);
    }

    private static synchronized void assertParallelAccessToGlobalContext() {
        if (!useGlobalContext.get()) {
            return;
        }

        if (GLOBAL_CONTEXT.get() == null) {
            return;
        }

        Thread currentThread = Thread.currentThread();

        Thread ownerThread = GLOBAL_CONTEXT_THREAD_OWNER.updateAndGet((weak) -> {
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
