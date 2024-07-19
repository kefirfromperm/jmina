package mina;

import mina.context.MinaContextHolder;
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
