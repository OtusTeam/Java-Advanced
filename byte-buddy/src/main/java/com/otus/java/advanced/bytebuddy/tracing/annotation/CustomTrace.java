package com.otus.java.advanced.bytebuddy.tracing.annotation;

import org.slf4j.event.Level;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface CustomTrace {
    TraceMode mode() default TraceMode.ALL;

    Level logLevel() default Level.TRACE;

    Level exceptionLogLevel() default Level.TRACE;

    enum TraceMode {
        IN, OUT, INOUT, EXCEPTION, ALL
    }
}
