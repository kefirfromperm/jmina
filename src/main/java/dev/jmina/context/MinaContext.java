package dev.jmina.context;

import dev.jmina.core.Check;
import dev.jmina.core.Condition;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MinaContext {
    private final Map<Condition, Check> verifyCalls = new ConcurrentHashMap<>();
    private final Map<Condition, AtomicInteger> counters = new ConcurrentHashMap<>();
    private final Set<Condition> forbidden = new HashSet<>();

    public MinaContext() {
    }

    public void addVerifyCall(Condition condition, Check verification) {
        verifyCalls.put(condition, verification);
    }

    public Map<Condition, Check> getVerifyCalls() {
        return verifyCalls;
    }

    public void addForbidden(Condition condition) {
        forbidden.add(condition);
    }

    public Set<Condition> getForbidden() {
        return forbidden;
    }

    public int incrementAndGetIndex(Condition condition) {
        return counters.computeIfAbsent(condition, ignore -> new AtomicInteger(-1)).incrementAndGet();
    }

    public boolean isCalled(Condition condition) {
        return counters.get(condition) != null;
    }
}
