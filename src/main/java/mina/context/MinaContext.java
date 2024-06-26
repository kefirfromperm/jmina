package mina.context;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MinaContext {
    private final List<MinaCall> expectedCalls = new CopyOnWriteArrayList<>();

    public MinaContext() {
    }

    public List<MinaCall> getExpectedCalls() {
        return expectedCalls;
    }

    public void addExpectedCall(MinaCall call) {
        expectedCalls.add(call);
    }
}
