package mina.core;

import mina.subject.Simple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static mina.core.Mina.assertAllCalled;
import static mina.core.Mina.on;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.slf4j.event.Level.ERROR;
import static org.slf4j.event.Level.INFO;

public class MinaNegativeTest {
    Logger log = LoggerFactory.getLogger(MinaNegativeTest.class);

    @Test
    public void testForbidden() {
        on(ERROR).exception();

        assertThrows(AssertionError.class, () -> new Simple().doException());

        assertAllCalled();
    }

    @Test
    public void testSingleArgumentMismatchCount() {
        on(INFO).check((String str) -> assertTrue(true));
        assertThrows(IllegalArgumentException.class, () -> log.info("test"));
        assertAllCalled();
    }

    @Test
    public void testSingleArgumentMismatchType() {
        on(INFO).check((String str) -> assertTrue(true));
        assertThrows(ClassCastException.class, () -> log.info("test mismatch type {}", 42));
        assertAllCalled();
    }

    @Test
    public void testTwoArgumentsMismatchCount() {
        on(INFO).check((String str, Integer count) -> assertTrue(true));
        assertThrows(IllegalArgumentException.class, () -> log.info("test {}", "test"));
        assertAllCalled();
    }

    @Test
    public void testTwoArgumentsZeroCount() {
        on(INFO).check((String str, Integer count) -> assertTrue(true));
        assertThrows(IllegalArgumentException.class, () -> log.info("test"));
        assertAllCalled();
    }

    @Test
    public void testTwoArgumentsMismatchType() {
        on(INFO).check((String str, Integer count) -> assertTrue(true));
        assertThrows(ClassCastException.class, () -> log.info("test {} {}", "test", "test"));
        assertAllCalled();
    }

    @Test
    public void testThreeArgumentsMismatchCount() {
        on(INFO).check((String str, Integer count, Double pi) -> assertTrue(true));
        assertThrows(IllegalArgumentException.class, () -> log.info("test {} {}", "test", 5));
        assertAllCalled();
    }

    @Test
    public void testThreeArgumentsZeroCount() {
        on(INFO).check((String str, Integer count, Double pi) -> assertTrue(true));
        assertThrows(IllegalArgumentException.class, () -> log.info("test"));
        assertAllCalled();
    }

    @Test
    public void testThreeArgumentsMismatchType() {
        on(INFO).check((String str, Integer count, Double pi) -> assertTrue(true));
        assertThrows(ClassCastException.class, () -> log.info("test {} {} {}", "test", 5, "3.14"));
        assertAllCalled();
    }

    @Test
    public void testArgumentsComparisonLess() {
        on(INFO).check(1, "2");
        assertThrows(AssertionError.class, () -> log.info("test {}", 1));
        assertAllCalled();
    }

    @Test
    public void testArgumentsComparisonMore() {
        on(INFO).check(1, "2");
        assertThrows(AssertionError.class, () -> log.info("test {} {} {}", 1, "2", 3.));
        assertAllCalled();
    }

    @Test
    public void testArgumentsMismatch() {
        on(INFO).check(1, "2");
        assertThrows(AssertionError.class, () -> log.info("test {} {}", 2, "3"));
        assertAllCalled();
    }

    @Test
    public void testArgumentsTypeMismatch() {
        on(INFO).check(1, "2");
        assertThrows(AssertionError.class, () -> log.info("test {} {}", 1, 1));
        assertAllCalled();
    }

    @Test
    public void testArgumentsNull() {
        assertThrows(IllegalArgumentException.class, () -> on(INFO).check((Object[]) null));
    }

    @AfterEach
    public void clean() {
        Mina.clean();
    }
}
