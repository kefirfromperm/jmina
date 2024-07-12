package mina.log;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.simple.SimpleLogger;
import org.slf4j.simple.SimpleLoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class MinaLoggerFactoryTest {
    /**
     * Test create a logger without proxy
     */
    @Test
    public void testNoProxyFactory() {
        assertInstanceOf(MinaLogger.class, new MinaLoggerFactory().getLogger("test"));
    }

    @Test
    public void testProxyFactory() {
        Logger logger = new MinaLoggerFactory(new SimpleLoggerFactory()).getLogger("test");
        assertInstanceOf(ProxyLogger.class, logger);
        ProxyLogger proxyLogger = (ProxyLogger) logger;
        List<Logger> delegates = proxyLogger.getDelegates();
        assertEquals(2, delegates.size());
        assertInstanceOf(SimpleLogger.class, delegates.get(0));
        assertInstanceOf(MinaLogger.class, delegates.get(1));
    }
}
