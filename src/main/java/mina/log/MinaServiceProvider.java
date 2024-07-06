package mina.log;

import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.helpers.BasicMDCAdapter;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.helpers.Reporter;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class MinaServiceProvider implements SLF4JServiceProvider {
    public static final String DELEGATE_PROVIDER_PROPERTY_KEY = "mina.delegate.provider";
    /**
     * Declare the version of the SLF4J API this implementation is compiled against.
     * The value of this field is modified with each major release.
     */
    // to avoid constant folding by the compiler, this field must *not* be final
    public static String REQUESTED_API_VERSION = "2.0.99"; // !final

    private ILoggerFactory loggerFactory;
    private IMarkerFactory markerFactory;
    private MDCAdapter mdcAdapter;

    private SLF4JServiceProvider delegate;

    public ILoggerFactory getLoggerFactory() {
        return loggerFactory;
    }

    @Override
    public IMarkerFactory getMarkerFactory() {
        return markerFactory;
    }

    @Override
    public MDCAdapter getMDCAdapter() {
        return mdcAdapter;
    }

    @Override
    public String getRequestedApiVersion() {
        return REQUESTED_API_VERSION;
    }

    @Override
    public void initialize() {
        loadDelegate();

        loggerFactory = delegate != null ? new MinaLoggerFactory(delegate.getLoggerFactory()) : new MinaLoggerFactory();
        markerFactory = delegate != null ? delegate.getMarkerFactory() : new BasicMarkerFactory();
        mdcAdapter = delegate != null ? delegate.getMDCAdapter() : new BasicMDCAdapter();
    }

    private void loadDelegate() {
        String delegateProviderClassName = System.getProperty(DELEGATE_PROVIDER_PROPERTY_KEY);

        if (null == delegateProviderClassName || delegateProviderClassName.isEmpty()) {
            return;
        }

        try {
            String message = String.format("Attempting to load Mina's delegate provider \"%s\" specified via \"%s\" system property", delegateProviderClassName, DELEGATE_PROVIDER_PROPERTY_KEY);
            Reporter.info(message);
            Class<?> clazz = this.getClass().getClassLoader().loadClass(delegateProviderClassName);
            Constructor<?> constructor = clazz.getConstructor();
            delegate = (SLF4JServiceProvider) constructor.newInstance();
            delegate.initialize();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            String message = String.format("Failed to instantiate the specified SLF4JServiceProvider (%s)", delegateProviderClassName);
            Reporter.error(message, e);
        } catch (ClassCastException e) {
            String message = String.format("Specified SLF4JServiceProvider (%s) does not implement SLF4JServiceProvider interface", delegateProviderClassName);
            Reporter.error(message, e);
        }

        Reporter.info("Load a Mina's delegate Slf4jServiceProvider " + delegate.getClass().getName());
    }
}
