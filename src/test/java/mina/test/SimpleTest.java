package mina.test;


import mina.core.Mina;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.event.Level;

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
}
