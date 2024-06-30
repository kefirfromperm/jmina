package mina.log;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MinaLoggerFactory implements ILoggerFactory {

    private final ILoggerFactory delegate;
    private final ConcurrentMap<String, Logger> loggerMap;

    public MinaLoggerFactory() {
        delegate = null;
        loggerMap = new ConcurrentHashMap<>();
    }

    public MinaLoggerFactory(ILoggerFactory delegate) {
        this.delegate = delegate;
        this.loggerMap = new ConcurrentHashMap<>();
    }

    /**
     * Return an appropriate {@link MinaLogger} instance by name.
     * <p>
     * This method will call {@link #createLogger(String)} if the logger
     * has not been created yet.
     */
    public Logger getLogger(String name) {
        return loggerMap.computeIfAbsent(name, this::createLogger);
    }

    /**
     * Actually creates the logger for the given name.
     */
    protected Logger createLogger(String name) {
        MinaLogger minaLogger = new MinaLogger(name);
        if (delegate == null) {
            return minaLogger;
        }

        ArrayList<Logger> delegates = new ArrayList<>(2);
        delegates.add(delegate.getLogger(name));
        delegates.add(minaLogger);
        return new ProxyLogger(name, delegates);
    }

    /**
     * Clear the internal logger cache.
     * <p>
     * This method is intended to be called by classes (in the same package or
     * subclasses) for testing purposes. This method is internal. It can be
     * modified, renamed or removed at any time without notice.
     * <p>
     * You are strongly discouraged from calling this method in production code.
     */
    protected void reset() {
        loggerMap.clear();
    }
}
