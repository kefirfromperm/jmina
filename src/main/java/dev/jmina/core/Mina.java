package dev.jmina.core;

import dev.jmina.context.MinaContextHolder;
import org.slf4j.Marker;
import org.slf4j.event.Level;

/**
 * This is a core class of Mina. It implements a set of static methods to use Mina in your unit tests.
 * <p>
 * {@code on(...)} - these methods responsible for condition to filter logging calls. It creates a
 * {@link dev.jmina.core.CheckStep} object. And this object can be used add verification for a logging call. For example
 * <pre>{@code
 *     on(MyClass.class, Level.DEBUG, "my comment {}").check(myArg -> assertEquals("test", myArg));
 * }</pre>
 * It should be put before call your code from the test.
 * <p>
 * In the end of your code you should call {@link #assertAllCalled} to check that your code has called all the needed
 * logs.
 * <pre>
 *     assertAllCalled();
 * </pre>
 * Finally, call {@link #clean()} to clean up the Mina context.
 * <pre>
 *     clean();
 * </pre>
 */
public final class Mina {
    /**
     * A private constructor to prevent initializing object.
     */
    private Mina() {
    }

    /**
     * Use Mina global context store. It's suitable for multithreading code but not for parallel tests.
     * <p>
     * Note! I don't recommend to use this method. Use system property {@code jmina.context.global}
     */
    public static void useGlobalContext() {
        MinaContextHolder.useGlobalContext();
    }

    /**
     * Use Mina thread local context store. It's suitable for single thread code and can be used in parallel tests.
     * <p>
     * Note! I don't recommend to use this method. Use system property {@code jmina.context.global}
     */
    public static void useThreadLocalContext() {
        MinaContextHolder.useThreadLocalContext();
    }

    /**
     * @param loggerName     string logger name
     * @param level          Slf4j logging level TRACE, DEBUG, INFO, WARN, ERROR
     * @param marker         logging marker
     * @param messagePattern A log message pattern, same as in logging
     * @return next step to add verification @see {@link CheckStep}
     */
    public static CheckStep on(
            String loggerName,
            Level level,
            Marker marker,
            String messagePattern
    ) {
        return new CheckStep(
                MinaContextHolder.createOrGetContext(),
                new Condition(loggerName, level, marker, messagePattern)
        );
    }

    /**
     * @param loggerName string logger name
     * @param level      Slf4j logging level TRACE, DEBUG, INFO, WARN, ERROR
     * @param marker     logging marker
     * @return next step to add verification @see {@link CheckStep}
     */
    public static CheckStep on(
            String loggerName,
            Level level,
            Marker marker
    ) {
        return on(loggerName, level, marker, null);
    }

    /**
     * @param loggerName     string logger name
     * @param level          Slf4j logging level TRACE, DEBUG, INFO, WARN, ERROR
     * @param messagePattern A log message pattern, same as in logging
     * @return next step to add verification @see {@link CheckStep}
     */
    public static CheckStep on(
            String loggerName,
            Level level,
            String messagePattern
    ) {
        return on(loggerName, level, null, messagePattern);
    }

    /**
     * @param loggerName string logger name
     * @param level      Slf4j logging level TRACE, DEBUG, INFO, WARN, ERROR
     * @return next step to add verification @see {@link CheckStep}
     */
    public static CheckStep on(
            String loggerName,
            Level level
    ) {
        return on(loggerName, level, null, null);
    }

    /**
     * @param loggerName     string logger name
     * @param marker         logging marker
     * @param messagePattern A log message pattern, same as in logging
     * @return next step to add verification @see {@link CheckStep}
     */
    public static CheckStep on(
            String loggerName,
            Marker marker,
            String messagePattern
    ) {
        return on(loggerName, null, marker, messagePattern);
    }

    /**
     * @param loggerName string logger name
     * @param marker     logging marker
     * @return next step to add verification @see {@link CheckStep}
     */
    public static CheckStep on(
            String loggerName,
            Marker marker
    ) {
        return on(loggerName, null, marker, null);
    }

    /**
     * @param loggerName     string logger name
     * @param messagePattern A log message pattern, same as in logging
     * @return next step to add verification @see {@link CheckStep}
     */
    public static CheckStep on(
            String loggerName,
            String messagePattern
    ) {
        return on(loggerName, null, null, messagePattern);
    }

    /**
     * @param loggerName string logger name
     * @return next step to add verification @see {@link CheckStep}
     */
    public static CheckStep onLoggerName(
            String loggerName
    ) {
        return on(loggerName, null, null, null);
    }

    /**
     * @param loggerClass    The logger owner class. The class {@code getName()} is used to get a logger name.
     * @param level          Slf4j logging level TRACE, DEBUG, INFO, WARN, ERROR
     * @param marker         logging marker
     * @param messagePattern A log message pattern, same as in logging
     * @return next step to add verification @see {@link CheckStep}
     */
    public static CheckStep on(
            Class<?> loggerClass,
            Level level,
            Marker marker,
            String messagePattern
    ) {
        return on(
                loggerClass != null ? loggerClass.getName() : null,
                level, marker, messagePattern
        );
    }

    /**
     * @param loggerClass The logger owner class. The class {@code getName()} is used to get a logger name.
     * @param level       Slf4j logging level TRACE, DEBUG, INFO, WARN, ERROR
     * @param marker      logging marker
     * @return next step to add verification @see {@link CheckStep}
     */
    public static CheckStep on(
            Class<?> loggerClass,
            Level level,
            Marker marker
    ) {
        return on(loggerClass, level, marker, null);
    }

    /**
     * @param loggerClass    The logger owner class. The class {@code getName()} is used to get a logger name.
     * @param level          Slf4j logging level TRACE, DEBUG, INFO, WARN, ERROR
     * @param messagePattern A log message pattern, same as in logging
     * @return next step to add verification @see {@link CheckStep}
     */
    public static CheckStep on(
            Class<?> loggerClass,
            Level level,
            String messagePattern
    ) {
        return on(loggerClass, level, null, messagePattern);
    }

    /**
     * @param loggerClass The logger owner class. The class {@code getName()} is used to get a logger name.
     * @param level       Slf4j logging level TRACE, DEBUG, INFO, WARN, ERROR
     * @return next step to add verification @see {@link CheckStep}
     */
    public static CheckStep on(
            Class<?> loggerClass,
            Level level
    ) {
        return on(loggerClass, level, null, null);
    }

    /**
     * @param loggerClass    The logger owner class. The class {@code getName()} is used to get a logger name.
     * @param marker         logging marker
     * @param messagePattern A log message pattern, same as in logging
     * @return next step to add verification @see {@link CheckStep}
     */
    public static CheckStep on(
            Class<?> loggerClass,
            Marker marker,
            String messagePattern
    ) {
        return on(
                loggerClass, null, marker, messagePattern
        );
    }

    /**
     * @param loggerClass The logger owner class. The class {@code getName()} is used to get a logger name.
     * @param marker      logging marker
     * @return next step to add verification @see {@link CheckStep}
     */
    public static CheckStep on(
            Class<?> loggerClass,
            Marker marker
    ) {
        return on(loggerClass, null, marker, null);
    }

    /**
     * @param loggerClass    The logger owner class. The class {@code getName()} is used to get a logger name.
     * @param messagePattern A log message pattern, same as in logging
     * @return next step to add verification @see {@link CheckStep}
     */
    public static CheckStep on(
            Class<?> loggerClass,
            String messagePattern
    ) {
        return on(loggerClass, null, null, messagePattern);
    }

    /**
     * @param loggerClass The logger owner class. The class {@code getName()} is used to get a logger name.
     * @return next step to add verification @see {@link CheckStep}
     */
    public static CheckStep on(
            Class<?> loggerClass
    ) {
        return on(loggerClass, null, null, null);
    }

    /**
     * @param level          Slf4j logging level TRACE, DEBUG, INFO, WARN, ERROR
     * @param marker         logging marker
     * @param messagePattern A log message pattern, same as in logging
     * @return next step to add verification @see {@link CheckStep}
     */
    public static CheckStep on(
            Level level,
            Marker marker,
            String messagePattern
    ) {
        return on((String) null, level, marker, messagePattern);
    }

    /**
     * @param level  Slf4j logging level TRACE, DEBUG, INFO, WARN, ERROR
     * @param marker logging marker
     * @return next step to add verification @see {@link CheckStep}
     */
    public static CheckStep on(
            Level level,
            Marker marker
    ) {
        return on((String) null, level, marker, null);
    }

    /**
     * @param level          Slf4j logging level TRACE, DEBUG, INFO, WARN, ERROR
     * @param messagePattern A log message pattern, same as in logging
     * @return next step to add verification @see {@link CheckStep}
     */
    public static CheckStep on(
            Level level,
            String messagePattern
    ) {
        return on((String) null, level, null, messagePattern);
    }

    /**
     * @param level Slf4j logging level TRACE, DEBUG, INFO, WARN, ERROR
     * @return next step to add verification @see {@link CheckStep}
     */
    public static CheckStep on(
            Level level
    ) {
        return on((String) null, level, null, null);
    }

    /**
     * @param marker         logging marker
     * @param messagePattern A log message pattern, same as in logging
     * @return next step to add verification @see {@link CheckStep}
     */
    public static CheckStep on(
            Marker marker,
            String messagePattern
    ) {
        return on((String) null, null, marker, messagePattern);
    }

    /**
     * @param marker logging marker
     * @return next step to add verification @see {@link CheckStep}
     */
    public static CheckStep on(
            Marker marker
    ) {
        return on((String) null, null, marker, null);
    }

    /**
     * @param messagePattern A log message pattern, same as in logging
     * @return next step to add verification @see {@link CheckStep}
     */
    public static CheckStep on(
            String messagePattern
    ) {
        return on((String) null, null, null, messagePattern);
    }

    /**
     * This is an empty condition to catch all the logging calls.
     *
     * @return next step to add verification @see {@link CheckStep}
     */
    public static CheckStep on() {
        return on((String) null, null, null, null);
    }

    /**
     * Check that all the logging calls by defined conditions were called.
     *
     * @throws org.opentest4j.IncompleteExecutionException if some logs by defined conditions were not called
     */
    public static void assertAllCalled() {
        MinaHandler.verifyLost();
    }

    public static void clean() {
        MinaContextHolder.removeContext();
    }
}
