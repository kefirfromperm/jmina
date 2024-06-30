package mina.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Simple {
    private final Logger log = LoggerFactory.getLogger(Simple.class);

    public void doSomething() {
        log.info("My first test with {}", "Mina");
    }

    public void doForbidden() {
        try {
            throw new RuntimeException("Test exception");
        } catch (Exception e) {
            log.error("Test error (c) {} {} {}", "Vitalii", null, "Samolovskikh", e);
        }
    }
}
