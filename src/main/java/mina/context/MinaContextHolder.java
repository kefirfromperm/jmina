package mina.context;

public final class MinaContextHolder {
    private static final ThreadLocal<MinaContext> CONTEXT = new InheritableThreadLocal<>();

    private MinaContextHolder() {
    }

    public static MinaContext getContext() {
        MinaContext minaContext = CONTEXT.get();
        if (minaContext == null) {
            minaContext = new MinaContext();
            CONTEXT.set(minaContext);
        }
        return minaContext;
    }

    public static void removeContext() {
        CONTEXT.remove();
    }
}
