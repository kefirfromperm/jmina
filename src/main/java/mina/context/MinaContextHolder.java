package mina.context;

import java.util.concurrent.atomic.AtomicReference;

public final class MinaContextHolder {
    public static final String GLOBAL_CONTEXT_PROPERTY_KEY = "mina.context.global";

    private static final AtomicReference<ContextStore> CONTEXT_STORE = new AtomicReference<>();

    static {
        boolean useGlobalContext = Boolean.parseBoolean(System.getProperty(GLOBAL_CONTEXT_PROPERTY_KEY));
        if (useGlobalContext) {
            CONTEXT_STORE.set(new GlobalContextStore());
        } else {
            CONTEXT_STORE.set(new ThreadLocalContextStore());
        }
    }

    private MinaContextHolder() {
    }

    public static MinaContext createOrGetContext() {
        return CONTEXT_STORE.get().createOrGetContext();
    }

    public static MinaContext getContext() {
        return CONTEXT_STORE.get().getContext();
    }

    public static void removeContext() {
        CONTEXT_STORE.get().removeContext();
    }

    public static void useGlobalContext() {
        CONTEXT_STORE.updateAndGet((store) -> {
            if (store instanceof GlobalContextStore) {
                return store;
            }

            store.close();

            return new GlobalContextStore();
        });
    }

    public static void useThreadLocalContext() {
        CONTEXT_STORE.updateAndGet((store) -> {
            if (store instanceof ThreadLocalContextStore) {
                return store;
            }

            store.close();

            return new ThreadLocalContextStore();
        });
    }
}
