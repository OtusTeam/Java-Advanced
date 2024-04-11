package com.otus.java.advanced.instrumentation.agent;

import javassist.*;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.TraceClassVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class PerformanceTransformer implements ClassFileTransformer {

    private static final Logger log = LoggerFactory.getLogger(PerformanceTransformer.class);

    private final String instrumentedClassName;

    private final String instrumentedMethodName;

    private final ClassLoader classLoader;

    public PerformanceTransformer(String instrumentedClassName, String instrumentedMethodName, ClassLoader classLoader) {
        this.instrumentedClassName = instrumentedClassName;
        this.instrumentedMethodName = instrumentedMethodName;
        this.classLoader = classLoader;
    }

    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classFileBuffer) {

        var byteCode = classFileBuffer;

        var name = instrumentedClassName.replaceAll("\\.", "/");
        if (!className.equals(name)) {
            return byteCode;
        }

        if (loader.equals(classLoader)) {
            info("Transforming the class: " + className);
            try {
                info("Original class's byte code: \n" + getByteCodeAsString(byteCode));

                ClassPool cp = ClassPool.getDefault();
                cp.appendClassPath(new LoaderClassPath(loader));

                info("Getting class {}" + instrumentedClassName);
                CtClass cc = cp.get(instrumentedClassName);
                CtMethod m = cc.getDeclaredMethod(instrumentedMethodName);
                m.addLocalVariable("startTime", CtClass.longType);
                m.insertBefore("startTime = System.nanoTime();");

                m.addLocalVariable("endTime", CtClass.longType);
                m.addLocalVariable("opTime", CtClass.longType);
                m.insertAfter("""
                                      endTime = System.nanoTime();
                                      opTime = (endTime-startTime)/1000;
                                      log.info("[AGENT] Withdrawal operation completed in: " + opTime + " microseconds!");
                                      """
                                      .strip());

                byteCode = cc.toBytecode();
                cc.detach();

                /* LESSON why is the old byte code here ? */
                info("Instrumented class's byte code: \n{}" + getByteCodeAsString(instrumentedClassName));
                /*info("Instrumented class's byte code: \n{}" + getByteCodeAsString(byteCode));*/

                saveToFile(instrumentedClassName, byteCode);

            } catch (NotFoundException | CannotCompileException | IOException e) {
                info("Exception: " + e);
            }
        }
        return byteCode;
    }

    private static String getByteCodeAsString(String className) throws IOException {
        ClassReader reader = new ClassReader(className);
        return parseByteCode(reader);
    }

    private static String getByteCodeAsString(byte[] classFileBuffer) throws IOException {
        ClassReader reader = new ClassReader(classFileBuffer);
        return parseByteCode(reader);
    }

    private static String parseByteCode(ClassReader reader) {
        StringWriter sw = new StringWriter();
        TraceClassVisitor tcv = new TraceClassVisitor(new PrintWriter(sw));
        reader.accept(tcv, 0);
        return sw.toString();
    }

    private static void saveToFile(String className, byte[] newClassByteCode) throws IOException {
        String[] split = className.split("\\.");
        String last = split[split.length - 1];
        try (FileOutputStream out = new FileOutputStream(last + ".class")) {
            out.write(newClassByteCode);
            info("Transformed class saved to file: " + last);
        }
    }

    private static void info(String text) {
        log.info(text);
//        System.out.println(text);
    }
}
