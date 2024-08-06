package com.otus.java.advanced.bytebuddy.tracing;

import com.otus.java.advanced.bytebuddy.tracing.annotation.CustomTrace;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

public class TracePointCut extends StaticMethodMatcherPointcut {

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return method.getDeclaredAnnotation(CustomTrace.class) != null;
    }
}
