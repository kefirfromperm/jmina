package mina.context;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class MinaContext {
    private final Set<MinaCall> expectedCalls = new CopyOnWriteArraySet<>();

    public MinaContext() {
    }

    public Set<MinaCall> getExpectedCalls() {
        return expectedCalls;
    }

    public void addExpectedCall(MinaCall call) {
        expectedCalls.add(call);
    }
}
