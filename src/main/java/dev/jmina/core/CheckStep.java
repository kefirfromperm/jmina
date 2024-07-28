package dev.jmina.core;

import dev.jmina.context.MinaContext;
import org.slf4j.Marker;
import org.slf4j.event.Level;

/**
 * This is a class to define verification for a logging call.
 */
public final class CheckStep {
    /**
     * Mina context which contains all the call conditions and verifications.
     */
    private final MinaContext context;

    /**
     * A new condition added by a method {@link Mina#on(String, Level, Marker, String)}
     */
    private final Condition condition;

    /**
     * A package private constructor. It can be created only inside the {@link Mina} class.
     *
     * @param context   a Mina context
     * @param condition logging call condition
     */
    CheckStep(MinaContext context, Condition condition) {
        this.context = context;
        this.condition = condition;
    }

    /**
     * The canonical check. It is a method which allows to verify most of the data. The verification closure
     * receive three parameters.
     * <ul>
     * <li>index - 1-based the ordinal index of the log call</li>
     * <li>arguments - an array of the log arguments</li>
     * <li>throwable - a throwable from the log call</li>
     * </ul>
     *
     * @param check verification closure
     */
    public void checkCanonical(Check check) {
        if (check == null) {
            throw new IllegalArgumentException("Check can not be null");
        }
        context.addVerifyCall(condition, check);
    }

    /**
     * Add the check without any arguments.
     *
     * @param check a closure without arguments
     */
    public void check(NoArgumentsCheck check) {
        checkCanonical(check);
    }

    /**
     * A one argument verification.
     *
     * @param minaCheck closure with one argument which is a first parameter of a log call or throwable
     */
    public <T> void check(SingleArgumentCheck<T> minaCheck) {
        checkCanonical(minaCheck);
    }

    /**
     * A two arguments verification.
     *
     * @param minaCheck closure with two arguments. The last one can be throwable.
     */
    public <T1, T2> void check(TwoArgumentsCheck<T1, T2> minaCheck) {
        checkCanonical(minaCheck);
    }

    /**
     * A three arguments verification.
     *
     * @param minaCheck closure with three arguments. The last one can be throwable.
     */
    public <T1, T2, T3> void check(ThreeArgumentsCheck<T1, T2, T3> minaCheck) {
        checkCanonical(minaCheck);
    }

    /**
     * A four arguments verification.
     *
     * @param minaCheck closure with four arguments. The last one can be throwable.
     */
    public <T1, T2, T3, T4> void check(FourArgumentsCheck<T1, T2, T3, T4> minaCheck) {
        checkCanonical(minaCheck);
    }

    /**
     * A five arguments verification.
     *
     * @param minaCheck closure with five arguments. The last one can be throwable.
     */
    public <T1, T2, T3, T4, T5> void check(FiveArgumentsCheck<T1, T2, T3, T4, T5> minaCheck) {
        checkCanonical(minaCheck);
    }

    /**
     * A six arguments verification.
     *
     * @param minaCheck closure with six arguments. The last one can be throwable.
     */
    public <T1, T2, T3, T4, T5, T6> void check(SixArgumentsCheck<T1, T2, T3, T4, T5, T6> minaCheck) {
        checkCanonical(minaCheck);
    }

    /**
     * A simple equals verification. It uses {@link Object#equals(Object)} method to compare log call arguments with
     * the given arguments.
     *
     * @param expectedArguments an array of expected arguments
     */
    public void check(Object... expectedArguments) {
        checkCanonical(new EqualsCheck(expectedArguments));
    }

    /**
     * Do nothing check is needed just to verify that the log call was called.
     *
     * @see Mina#assertAllCalled()
     */
    public void check() {
        checkCanonical(DoNothingCheck.getInstance());
    }

    /**
     * Verify just a log call parameters
     *
     * @param check a closure with one parameter of array of objects
     */
    public void checkArguments(ArrayArgumentsCheck check) {
        checkCanonical(check);
    }

    /**
     * Verify log call parameters and a throwable
     *
     * @param check a closure with two parameters - an array of objects and a throwable
     */
    public void checkArguments(ArgumentsThrowableCheck check) {
        checkCanonical(check);
    }

    /**
     * Verify just throwable
     *
     * @param check a closure with one parameter - throwable
     */
    public void checkThrowable(ThrowableCheck check) {
        checkCanonical(check);
    }

    /**
     * Always throw an error on a condition call
     */
    public void exception() {
        checkCanonical(new ForbiddenCheck(condition));
    }
}
