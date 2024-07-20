package dev.jmina.context;

import org.junit.jupiter.api.Test;

public class MinaContextHolderTest {
    @Test
    public void testDontChangeContextStore() {
        MinaContextHolder.useGlobalContext();
        MinaContextHolder.useGlobalContext();

        MinaContextHolder.useThreadLocalContext();
        MinaContextHolder.useThreadLocalContext();
    }
}
