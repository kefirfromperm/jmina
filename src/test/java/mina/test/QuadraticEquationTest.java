package mina.test;

import mina.core.Mina;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.event.Level;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuadraticEquationTest {
    @Test
    public void testSolve() {
        Mina.when(QuadraticEquation.class, Level.DEBUG, "discriminant: {}")
                .then((Object[] args) ->
                        assertEquals(9., (double) args[0])
                );

        List<Double> roots = new QuadraticEquation().solve(1, -1, -2);

        assertEquals(-1, roots.get(0));
        assertEquals(2, roots.get(1));
    }

    @AfterEach
    public void clean() {
        Mina.clean();
    }
}
