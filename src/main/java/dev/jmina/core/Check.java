package dev.jmina.core;

/**
 * The canonical functional interface to validate log calls.
 */
@FunctionalInterface
public interface Check {
    /**
     * Add here all you validations on a log call
     *
     * @param index     1-based index of call. When a log corresponding to the condition was called first time it will be 1,
     *                  next - 2, next one - 3, etc...
     * @param arguments all log arguments put after log message
     * @param throwable a log call throwable or null
     */
    void verify(int index, Object[] arguments, Throwable throwable);
}
