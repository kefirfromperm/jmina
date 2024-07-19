package mina.core;

import mina.context.MinaContext;

public class CheckStep {
    private final MinaContext context;
    private final Condition condition;

    CheckStep(MinaContext context, Condition condition) {
        this.context = context;
        this.condition = condition;
    }

    public void checkCanonical(Check check) {
        if (check == null) {
            throw new IllegalArgumentException("Check can not be null");
        }
        context.addVerifyCall(condition, check);
    }

    public void check(NoArgumentsCheck check) {
        checkCanonical(check);
    }

    public <T> void check(SingleArgumentCheck<T> minaCheck) {
        checkCanonical(minaCheck);
    }

    public <T1, T2> void check(TwoArgumentsCheck<T1, T2> minaCheck) {
        checkCanonical(minaCheck);
    }

    public <T1, T2, T3> void check(ThreeArgumentsCheck<T1, T2, T3> minaCheck) {
        checkCanonical(minaCheck);
    }

    public void check(Object... arguments) {
        checkCanonical(new EqualsCheck(arguments));
    }

    public void check() {
        checkCanonical(DoNothingCheck.getInstance());
    }

    public void checkArguments(ArrayArgumentsCheck check) {
        checkCanonical(check);
    }

    public void checkArguments(ArgumentsThrowableCheck check) {
        checkCanonical(check);
    }

    public void checkThrowable(ThrowableCheck check) {
        checkCanonical(check);
    }

    public void exception() {
        checkCanonical(new ForbiddenCheck(condition));
    }
}
