package com.otus.java.advanced.bytebuddy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import com.otus.java.advanced.bytebuddy.annotation.OurLogger;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodCall;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.util.ClassUtils;

@Configuration
@Order
public class LoggerBeanPostProcessor implements BeanPostProcessor {

    private final ByteBuddy byteBuddy = new ByteBuddy();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Class<?> targetClass = ClassUtils.getUserClass(bean.getClass());
        if (hasLoggerAnnotation(targetClass)) {
            Object targetObject = createTargetObject(createTargetClassLogger(targetClass));
            return createProxyTargetObject(targetObject);
        }

        return bean;
    }

    <T> Class<? extends T> createTargetClassLogger(Class<T> targetClass) {
        try {
            return byteBuddy
                    .subclass(targetClass)
                    .name(targetClass.getCanonicalName() + "WithLogger")
                    .defineField(
                            "LOGGER",
                            org.slf4j.Logger.class,
                            Modifier.PRIVATE | Modifier.FINAL
                    )
                    .defineConstructor(Visibility.PUBLIC)
                    .withParameters(org.slf4j.Logger.class)
                    .intercept(
                            MethodCall
                                    .invoke(targetClass.getConstructor())
                                    .andThen(FieldAccessor.ofField(getTargetClassLoggerAnnotation(targetClass).value()).setsArgumentAt(0))
                    )
                    .make()
                    .load(getClass().getClassLoader())
                    .getLoaded();

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    <T> OurLogger getTargetClassLoggerAnnotation(Class<T> targetClass) {
        return AnnotationUtils.getAnnotation(
                targetClass,
                OurLogger.class
        );
    }

    <T> T createTargetObject(Class<? extends T> targetClassWithLogger) {
        try {
            return targetClassWithLogger
                    .getConstructor(org.slf4j.Logger.class)
                    .newInstance(org.slf4j.LoggerFactory.getLogger(targetClassWithLogger));
        } catch (
                NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e
        ) {
            throw new RuntimeException(e);
        }
    }

    Object createProxyTargetObject(Object targetObject) {
        Advisor logAdvisor = createLogAdvisor();

        return createAopProxyWithAdvisor(targetObject, logAdvisor);
    }

    boolean hasLoggerAnnotation(Class<?> targetClass) {

        return getTargetClassLoggerAnnotation(targetClass) != null;
    }

    Advisor createLogAdvisor() {
        Pointcut logPointCut = new LogPointCut();
        Advice logAdvice = new LogAdvice();
        return new DefaultPointcutAdvisor(
                logPointCut, logAdvice
        );
    }

    Object createAopProxyWithAdvisor(Object targetObject, Advisor advisor) {
        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(targetObject);
        pf.addAdvisor(advisor);

        return pf.getProxy();
    }
}
