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
                .when(EmptyCode.class, Level.INFO, "Log something: {}")
                .then(arguments -> {
                    assertEquals(3, arguments.length);
                });

        new EmptyCode().doNothing();

        assertThrows(AssertionError.class, Mina::verifyLost);
    }

    @Test
    public void testSomething() {
        Mina
                .when("mina.test.Simple", Level.INFO, null, "My first test with {}")
                .then((arguments) -> assertEquals("Mina", arguments[0]));

        new Simple().doSomething();
    }

    @Test
    public void testIndex() {
        Mina
                .when("mina.test.Simple", Level.INFO, null, "My first test with {}")
                .then((index, arguments, throwable) -> {
                    assertEquals("Mina", arguments[0]);
                    assertEquals(1, index);
                });

        new Simple().doSomething();
    }

    @Test
    public void testLoggerClass() {
        Mina
                .when(Simple.class, Level.INFO, "My first test with {}")
                .then((arguments) -> assertEquals("Mina", arguments[0]));

        new Simple().doSomething();
    }

    @Test
    public void testPartialLoggerName() {
        Mina
                .when("mina.test", Level.INFO, null, "My first test with {}")
                .then((arguments) -> assertEquals("Mina", arguments[0]));

        new Simple().doSomething();
    }

    @Test
    public void testException() {
        Mina
                .when(null, Level.ERROR, null, null)
                .thenThrowable((throwable) -> assertInstanceOf(RuntimeException.class, throwable));

        new Simple().doException();
    }

    @Test
    public void testExceptionWithArguments() {
        Mina
                .when(null, Level.ERROR, null, null)
                .then((arguments, throwable) -> {
                    assertInstanceOf(RuntimeException.class, throwable);
                    assertEquals("Vitalii", arguments[0]);
                    assertNull(arguments[1]);
                });

        new Simple().doException();
    }

    @Test
    public void testForbidden() {
        Mina.forbid(null, Level.ERROR, null, null);

        assertThrows(AssertionError.class, () -> new Simple().doException());
    }
}
