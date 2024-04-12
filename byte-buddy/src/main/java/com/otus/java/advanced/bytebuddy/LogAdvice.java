package com.otus.java.advanced.bytebuddy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import com.otus.java.advanced.bytebuddy.annotation.OurLog;
import com.otus.java.advanced.bytebuddy.annotation.OurLogger;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;

@Slf4j
public class LogAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Logger objectLogger = getObjectLogger(invocation);

        OurLog ourLogAnnotation = getOurLogAnnotation(invocation);

        String methodName = invocation.getMethod().getName();
        if (ourLogAnnotation.isShowError()) {
            log.info("before throws logger exception");
            throw new RuntimeException("throw error due to logger error show is: " + ourLogAnnotation.isShowError());
        } else {
            log.info("before log");
            objectLogger.info("proceed doSomething() method");
            log.info("after write log");

            log.info("before method: {} invocation", methodName);

            Object returnValue = invocation.proceed();
            log.info("after method: {} invocation", methodName);

            return returnValue;
        }
    }

    private Logger getObjectLogger(
            MethodInvocation invocation
    ) {

        OurLogger ourLoggerAnnotation = getOurLoggerAnnotation(invocation);
        String targetObjectLoggerName = ourLoggerAnnotation.value();

        Object targetObject = invocation.getThis();

        if (targetObject == null) {
            targetObject = invocation.getStaticPart();
        }

        Class<?> targetClass = targetObject.getClass();
        Field loggerField;
        try {
            loggerField = targetClass.getDeclaredField(targetObjectLoggerName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("""
            Target class: %s does not has Logger field: %s
            please set default name <%s> or set your
        """.formatted(targetObject, targetObjectLoggerName, "LOGGER"));
        }
        loggerField.setAccessible(true);

        try {
            return (Logger) loggerField.get(targetObject);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private OurLogger getOurLoggerAnnotation(
            MethodInvocation invocation
    ) {
        return Objects.requireNonNull(invocation.getThis()).getClass().getSuperclass().getAnnotation(OurLogger.class);
    }

    private OurLog getOurLogAnnotation(
            MethodInvocation invocation
    ) {
        Method method = invocation.getMethod();
        return method.getAnnotation(OurLog.class);
    }
}
