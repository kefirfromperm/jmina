package mina.core;

import mina.context.MinaContext;

public class ConditionStep {
    private final MinaContext context;
    private final MinaCondition condition;

    ConditionStep(MinaContext context, MinaCondition condition) {
        this.context = context;
        this.condition = condition;
    }

    public void checkCanonical(MinaCheck minaCheck) {
        context.addVerifyCall(condition, minaCheck);
    }

    public <T> void check(MinaSingleArgumentCheck<T> minaCheck) {
        checkCanonical(minaCheck);
    }

    public <T1, T2> void check(MinaTwoArgumentsCheck<T1, T2> minaCheck) {
        checkCanonical(minaCheck);
    }

    public void check(Object... arguments) {
        checkCanonical(new MinaEqualsCheck(arguments));
    }

    public void checkArguments(MinaArgumentsCheck minaCheck) {
        checkCanonical(minaCheck);
    }

    public void checkArguments(MinaArgumentsThrowableCheck minaCheck) {
        checkCanonical(minaCheck);
    }

    public void checkThrowable(MinaThrowableCheck minaCheck) {
        checkCanonical(minaCheck);
    }

    public void exception() {
        context.addForbiddenCall(condition);
    }
}
