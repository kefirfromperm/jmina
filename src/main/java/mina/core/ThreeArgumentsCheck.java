package mina.core;

@FunctionalInterface
public interface ThreeArgumentsCheck<T1, T2, T3> extends Check {
    void verify(T1 argument1, T2 argument2, T3 argument3);

    @Override
    @SuppressWarnings("unchecked")
    default void verify(int index, Object[] arguments, Throwable throwable) {
        if (arguments == null) {
            throw new AssertionError("Call #" + index + ": Parameter arguments can't be null.");
        }

        if (arguments.length >= 3) {
            verify((T1) arguments[0], (T2) arguments[1], (T3) arguments[2]);
        } else if (arguments.length == 2 && throwable != null) {
            verify((T1) arguments[0], (T2) arguments[1], (T3) throwable);
        } else {
            throw new AssertionError(
                    "Call #" + index + ": The verification awaits at least three arguments or two arguments and a throwable but found " +
                            arguments.length + " arguments and " + (throwable == null ? "no" : "a") + " throwable."
            );
        }
    }
}
