package mina.core;


import mina.subject.EmptyCode;
import mina.subject.Simple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static mina.core.Mina.assertAllCalled;
import static mina.core.Mina.on;
import static org.junit.jupiter.api.Assertions.*;
import static org.slf4j.event.Level.*;

public class MinaTest {
    @AfterEach
    public void clean() {
        Mina.clean();
    }

    @Test
    public void testDoNothing() {
        on(EmptyCode.class, INFO, "Log something: {}")
                .checkArguments(arguments -> assertEquals(3, arguments.length));

        new EmptyCode().doNothing();

        assertThrows(AssertionError.class, Mina::assertAllCalled);
    }

    @Test
    public void testSomething() {
        on(Simple.class, TRACE, "Trace something").check();
        on(Simple.class, DEBUG, Simple.TEST_MARKER, "Debug something").check();
        on(Simple.class, INFO, "This is a {} log").check(3);
        on(Simple.class, WARN, "Warn {}").check((String problem, Throwable throwable) -> {
            assertEquals("problem", problem);
            assertInstanceOf(Exception.class, throwable);
        });
        on(Simple.class, ERROR, "Test error")
                .check((Throwable throwable) -> assertInstanceOf(RuntimeException.class, throwable));

        new Simple().doSomething();

        assertAllCalled();
    }

    @Test
    public void testOnlyLoggerCondition() {
        on(Simple.class).check();
        new Simple().doSingleLog();
        assertAllCalled();
    }

    @Test
    public void testOnlyLevelCondition() {
        on(INFO).check();
        new Simple().doSingleLog();
        assertAllCalled();
    }

    @Test
    public void testLoggerLevelCondition() {
        on(Simple.class, INFO).check();
        new Simple().doSingleLog();
        assertAllCalled();
    }

    @Test
    public void testNoCondition() {
        on().check();
        new Simple().doSingleLog();
        assertAllCalled();
    }

    @Test
    public void testPartialCondition() {
        on(Simple.class, DEBUG, Simple.MARKER_1).check(1);
        on(Simple.class, Simple.MARKER_2, "message 2 {}").check(2);
        on(Simple.class, Simple.MARKER_3).check(3);
        on(Simple.class, "message 4 {}").check(4);
        on(INFO, Simple.MARKER_1, "message 5 {}").check(5);
        on(INFO, Simple.MARKER_2).check(6);
        on(INFO, "message 7 {}").check(7);
        on(Simple.MARKER_1, "message 8 {}").check(8);
        on(Simple.TEST_MARKER).check(9);
        on("message 10 {}").check(10);

        new Simple().doConditions();
        assertAllCalled();
    }

    @Test
    public void testMultiArguments() {
        // No arguments
        on("no arguments").check();

        // One argument
        on(INFO, "{}").check((Integer arg) -> assertEquals(1, arg));
        on(ERROR, "no arguments")
                .check((Throwable throwable) -> assertEquals("Test runtime exception 1", throwable.getMessage()));

        // Two arguments
        on(INFO, "{} {}").check((Integer arg1, String arg2) -> {
            assertEquals(2, arg1);
            assertEquals("test 1", arg2);
        });
        on(ERROR, "{}").check((Integer arg, Throwable throwable) -> {
            assertEquals(3, arg);
            assertEquals("Test runtime exception 2", throwable.getMessage());
        });

        // Three arguments
        on(INFO, "{} {} {}").check((Integer arg1, String arg2, Character arg3) -> {
            assertEquals(4, arg1);
            assertEquals("test 2", arg2);
            assertEquals('c', arg3);
        });
        on(ERROR, "{} {}").check((Integer arg1, String arg2, Throwable throwable) -> {
            assertEquals(5, arg1);
            assertEquals("test 3", arg2);
            assertEquals("Test runtime exception 3", throwable.getMessage());
        });

        new Simple().doMultiArguments();
        assertAllCalled();
    }

    @Test
    public void testMultiArgumentsUsingArray() {
        // No arguments
        on("no arguments").check();

        // One argument
        on(INFO, "{}").checkArguments((Object[] args) -> assertEquals(1, args[0]));
        on(ERROR, "no arguments").checkThrowable((Throwable throwable) ->
                                                         assertEquals(
                                                                 "Test runtime exception 1", throwable.getMessage())
        );

        // Two arguments
        on(INFO, "{} {}").checkArguments((Object[] args) -> {
            assertEquals(2, args[0]);
            assertEquals("test 1", args[1]);
        });
        on(ERROR, "{}").checkArguments((Object[] args, Throwable throwable) -> {
            assertEquals(3, args[0]);
            assertEquals("Test runtime exception 2", throwable.getMessage());
        });

        // Three arguments
        on(INFO, "{} {} {}").checkArguments((Object[] args) -> {
            assertEquals(4, args[0]);
            assertEquals("test 2", args[1]);
            assertEquals('c', args[2]);
        });
        on(ERROR, "{} {}").checkArguments((Object[] args, Throwable throwable) -> {
            assertEquals(5, args[0]);
            assertEquals("test 3", args[1]);
            assertEquals("Test runtime exception 3", throwable.getMessage());
        });

        new Simple().doMultiArguments();
        assertAllCalled();
    }

    @Test
    public void testIndex() {
        AtomicInteger count = new AtomicInteger();
        on(Simple.class).checkCanonical((index, arguments, throwable) -> assertEquals(count.incrementAndGet(), index));

        new Simple().doSomething();

        assertAllCalled();
    }

    @Test
    public void testPartialLoggerName() {
        on("mina.subject", INFO, null, null).check();

        new Simple().doSomething();

        assertAllCalled();
    }

    @Test
    public void testException() {
        on(ERROR)
                .checkThrowable((throwable) -> assertInstanceOf(RuntimeException.class, throwable));

        new Simple().doException();

        assertAllCalled();
    }

    @Test
    public void testExceptionWithArguments() {
        on(ERROR)
                .checkArguments((arguments, throwable) -> {
                    assertInstanceOf(RuntimeException.class, throwable);
                    assertEquals("Vitalii", arguments[0]);
                    assertNull(arguments[1]);
                });

        new Simple().doException();

        assertAllCalled();
    }

    @Test
    public void testForbidden() {
        on(ERROR).exception();

        assertThrows(AssertionError.class, () -> new Simple().doException());

        assertAllCalled();
    }
}
