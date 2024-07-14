package mina.core;

import org.junit.jupiter.api.Test;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.event.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ConditionTest {
    private final Marker TEST = MarkerFactory.getMarker("TEST");
    private final Marker WRONG = MarkerFactory.getMarker("WRONG");

    @Test
    public void testEquals() {
        // Given a condition
        Condition condition = new Condition("test", Level.ERROR, TEST, "test {}");

        assertEquals(condition, new Condition("test", Level.ERROR, TEST, "test {}"));

        assertNotEquals(condition, new Condition("test1", Level.ERROR, TEST, "test {}"));
        assertNotEquals(condition, new Condition("test", Level.WARN, TEST, "test {}"));
        assertNotEquals(condition, new Condition("test", Level.ERROR, WRONG, "test {}"));
        assertNotEquals(condition, new Condition("test", Level.ERROR, TEST, "test1 {}"));
    }
}
