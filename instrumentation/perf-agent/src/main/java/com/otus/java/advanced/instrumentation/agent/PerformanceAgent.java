package com.otus.java.advanced.instrumentation.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.util.Arrays;

public class PerformanceAgent {

    private static final Logger log = LoggerFactory.getLogger(PerformanceAgent.class);

    /**
     * Entrypoint into this agent in case of STATIC load
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        log.info("premain method started");

        instrument(agentArgs, inst);
    }

    /**
     * Entrypoint into this agent in case of DYNAMIC load
     */
    public static void agentmain(String agentArgs, Instrumentation inst) {
        log.info("agentmain method started");

        instrument(agentArgs, inst);
    }

    private static void instrument(String classAndMethodName, Instrumentation instrumentation) {
        if (classAndMethodName == null || classAndMethodName.isBlank()) {
            throw new RuntimeException("ClassAndMethodName is blank");
        }
        String[] entry = classAndMethodName.split("#");

        Class<?> clazz = getClass(entry[0], instrumentation);
        Method method = getMethod(clazz, entry[1]);

        instrument(clazz, method, instrumentation);
    }

    private static Method getMethod(Class<?> clazz, String methodName) {
        return Arrays.stream(clazz.getMethods())
                .filter(m -> m.getName().equals(methodName))
                .findFirst()
                .orElseThrow();
    }

    private static Class<?> getClass(String classAndMethodName, Instrumentation instrumentation) {
        Class<?> clazz = null;

        try {
            clazz = Class.forName(classAndMethodName);

        } catch (Exception ex) {
            log.error("Class [{}] not found with Class.forName", classAndMethodName, ex);

            for (Class<?> c : instrumentation.getAllLoadedClasses()) {
                if (c.getName().equals(classAndMethodName)) {
                    clazz = c;
                    break;
                }
            }

            if (clazz == null) {
                throw new RuntimeException("Failed to find class [" + classAndMethodName + "]");
            }
        }
        return clazz;
    }

    private static void instrument(Class<?> clazz, Method method, Instrumentation instrumentation) {
        PerformanceTransformer dt =
                new PerformanceTransformer(clazz.getName(), method.getName(), clazz.getClassLoader());
        instrumentation.addTransformer(dt, true);

        try {
            instrumentation.retransformClasses(clazz);

        } catch (Exception ex) {
            throw new RuntimeException("Transform failed for class: [" + clazz.getName() + "]", ex);
        }
    }
}
