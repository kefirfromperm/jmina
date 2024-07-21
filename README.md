# JMina for unit tests

[![build](https://github.com/kefirfromperm/jmina/actions/workflows/build.yml/badge.svg)](https://github.com/kefirfromperm/jmina/actions/workflows/build.yml)
[![GitHub Release](https://img.shields.io/github/v/release/kefirfromperm/jmina)](https://github.com/kefirfromperm/jmina/packages/2209486)
[![GitHub License](https://img.shields.io/github/license/kefirfromperm/jmina)](LICENSE)

> [!NOTE]
> JMina is not [Apache MINA](https://mina.apache.org/) and is not associated with it in any way. Unfortunately, I
> remembered about it after I registered the domain name.

JMina is a tool library that extends the capabilities of unit tests in Java. JMina implements the Slf4j logger
interface, so you can check the values of variables anywhere in your code during test execution.

## Configure

Right now JMina is published only in the GitHub packages repository. So first of all add the repository to you
configuration.

```kotlin
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/kefirfromperm/jmina")
    }
}
```

Then add JMina to the dependencies section.

```kotlin
dependencies {
    // ...

    // Test dependencies
    testImplementation("dev.jmina:jmina:0.1.1")
}
```

Finally configure JMina as a primary Slf4j provider.

```kotlin
tasks {
    test {
        // Use JMina as an Slf4j provider
        systemProperty("slf4j.provider", "dev.jmina.log.MinaServiceProvider")

        // Configure JMina proxy to use your preferred logging provider as a delegate 
        systemProperty("jmina.delegate.provider", "org.slf4j.simple.SimpleServiceProvider")
    }
}
```

## Overview

For example, if you want to test a simple class which solves quadratic equation. Do you want to check the discriminant?
Just add a log call to the right place.

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

[QuadraticEquation.java](src/test/java/dev/jmina/example/QuadraticEquation.java)

Then use JMina in the unit test.

```java
public class QuadraticEquationTest {
    @Test
    public void testSolve() {
        // Verify discriminant value inside the solve method
        Mina
                .on(QuadraticEquation.class, DEBUG, "discriminant: {}")
                .check((Double discriminant) -> assertEquals(9, discriminant));

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
```

[QuadraticEquationTest.java](src/test/java/dev/jmina/example/QuadraticEquationTest.java)

