package mina.test;

import mina.core.Mina;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.slf4j.event.Level.DEBUG;

public class QuadraticEquationTest {
    @Test
    public void testSolve() {
        Mina.when(QuadraticEquation.class, DEBUG, "discriminant: {}")
                .then(args ->
                              // Verify discriminant value inside the solve method
                              assertEquals(9., (double) args[0])
                );

        // Run our code
        List<Double> roots = new QuadraticEquation().solve(1, -1, -2);

        // Verify that all logs were called
        Mina.assertAllCalled();

        // Verify roots
        assertEquals(-1, roots.get(0));
        assertEquals(2, roots.get(1));
    }

    @AfterEach
    public void clean() {
        Mina.clean();
    }
}
