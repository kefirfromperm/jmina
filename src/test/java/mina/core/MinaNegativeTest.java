package mina.core;

import mina.exception.ArgumentsCountException;
import mina.subject.EmptyCode;
import mina.subject.Simple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.opentest4j.IncompleteExecutionException;
import org.opentest4j.MultipleFailuresError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static mina.core.Mina.assertAllCalled;
import static mina.core.Mina.on;
import static org.junit.jupiter.api.Assertions.*;
import static org.slf4j.event.Level.ERROR;
import static org.slf4j.event.Level.INFO;

@SuppressWarnings("LoggingSimilarMessage")
public class MinaNegativeTest {
    Logger log = LoggerFactory.getLogger(MinaNegativeTest.class);

    @Test
    public void testDoNothing() {
        on(EmptyCode.class, INFO, "Log something: {}")
                .checkArguments(arguments -> assertEquals(3, arguments.length));

        new EmptyCode().doNothing();

        assertThrows(IncompleteExecutionException.class, Mina::assertAllCalled);
    }

    @Test
    public void testForbidden() {
        on(ERROR).exception();

        assertThrows(AssertionError.class, () -> new Simple().doException());

        assertAllCalled();
    }

    @Test
    public void testSingleArgumentMismatchCount() {
        on(INFO).check((String str) -> assertTrue(true));
        assertThrows(ArgumentsCountException.class, () -> log.info("test"));
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
        assertThrows(ArgumentsCountException.class, () -> log.info("test {}", "test"));
        assertAllCalled();
    }

    @Test
    public void testTwoArgumentsZeroCount() {
        on(INFO).check((String str, Integer count) -> assertTrue(true));
        assertThrows(ArgumentsCountException.class, () -> log.info("test"));
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
        assertThrows(ArgumentsCountException.class, () -> log.info("test {} {}", "test", 5));
        assertAllCalled();
    }

    @Test
    public void testThreeArgumentsZeroCount() {
        on(INFO).check((String str, Integer count, Double pi) -> assertTrue(true));
        assertThrows(ArgumentsCountException.class, () -> log.info("test"));
        assertAllCalled();
    }

    @Test
    public void testThreeArgumentsMismatchType() {
        on(INFO).check((String str, Integer count, Double pi) -> assertTrue(true));
        assertThrows(ClassCastException.class, () -> log.info("test {} {} {}", "test", 5, "3.14"));
        assertAllCalled();
    }

    @Test
    public void testFourArgumentsMismatchCount() {
        on(INFO).check((String str, Integer count, Double pi, Short sh) -> assertTrue(true));
        assertThrows(ArgumentsCountException.class, () -> log.info("test {} {} {}", "test", 5, 3.14));
        assertAllCalled();
    }

    @Test
    public void testFourArgumentsZeroCount() {
        on(INFO).check((String str, Integer count, Double pi, Short sh) -> assertTrue(true));
        assertThrows(ArgumentsCountException.class, () -> log.info("test"));
        assertAllCalled();
    }

    @Test
    public void testFourArgumentsMismatchType() {
        on(INFO).check((String str, Integer count, Double pi, Short sh) -> assertTrue(true));
        assertThrows(ClassCastException.class, () -> log.info("test {} {} {} {}", "test", 5, 3.14, 12L));
        assertAllCalled();
    }

    @Test
    public void testFiveArgumentsMismatchCount() {
        on(INFO).check((String str, Integer count, Double pi, Short sh, Float f) -> assertTrue(true));
        assertThrows(ArgumentsCountException.class, () -> log.info("test {} {} {} {}", "test", 5, 3.14, 13));
        assertAllCalled();
    }

    @Test
    public void testFiveArgumentsZeroCount() {
        on(INFO).check((String str, Integer count, Double pi, Short sh, Float f) -> assertTrue(true));
        assertThrows(ArgumentsCountException.class, () -> log.info("test"));
        assertAllCalled();
    }

    @Test
    public void testFiveArgumentsMismatchType() {
        on(INFO).check((String str, Integer count, Double pi, Short sh, Float f) -> assertTrue(true));
        assertThrows(ClassCastException.class, () -> log.info("test {} {} {} {} {}", "test", 5, 3.14, 12, 'e'));
        assertAllCalled();
    }

    @Test
    public void testSixArgumentsMismatchCount() {
        on(INFO).check((String str, Integer count, Double pi, Short sh, Float f, Character c) -> assertTrue(true));
        assertThrows(ArgumentsCountException.class, () -> log.info("test {} {} {} {} {}", "test", 5, 3.14, 13, 2.73f));
        assertAllCalled();
    }

    @Test
    public void testSixArgumentsZeroCount() {
        on(INFO).check((String str, Integer count, Double pi, Short sh, Float f, Character c) -> assertTrue(true));
        assertThrows(ArgumentsCountException.class, () -> log.info("test"));
        assertAllCalled();
    }

    @Test
    public void testSixArgumentsMismatchType() {
        on(INFO).check((String str, Integer count, Double pi, Short sh, Float f, Character c) -> assertTrue(true));
        assertThrows(
                ClassCastException.class, () -> log.info("test {} {} {} {} {} {}", "test", 5, 3.14, 12, 2.73f, "c"));
        assertAllCalled();
    }

    @Test
    public void testArgumentsComparisonLess() {
        on(INFO).check(1, "2");
        assertThrows(AssertionFailedError.class, () -> log.info("test {}", 1));
        assertAllCalled();
    }

    @Test
    public void testArgumentsComparisonMore() {
        on(INFO).check(1, "2");
        assertThrows(AssertionFailedError.class, () -> log.info("test {} {} {}", 1, "2", 3.));
        assertAllCalled();
    }

    @Test
    public void testSingleArgumentMismatch() {
        on(INFO).check(1, "2");
        assertThrows(AssertionFailedError.class, () -> log.info("test {} {}", 1, "3"));
        assertAllCalled();
    }

    @Test
    public void testArgumentsMismatch() {
        on(INFO).check(1, "2");
        assertThrows(MultipleFailuresError.class, () -> log.info("test {} {}", 2, "3"));
        assertAllCalled();
    }

    @Test
    public void testArgumentsTypeMismatch() {
        on(INFO).check(1, "2");
        assertThrows(AssertionFailedError.class, () -> log.info("test {} {}", 1, 1));
        assertAllCalled();
    }

    @Test
    public void testArgumentsNull() {
        assertThrows(IllegalArgumentException.class, () -> on(INFO).check((Object[]) null));
    }

    @Test
    public void testNullCheck() {
        assertThrows(IllegalArgumentException.class, () -> on(INFO).checkCanonical(null));
        assertThrows(IllegalArgumentException.class, () -> on(INFO).check((NoArgumentsCheck) null));
        assertThrows(IllegalArgumentException.class, () -> on(INFO).check((SingleArgumentCheck<?>) null));
        assertThrows(IllegalArgumentException.class, () -> on(INFO).check((TwoArgumentsCheck<?, ?>) null));
        assertThrows(IllegalArgumentException.class, () -> on(INFO).check((ThreeArgumentsCheck<?, ?, ?>) null));
        assertThrows(IllegalArgumentException.class, () -> on(INFO).checkArguments((ArrayArgumentsCheck) null));
        assertThrows(IllegalArgumentException.class, () -> on(INFO).checkArguments((ArgumentsThrowableCheck) null));
        assertThrows(IllegalArgumentException.class, () -> on(INFO).checkThrowable(null));
    }

    @AfterEach
    public void clean() {
        Mina.clean();
    }
}
