package com.otus.java.advanced.bytebuddy.tracing.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CustomTracer {
    String loggerName() default "TRACE_LOGGER";

    InjectMode injectMode() default InjectMode.RELOAD;

    enum InjectMode {
        EXTENDS, RELOAD
    }
}
