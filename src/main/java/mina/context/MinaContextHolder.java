package mina.context;

import java.util.concurrent.atomic.AtomicReference;

public final class MinaContextHolder {
    public static final String GLOBAL_CONTEXT_PROPERTY_KEY = "mina.context.global";

    private static final ThreadLocal<MinaContext> CONTEXT = new InheritableThreadLocal<>();
    private static final AtomicReference<MinaContext> GLOBAL = new AtomicReference<>();
    private static final boolean useGlobalContext;

    static {
        useGlobalContext = Boolean.parseBoolean(System.getProperty(GLOBAL_CONTEXT_PROPERTY_KEY));
    }

    private MinaContextHolder() {
    }

    public static MinaContext getContext() {
        if (useGlobalContext) {
            return GLOBAL.updateAndGet(context -> context == null ? new MinaContext() : context);
        } else {
            return getThreadLocalContext();
        }
    }

    private static MinaContext getThreadLocalContext() {
        MinaContext minaContext = CONTEXT.get();
        if (minaContext == null) {
            minaContext = new MinaContext();
            CONTEXT.set(minaContext);
        }
        return minaContext;
    }

    public static void removeContext() {
        if (useGlobalContext) {
            GLOBAL.set(null);
        } else {
            CONTEXT.remove();
        }
    }

}
