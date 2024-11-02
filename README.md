# JMina for unit tests

[![build](https://github.com/kefirfromperm/jmina/actions/workflows/build.yml/badge.svg)](https://github.com/kefirfromperm/jmina/actions/workflows/build.yml)
[![Maven Central Version](https://img.shields.io/maven-central/v/dev.jmina/jmina)](https://central.sonatype.com/artifact/dev.jmina/jmina)
[![GitHub License](https://img.shields.io/github/license/kefirfromperm/jmina)](LICENSE)
[![javadoc](https://javadoc.io/badge2/dev.jmina/jmina/javadoc.svg)](https://javadoc.io/doc/dev.jmina/jmina)

> [!NOTE]
> JMina is not [Apache MINA](https://mina.apache.org/) and is not associated with it in any way. Unfortunately, I
> remembered about it after I registered the domain name.

JMina allows to validate variables anywhere in your code during unit test. It implements [Slf4j](https://www.slf4j.org/)
logger interface and allows you to do not make valuable changes in your code to use JMina. In a unit test you can add
any specific verifications for you log calls.

```java
on(MyClass.class, TRACE, "My variable {}").check(val -> assertEquals("TEST", val));
```

## Configure

JMina is published in Maven Central repository. To start to use it just add JMina to the dependencies section in your 
Gradle or Maven file.

```kotlin
dependencies {
    testImplementation("dev.jmina:jmina:0.1.4")
}
```

Configure JMina. Define system properties for unit tests.

| System Property           | Value                                                       | Description                                                              |
|---------------------------|-------------------------------------------------------------|--------------------------------------------------------------------------|
| `slf4j.provider`          | `dev.jmina.log.MinaServiceProvider`                         | Use JMina as a primary SLF4J provider. Mandatory.                        |
| `jmina.delegate.provider` | An SLF4J service provider of your preffered logging library | Configure JMina proxy to use your preferred SLF4J provider as a delegate |
| `jmina.context.global`    | `true` or `false`                                           | User global context store or thread local                                |

Gradle example
```kotlin
tasks {
    test {
        systemProperty("slf4j.provider", "dev.jmina.log.MinaServiceProvider")
        systemProperty("jmina.delegate.provider", "ch.qos.logback.classic.spi.LogbackServiceProvider")
        systemProperty("jmina.context.global", "true")
    }
}
```

### Global Context Store vs. Thread Local Context Store

JMina works in two modes - global context store and thread local context store. When you choose to use a global
context store JMina create a single context for all threads. In case of a thread local context store it creates contexts
for each thread in a thread local variables.

The *global context store* is suitable for testing a multithreading code in a single thread test environment. It is
incompatible with multithreading tests.

The *thread local context* store is used for a single thread code and a multithreading test environment. It's
incompatible
with a multithreading code.

## Quick Example

Iif you want to test a simple class which solves quadratic equation. Do you want to check the discriminant? Of course!
Just add a log call to the right place and put there the discriminant value.
```java
public class QuadraticEquation {
    private final Logger log = LoggerFactory.getLogger(QuadraticEquation.class);

    public List<Double> solve(double a, double b, double c) {
        double discriminant = b * b - 4 * a * c;

        log.debug("discriminant: {}", discriminant);    // Log the discriminant value to verify it during test execution

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

Then use JMina checks in the unit test.
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
        // Don't forget to clean-up context after each test
        Mina.clean();
    }
}
```
[QuadraticEquationTest.java](src/test/java/dev/jmina/example/QuadraticEquationTest.java)

