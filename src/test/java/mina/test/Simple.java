package mina.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Simple {
    private final Logger log = LoggerFactory.getLogger(Simple.class);

    public void toDoSomething() {
        log.info("My first test with {}", "Mina");
    }
}
