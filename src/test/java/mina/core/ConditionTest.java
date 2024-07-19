package mina.core;

import org.junit.jupiter.api.Test;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.event.Level;

import static org.junit.jupiter.api.Assertions.*;

public class ConditionTest {
    private final Marker TEST = MarkerFactory.getMarker("TEST");
    private final Marker WRONG = MarkerFactory.getMarker("WRONG");

    @Test
    public void testEquals() {
        // Given a condition
        Condition condition = new Condition("test", Level.ERROR, TEST, "test {}");

        //noinspection EqualsWithItself
        assertEquals(condition, condition);
        assertNotEquals(condition, null);
        assertEquals(condition, new Condition("test", Level.ERROR, TEST, "test {}"));

        assertNotEquals(condition, new Condition("test1", Level.ERROR, TEST, "test {}"));
        assertNotEquals(condition, new Condition("test", Level.WARN, TEST, "test {}"));
        assertNotEquals(condition, new Condition("test", Level.ERROR, WRONG, "test {}"));
        assertNotEquals(condition, new Condition("test", Level.ERROR, TEST, "test1 {}"));
    }

    @Test
    public void testMatch() {
        // When
        Condition condition = new Condition("test", Level.ERROR, TEST, "test {}");

        // Then
        assertFalse(condition.match(null, Level.ERROR, TEST, "test {}"));
        assertTrue(condition.match("test.test", Level.ERROR, TEST, "test {}"));

        // When condition with empty logger
        Condition conditionNullLogger = new Condition(null, Level.ERROR, TEST, "test {}");

        // Then
        assertTrue(conditionNullLogger.match(null, Level.ERROR, TEST, "test {}"));
        assertTrue(conditionNullLogger.match("test.test", Level.ERROR, TEST, "test {}"));
    }
}
