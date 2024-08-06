package com.otus.java.advanced.bytebuddy.tracing;

import com.otus.java.advanced.bytebuddy.tracing.annotation.CustomTracer;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodCall;
import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

@Configuration
@Order
public class LoggerBeanPostProcessor implements BeanPostProcessor {

    private final ByteBuddy byteBuddy = new ByteBuddy();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = ClassUtils.getUserClass(bean.getClass());
        CustomTracer tracerAnnotation = findTracerAnnotation(targetClass);
        if (tracerAnnotation == null) {
            return bean;
        }

        Class<?> targetClassWithLogger = createTargetClassLogger(targetClass, tracerAnnotation);
        Object targetObject = createTargetObject(targetClassWithLogger);
        Object proxyTargetObject = createProxyTargetObject(targetObject);

        return proxyTargetObject;
    }

    private <T> Class<? extends T> createTargetClassLogger(Class<T> targetClass, CustomTracer tracerAnnotation) {
        try {
            String loggerName = tracerAnnotation.loggerName();
            switch (tracerAnnotation.injectMode()) {
                case RELOAD -> {
                    throw new UnsupportedOperationException(); // LESSON todo implement by yourself
                }

                case EXTENDS -> {
                    DynamicType.Builder<T> subclassed = byteBuddy.subclass(targetClass);

                    DynamicType.Builder<T> named = subclassed.name(targetClass.getCanonicalName() + "WithLogger");

                    DynamicType.Builder.FieldDefinition.Optional.Valuable<T> withField =
                            named.defineField(
                                    loggerName,
                                    Logger.class,
                                    Modifier.PRIVATE | Modifier.FINAL
                            );

                    DynamicType.Builder.MethodDefinition.ParameterDefinition.Initial<T> withConstructor =
                            withField.defineConstructor(Visibility.PUBLIC);

                    DynamicType.Builder.MethodDefinition.ExceptionDefinition<T> tExceptionDefinition =
                            withConstructor.withParameters(Logger.class);

                    DynamicType.Builder.MethodDefinition.ReceiverTypeDefinition<T> intercept =
                            tExceptionDefinition.intercept(
                                    MethodCall
                                            .invoke(targetClass.getConstructor())
                                            .andThen(FieldAccessor.ofField(loggerName).setsArgumentAt(0))
                            );

                    DynamicType.Unloaded<T> maked = intercept.make();

                    DynamicType.Loaded<T> loaded = maked.load(getClass().getClassLoader());

                    Class<? extends T> loadedClass = loaded.getLoaded();

                    return loadedClass;
                }

                default -> throw new RuntimeException("Unreachable statement");
            }

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> CustomTracer findTracerAnnotation(Class<T> targetClass) {
        return AnnotationUtils.getAnnotation(targetClass, CustomTracer.class);
    }

    private <T> T createTargetObject(Class<? extends T> targetClassWithLogger) {
        try {
            return targetClassWithLogger
                    .getConstructor(org.slf4j.Logger.class)
                    .newInstance(org.slf4j.LoggerFactory.getLogger(targetClassWithLogger));
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Object createProxyTargetObject(Object targetObject) {
        Pointcut tracePointCut = new TracePointCut();
        Advice traceAdvice = new TraceAdvice();

        Advisor logAdvisor = new DefaultPointcutAdvisor(tracePointCut, traceAdvice);

        ProxyFactory pf = new ProxyFactory();

        pf.setTarget(targetObject);
        pf.addAdvisor(logAdvisor);

        return pf.getProxy();
    }
}
