package mina.log;

import org.slf4j.Logger;
import org.slf4j.Marker;

import java.util.Collections;
import java.util.List;

public class ProxyLogger implements Logger {
    private final String name;
    private final List<Logger> delegates;

    public ProxyLogger(String name, List<Logger> delegates) {
        this.name = name;
        this.delegates = Collections.unmodifiableList(delegates);
    }

    /**
     * For testing purposes only
     */
    List<Logger> getDelegates() {
        return delegates;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isTraceEnabled() {
        return delegates.stream().anyMatch(Logger::isTraceEnabled);
    }

    @Override
    public void trace(String msg) {
        for (Logger delegate : delegates) {
            delegate.trace(msg);
        }
    }

    @Override
    public void trace(String format, Object arg) {
        for (Logger delegate : delegates) {
            delegate.trace(format, arg);
        }
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        for (Logger delegate : delegates) {
            delegate.trace(format, arg1, arg2);
        }
    }

    @Override
    public void trace(String format, Object... arguments) {
        for (Logger delegate : delegates) {
            delegate.trace(format, arguments);
        }
    }

    @Override
    public void trace(String msg, Throwable t) {
        for (Logger delegate : delegates) {
            delegate.trace(msg, t);
        }
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return delegates.stream().anyMatch(logger -> logger.isTraceEnabled(marker));
    }

    @Override
    public void trace(Marker marker, String msg) {
        for (Logger delegate : delegates) {
            delegate.trace(marker, msg);
        }
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        for (Logger delegate : delegates) {
            delegate.trace(marker, format, arg);
        }
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        for (Logger delegate : delegates) {
            delegate.trace(marker, format, arg1, arg2);
        }
    }

    @Override
    public void trace(Marker marker, String format, Object... argArray) {
        for (Logger delegate : delegates) {
            delegate.trace(marker, format, argArray);
        }
    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        for (Logger delegate : delegates) {
            delegate.trace(marker, msg, t);
        }
    }

    @Override
    public boolean isDebugEnabled() {
        return delegates.stream().anyMatch(Logger::isDebugEnabled);
    }

    @Override
    public void debug(String msg) {
        for (Logger delegate : delegates) {
            delegate.debug(msg);
        }
    }

    @Override
    public void debug(String format, Object arg) {
        for (Logger delegate : delegates) {
            delegate.debug(format, arg);
        }
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        for (Logger delegate : delegates) {
            delegate.debug(format, arg1, arg2);
        }
    }

    @Override
    public void debug(String format, Object... arguments) {
        for (Logger delegate : delegates) {
            delegate.debug(format, arguments);
        }
    }

    @Override
    public void debug(String msg, Throwable t) {
        for (Logger delegate : delegates) {
            delegate.debug(msg, t);
        }
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return delegates.stream().anyMatch(logger -> logger.isDebugEnabled(marker));
    }

    @Override
    public void debug(Marker marker, String msg) {
        for (Logger delegate : delegates) {
            delegate.debug(marker, msg);
        }
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
        for (Logger delegate : delegates) {
            delegate.debug(marker, format, arg);
        }
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        for (Logger delegate : delegates) {
            delegate.debug(marker, format, arg1, arg2);
        }
    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {
        for (Logger delegate : delegates) {
            delegate.debug(marker, format, arguments);
        }
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {
        for (Logger delegate : delegates) {
            delegate.debug(marker, msg, t);
        }
    }

    @Override
    public boolean isInfoEnabled() {
        return delegates.stream().anyMatch(Logger::isInfoEnabled);
    }

    @Override
    public void info(String msg) {
        for (Logger delegate : delegates) {
            delegate.info(msg);
        }
    }

    @Override
    public void info(String format, Object arg) {
        for (Logger delegate : delegates) {
            delegate.info(format, arg);
        }
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        for (Logger delegate : delegates) {
            delegate.info(format, arg1, arg2);
        }
    }

    @Override
    public void info(String format, Object... arguments) {
        for (Logger delegate : delegates) {
            delegate.info(format, arguments);
        }
    }

    @Override
    public void info(String msg, Throwable t) {
        for (Logger delegate : delegates) {
            delegate.info(msg, t);
        }
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return delegates.stream().anyMatch(logger -> logger.isInfoEnabled(marker));
    }

    @Override
    public void info(Marker marker, String msg) {
        for (Logger delegate : delegates) {
            delegate.info(marker, msg);
        }
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        for (Logger delegate : delegates) {
            delegate.info(marker, format, arg);
        }
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        for (Logger delegate : delegates) {
            delegate.info(marker, format, arg1, arg2);
        }
    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {
        for (Logger delegate : delegates) {
            delegate.info(marker, format, arguments);
        }
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
        for (Logger delegate : delegates) {
            delegate.info(marker, msg, t);
        }
    }

    @Override
    public boolean isWarnEnabled() {
        return delegates.stream().anyMatch(Logger::isWarnEnabled);
    }

    @Override
    public void warn(String msg) {
        for (Logger delegate : delegates) {
            delegate.warn(msg);
        }
    }

    @Override
    public void warn(String format, Object arg) {
        for (Logger delegate : delegates) {
            delegate.warn(format, arg);
        }
    }

    @Override
    public void warn(String format, Object... arguments) {
        for (Logger delegate : delegates) {
            delegate.warn(format, arguments);
        }
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        for (Logger delegate : delegates) {
            delegate.warn(format, arg1, arg2);
        }
    }

    @Override
    public void warn(String msg, Throwable t) {
        for (Logger delegate : delegates) {
            delegate.warn(msg, t);
        }
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return delegates.stream().anyMatch(logger -> logger.isWarnEnabled(marker));
    }

    @Override
    public void warn(Marker marker, String msg) {
        for (Logger delegate : delegates) {
            delegate.warn(marker, msg);
        }
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        for (Logger delegate : delegates) {
            delegate.warn(marker, format, arg);
        }
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        for (Logger delegate : delegates) {
            delegate.warn(marker, format, arg1, arg2);
        }
    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {
        for (Logger delegate : delegates) {
            delegate.warn(marker, format, arguments);
        }
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        for (Logger delegate : delegates) {
            delegate.warn(marker, msg, t);
        }
    }

    @Override
    public boolean isErrorEnabled() {
        return delegates.stream().anyMatch(Logger::isErrorEnabled);
    }

    @Override
    public void error(String msg) {
        for (Logger delegate : delegates) {
            delegate.error(msg);
        }
    }

    @Override
    public void error(String format, Object arg) {
        for (Logger delegate : delegates) {
            delegate.error(format, arg);
        }
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        for (Logger delegate : delegates) {
            delegate.error(format, arg1, arg2);
        }
    }

    @Override
    public void error(String format, Object... arguments) {
        for (Logger delegate : delegates) {
            delegate.error(format, arguments);
        }
    }

    @Override
    public void error(String msg, Throwable t) {
        for (Logger delegate : delegates) {
            delegate.error(msg, t);
        }
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return delegates.stream().anyMatch(logger -> logger.isErrorEnabled(marker));
    }

    @Override
    public void error(Marker marker, String msg) {
        for (Logger delegate : delegates) {
            delegate.error(marker, msg);
        }
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        for (Logger delegate : delegates) {
            delegate.error(marker, format, arg);
        }
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        for (Logger delegate : delegates) {
            delegate.error(marker, format, arg1, arg2);
        }
    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {
        for (Logger delegate : delegates) {
            delegate.error(marker, format, arguments);
        }
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        for (Logger delegate : delegates) {
            delegate.error(marker, msg, t);
        }
    }
}
