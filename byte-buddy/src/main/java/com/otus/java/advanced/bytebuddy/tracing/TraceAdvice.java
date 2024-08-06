package com.otus.java.advanced.bytebuddy.tracing;

import com.otus.java.advanced.bytebuddy.tracing.annotation.CustomTrace;
import com.otus.java.advanced.bytebuddy.tracing.annotation.CustomTracer;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class TraceAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        CustomTrace traceAnnotation = getTraceAnnotation(invocation);
        CustomTrace.TraceMode mode = traceAnnotation.mode();

        Logger traceLogger = getObjectLogger(invocation);
        if (mode == CustomTrace.TraceMode.IN
                || mode == CustomTrace.TraceMode.INOUT
                || mode == CustomTrace.TraceMode.ALL) {

            Object[] arguments = invocation.getArguments();
            String argsStr = Arrays.stream(arguments).map(String::valueOf).collect(Collectors.joining(", "));

            log(traceAnnotation.logLevel(), traceLogger, "IN", argsStr);
        }

        Object result;
        try {
            result = invocation.proceed();

            if (mode == CustomTrace.TraceMode.OUT
                    || mode == CustomTrace.TraceMode.INOUT
                    || mode == CustomTrace.TraceMode.ALL) {

                log(traceAnnotation.logLevel(), traceLogger, "OUT", String.valueOf(result));
            }

        } catch (Throwable e) {

            if (mode == CustomTrace.TraceMode.EXCEPTION
                    || mode == CustomTrace.TraceMode.ALL) {

                log(traceAnnotation.exceptionLogLevel(), traceLogger, "EXCEPTION", String.valueOf(e.toString()));
            }

            throw e;
        }

        return result;
    }

    private Logger getObjectLogger(MethodInvocation invocation) {
        CustomTracer tracerAnnotation = getTracerAnnotation(invocation);
        String loggerName = tracerAnnotation.loggerName();

        Object targetObject = invocation.getThis();

        if (targetObject == null) {
            targetObject = invocation.getStaticPart();
        }

        Field loggerField = getField(targetObject, loggerName);

        try {
            return (Logger) loggerField.get(targetObject);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private CustomTracer getTracerAnnotation(MethodInvocation invocation) {
        return Objects.requireNonNull(invocation.getThis())
                .getClass()
                .getSuperclass()
                .getAnnotation(CustomTracer.class);
    }

    private CustomTrace getTraceAnnotation(MethodInvocation invocation) {
        return Objects.requireNonNull(invocation)
                .getMethod()
                .getAnnotation(CustomTrace.class);
    }

    private static Field getField(Object targetObject, String targetObjectLoggerName) {
        Field loggerField;
        try {
            Class<?> targetClass = targetObject.getClass();
            loggerField = targetClass.getDeclaredField(targetObjectLoggerName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("""
                                                   Target class: %s does not has Logger field: %s
                                                   please set default name <%s> or set your
                                               """.formatted(targetObject, targetObjectLoggerName, "LOGGER"));
        }
        loggerField.setAccessible(true);
        return loggerField;
    }

    private static void log(Level level, Logger logger, String prefix, String str) {
        switch (level) {
            case ERROR -> logger.error(prefix + ": {}", str);
            case WARN -> logger.warn(prefix + ": {}", str);
            case INFO -> logger.info(prefix + ": {}", str);
            case DEBUG -> logger.debug(prefix + ": {}", str);
            case TRACE -> logger.trace(prefix + ": {}", str);
        }
    }
}
