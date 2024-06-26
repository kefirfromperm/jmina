package mina.log;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MinaLoggerFactory implements ILoggerFactory {

    ConcurrentMap<String, Logger> loggerMap;

    public MinaLoggerFactory() {
        loggerMap = new ConcurrentHashMap<>();
    }

    /**
     * Return an appropriate {@link MinaLogger} instance by name.
     *
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
        return new MinaLogger(name);
    }

    /**
     * Clear the internal logger cache.
     *
     * This method is intended to be called by classes (in the same package or
     * subclasses) for testing purposes. This method is internal. It can be
     * modified, renamed or removed at any time without notice.
     *
     * You are strongly discouraged from calling this method in production code.
     */
    protected void reset() {
        loggerMap.clear();
    }
}
