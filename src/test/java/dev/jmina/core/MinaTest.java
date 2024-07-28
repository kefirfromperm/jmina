package dev.jmina.core;


import dev.jmina.subject.Simple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static dev.jmina.core.Mina.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.slf4j.event.Level.*;

public class MinaTest {
    private final Logger log = LoggerFactory.getLogger(MinaTest.class);

    @AfterEach
    public void clean() {
        Mina.clean();
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
    public void testOnlyLoggerNameCondition() {
        onLoggerName("dev.jmina.subject.Simple").check();
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
    public void testLoggerNameLevelCondition() {
        on("dev.jmina.subject.Simple", INFO).check();
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
        on(Simple.class, ERROR, "message 11 {}").check(11);

        new Simple().doConditions();
        assertAllCalled();
    }

    @Test
    public void testLoggerNamePartialCondition() {
        on("dev.jmina.subject.Simple", DEBUG, Simple.MARKER_1).check(1);
        on("dev.jmina.subject.Simple", Simple.MARKER_2, "message 2 {}").check(2);
        on("dev.jmina.subject.Simple", Simple.MARKER_3).check(3);
        on("dev.jmina.subject.Simple", "message 4 {}").check(4);
        on("dev.jmina.subject.Simple", ERROR, "message 11 {}").check(11);

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

        // Four arguments
        on(INFO, "{} {} {} {}").check((Integer arg1, String arg2, Character arg3, Double arg4) -> {
            assertEquals(6, arg1);
            assertEquals("test 6", arg2);
            assertEquals('a', arg3);
            assertEquals(9., arg4);
        });
        on(ERROR, "{} {} {}").check((Integer arg1, String arg2, Character arg3, Throwable throwable) -> {
            assertEquals(7, arg1);
            assertEquals("test 7", arg2);
            assertEquals('b', arg3);
            assertEquals("Test runtime exception 7", throwable.getMessage());
        });

        // Five arguments
        on(INFO, "{} {} {} {} {}").check((Integer arg1, String arg2, Character arg3, Character arg4, Double arg5) -> {
            assertEquals(8, arg1);
            assertEquals("test 8", arg2);
            assertEquals('c', arg3);
            assertEquals('d', arg4);
            assertEquals(23.56, arg5);
        });
        on(ERROR, "{} {} {} {}").check(
                (Integer arg1, String arg2, Character arg3, Character arg4, Throwable throwable) -> {
                    assertEquals(9, arg1);
                    assertEquals("test 9", arg2);
                    assertEquals('c', arg3);
                    assertEquals('d', arg4);
                    assertEquals("Test runtime exception 9", throwable.getMessage());
                });

        // Six arguments
        on(INFO, "{} {} {} {} {} {}").check(
                (Integer arg1, String arg2, Character arg3, Character arg4, Double arg5, String arg6) -> {
                    assertEquals(10, arg1);
                    assertEquals("test 10", arg2);
                    assertNull(arg3);
                    assertEquals('d', arg4);
                    assertEquals(23.56, arg5);
                    assertEquals("r", arg6);
                });
        on(ERROR, "{} {} {} {} {}").check(
                (Integer arg1, String arg2, Character arg3, Character arg4, Double arg5, Throwable throwable) -> {
                    assertEquals(11, arg1);
                    assertEquals("test 11", arg2);
                    assertNull(arg3);
                    assertEquals('d', arg4);
                    assertEquals(23.56, arg5);
                    assertEquals("Test runtime exception 11", throwable.getMessage());
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

        assertEquals(5, count.get());

        assertAllCalled();
    }

    @Test
    public void testMdc() {
        on(INFO).check(() -> assertEquals("value", MDC.get("key")));
        new Simple().doMdc();
        assertNull(MDC.get("key"));
        assertAllCalled();
    }

    @Test
    public void testSwitchGlobalToThreadLocal() {
        Mina.useGlobalContext();
        Mina.useThreadLocalContext();
    }

    @Test
    public void testSwitchThreadLocalToGlobal() {
        Mina.useThreadLocalContext();
        Mina.useGlobalContext();
    }

    @Test
    public void testParallel() {
        // Just initialize Common Pool
        Arrays.asList(1, 2, 3, 4, 5).parallelStream().forEach(value -> log.info("value {}", value));

        // So context will not be propagated to the threads of common fork join pool
        // And the test will not work properly in a thread local context
        Mina.useGlobalContext();

        AtomicInteger count = new AtomicInteger();
        on(Simple.class).checkCanonical(
                (index, arguments, throwable) -> count.incrementAndGet()
        );

        new Simple().doParallel();

        assertEquals(5, count.get());

        assertAllCalled();
    }

    @Test
    public void testParallelTests() throws InterruptedException {
        // When using global context
        Mina.useGlobalContext();

        assertDoesNotThrow(() -> on().check());

        // We try to run Mina in parallel tests
        try {
            Executors.newSingleThreadExecutor().submit(() -> {
                log.info("a parallel test");
                on().check();
            }).get();
            fail();
        } catch (ExecutionException interruptedException) {
            Throwable cause = interruptedException.getCause();
            assertNotNull(cause);
            assertInstanceOf(IllegalStateException.class, cause);
            assertTrue(cause.getMessage().startsWith("Mina is configured to use GLOBAL context"));
        }
    }

    @Test
    public void testPartialLoggerName() {
        on("dev.jmina.subject", INFO, null, null).check();

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
    public void testForbiddenNotCalled() {
        on(ERROR).exception();
        on(INFO).check();

        log.info("Test forbidden not called");

        assertAllCalled();
    }
}
