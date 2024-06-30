package mina.test;


import mina.core.Mina;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.event.Level;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SimpleTest {
    @AfterEach
    public void clean() {
        Mina.clean();
    }

    @Test
    public void testSomething() {
        Mina.verify(
                "mina.test.Simple", Level.INFO, null, "My first test with {}",
                (arguments, throwable) -> Assertions.assertEquals("Mina", arguments[0])
        );

        new Simple().doSomething();
    }

    @Test
    public void testPartialLoggerName() {
        Mina.verify(
                "mina.test", Level.INFO, null, "My first test with {}",
                (arguments, throwable) -> Assertions.assertEquals("Mina", arguments[0])
        );

        new Simple().doSomething();
    }

    @Test
    public void testForbidden() {
        Mina.forbid(null, Level.ERROR, null, null);

        assertThrows(AssertionError.class, () -> new Simple().doForbidden());
    }
}
