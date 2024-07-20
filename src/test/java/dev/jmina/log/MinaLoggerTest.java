package dev.jmina.log;

import dev.jmina.core.Mina;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MinaLoggerTest {
    @Test
    public void testLoggerWithoutContext() {
        Mina.clean();
        assertDoesNotThrow(() -> new MinaLogger("test").info("test"));
    }
}
