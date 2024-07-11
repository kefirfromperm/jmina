package mina.test;


import mina.core.Mina;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.event.Level;

import static org.junit.jupiter.api.Assertions.*;

public class MinaTest {
    @AfterEach
    public void clean() {
        Mina.clean();
    }

    @Test
    public void testDoNothing() {
        Mina
                .on(EmptyCode.class, Level.INFO, "Log something: {}")
                .checkArguments(arguments -> assertEquals(3, arguments.length));

        new EmptyCode().doNothing();

        assertThrows(AssertionError.class, Mina::assertAllCalled);
    }

    @Test
    public void testSomething() {
        Mina
                .on("mina.test.Simple", Level.INFO, null, "My first test with {}")
                .check("Mina");

        new Simple().doSomething();
    }

    @Test
    public void testIndex() {
        Mina
                .on("mina.test.Simple", Level.INFO, null, "My first test with {}")
                .check((index, arguments, throwable) -> {
                    assertEquals("Mina", arguments[0]);
                    assertEquals(1, index);
                });

        new Simple().doSomething();
    }

    @Test
    public void testLoggerClass() {
        Mina
                .on(Simple.class, Level.INFO, "My first test with {}")
                .check(text -> assertEquals("Mina", text));

        new Simple().doSomething();
    }

    @Test
    public void testPartialLoggerName() {
        Mina
                .on("mina.test", Level.INFO, null, "My first test with {}")
                .check(text -> assertEquals("Mina", text));

        new Simple().doSomething();
    }

    @Test
    public void testException() {
        Mina
                .on(Level.ERROR)
                .checkThrowable((throwable) -> assertInstanceOf(RuntimeException.class, throwable));

        new Simple().doException();
    }

    @Test
    public void testExceptionWithArguments() {
        Mina
                .on(Level.ERROR)
                .check((arguments, throwable) -> {
                    assertInstanceOf(RuntimeException.class, throwable);
                    assertEquals("Vitalii", arguments[0]);
                    assertNull(arguments[1]);
                });

        new Simple().doException();
    }

    @Test
    public void testForbidden() {
        Mina.on(Level.ERROR).exception();

        assertThrows(AssertionError.class, () -> new Simple().doException());
    }
}
