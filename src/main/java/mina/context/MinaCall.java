package mina.context;

import mina.core.MinaVerification;

public class MinaCall {
    private final MinaCondition condition;
    private final MinaVerification expression;

    public MinaCall(MinaCondition condition, MinaVerification expression) {
        this.condition = condition;
        this.expression = expression;
    }

    public MinaCondition getCondition() {
        return condition;
    }

    public MinaVerification getExpression() {
        return expression;
    }
}
