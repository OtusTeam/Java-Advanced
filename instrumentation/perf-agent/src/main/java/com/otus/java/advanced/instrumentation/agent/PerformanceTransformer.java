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
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class PerformanceTransformer implements ClassFileTransformer {

    private static final Logger log = LoggerFactory.getLogger(PerformanceTransformer.class);

    private final String targetClassName;

    private final String targetMethodName;

    private final ClassLoader targetClassLoader;

    public PerformanceTransformer(String targetClassName, String targetMethodName, ClassLoader targetClassLoader) {
        this.targetClassName = targetClassName;
        this.targetMethodName = targetMethodName;
        this.targetClassLoader = targetClassLoader;
    }

    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classFileBuffer) throws IllegalClassFormatException {

        byte[] byteCode = classFileBuffer;

        String finalTargetClassName = targetClassName.replaceAll("\\.", "/");
        if (!className.equals(finalTargetClassName)) {
            return byteCode;
        }

        if (loader.equals(targetClassLoader)) {
            log.info("Transforming the class: {}", className);
            try {
                log.info("Original class's byte code: \n{}", getByteCodeAsString(targetClassName));

                ClassPool cp = ClassPool.getDefault();
                log.debug("Getting class {}", targetClassName);
                CtClass cc = cp.get(targetClassName);
                CtMethod m = cc.getDeclaredMethod(targetMethodName);
                m.addLocalVariable("startTime", CtClass.longType);
                m.insertBefore("startTime = System.currentTimeMillis();");

                m.addLocalVariable("endTime", CtClass.longType);
                m.addLocalVariable("opTime", CtClass.longType);
                m.insertAfter("""
                                      endTime = System.currentTimeMillis();
                                      opTime = (endTime-startTime)/1000;
                                      log.info("[AGENT] Withdrawal operation completed in:" + opTime + " seconds!");
                                      """
                                      .strip());

                byteCode = cc.toBytecode();
                cc.detach();

                /* LESSON why is the old byte code here ? */
                log.info("Instrumented class's byte code: \n{}", getByteCodeAsString(targetClassName)); //getByteCodeAsString(byteCode)

                saveToFile(targetClassName, byteCode);

            } catch (NotFoundException | CannotCompileException | IOException | ClassNotFoundException e) {
                log.error("Exception", e);
            }
        }
        return byteCode;
    }

    private static String getByteCodeAsString(String className)
            throws IOException, ClassNotFoundException {

        ClassReader reader = new ClassReader(className);
        return parseByteCode(reader);
    }

    private static String getByteCodeAsString(byte[] classFileBuffer) {

        ClassReader reader = new ClassReader(classFileBuffer);
        return parseByteCode(reader);
    }

    //todo may be replace this by javaassist's approach?
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
            log.info("Transformed class saved to file: {}", last);
        }
    }
}
