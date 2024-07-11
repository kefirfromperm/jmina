package mina.core;

import mina.context.MinaContext;

public class ConditionStep {
    private final MinaContext context;
    private final MinaCondition condition;

    ConditionStep(MinaContext context, MinaCondition condition) {
        this.context = context;
        this.condition = condition;
    }

    public void check(MinaCheck minaCheck) {
        context.addVerifyCall(condition, minaCheck);
    }

    public <T> void check(MinaSingleArgumentCheck<T> minaCheck) {
        check((MinaCheck) minaCheck);
    }

    public void check(Object... arguments) {
        check(new MinaEqualsCheck(arguments));
    }

    public void checkArguments(MinaArgumentsCheck minaCheck) {
        check(minaCheck);
    }

    public void check(MinaArgumentsThrowableCheck minaCheck) {
        check((MinaCheck) minaCheck);
    }

    public void checkThrowable(MinaThrowableCheck minaCheck) {
        check(minaCheck);
    }

    public void exception() {
        context.addForbiddenCall(condition);
    }
}
