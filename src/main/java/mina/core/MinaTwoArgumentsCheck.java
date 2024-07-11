package mina.core;

@FunctionalInterface
public interface MinaTwoArgumentsCheck<T1, T2> extends MinaCheck {
    void verify(T1 argument1, T2 argument2);

    @Override
    @SuppressWarnings("unchecked")
    default void verify(int index, Object[] arguments, Throwable throwable) {
        if (arguments == null) {
            throw new AssertionError("Call #" + index + ": Parameter arguments can't be null.");
        }

        if (arguments.length >= 2) {
            verify((T1) arguments[0], (T2) arguments[1]);
        } else if (arguments.length == 1 && throwable != null) {
            verify((T1) arguments[0], (T2) throwable);
        } else {
            throw new AssertionError(
                    "Call #" + index + ": The verification awaits at least two arguments or an argument an a throwable but found " +
                            arguments.length + " arguments and " + (throwable == null ? "no" : "a") + " throwable."
            );
        }
    }
}
