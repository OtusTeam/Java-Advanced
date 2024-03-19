package com.otus.javaadvanced.cache;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface Cachable {

    int UNSET = -1;

    int maximumSize() default UNSET;

    long expireAfterWriteDuration() default UNSET;

    TimeUnit expireAfterWriteTimeUnit() default TimeUnit.HOURS;

    long expireAfterAccessDuration() default UNSET;

    TimeUnit expireAfterAccessTimeUnit() default TimeUnit.HOURS;

    int concurrencyLevel() default UNSET;
}

