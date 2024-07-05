# Mina

[![build](actions/workflows/gradle.yml/badge.svg)](actions/workflows/gradle.yml)

Mina is a library that extends the capabilities of unit tests in Java. Mina implements the Slf4j logger interface, so
you can check the values of variables anywhere in your code during test execution.

For example, if you want to test a simple class which solves quadratic equation. Do you want to check the discriminant?
Just add a log call in right place.

```java
public class QuadraticEquation {
    private final Logger log = LoggerFactory.getLogger(QuadraticEquation.class);

    public List<Double> solve(double a, double b, double c) {
        double discriminant = b * b - 4 * a * c;

        log.debug("discriminant: {}", discriminant);

        if (discriminant < 0) {
            return Collections.emptyList();
        } else {
            List<Double> roots = new ArrayList<>();

            if (discriminant > 0) {
                roots.add((-b - sqrt(discriminant)) / (2 * a));
                roots.add((-b + sqrt(discriminant)) / (2 * a));
            } else {
                roots.add(-b / (2 * a));
            }

            return roots;
        }
    }
}
```

Then in the unit-test use Mina.

```java
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
```

