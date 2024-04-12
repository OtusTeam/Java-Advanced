package com.otus.java.advanced.bytebuddy;

import java.lang.reflect.Method;
import com.otus.java.advanced.bytebuddy.annotation.OurLog;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

public class LogPointCut extends StaticMethodMatcherPointcut {

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return method.getDeclaredAnnotation(OurLog.class) != null;
    }
}
