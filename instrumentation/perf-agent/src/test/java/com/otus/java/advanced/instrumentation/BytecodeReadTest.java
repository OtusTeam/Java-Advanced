package com.otus.java.advanced.instrumentation;

import javassist.ClassPool;
import javassist.NotFoundException;
import javassist.bytecode.ClassFile;
import javassist.bytecode.MethodInfo;
import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BytecodeReadTest {

    @Test
    public void whenUsingASM_thenReadBytecode() throws IOException {
        ClassReader reader = new ClassReader("java.lang.Object");
        StringWriter sw = new StringWriter();
        TraceClassVisitor tcv = new TraceClassVisitor(new PrintWriter(sw));
        reader.accept(tcv, 0);

        String string = sw.toString();
        System.out.println(string);
        assertTrue(string.contains("public class java/lang/Object"));
    }

    @Test
    public void whenUsingBCEL_thenReadBytecode() throws ClassNotFoundException {
        JavaClass objectClazz = Repository.lookupClass("java.lang.Object");

        assertEquals("java.lang.Object", objectClazz.getClassName());
        assertEquals(13, objectClazz.getMethods().length);
        String string = objectClazz.toString();
        System.out.println(string);
        assertTrue(string.contains("public class java.lang.Object"));
    }

    @Test
    public void whenUsingJavassist_thenReadBytecode() throws NotFoundException {
        ClassPool cp = ClassPool.getDefault();
        ClassFile cf = cp.get("java.lang.Object").getClassFile();

        List<MethodInfo> methods = cf.getMethods();
        assertEquals(13, methods.size());
        assertEquals("java.lang.Object", cf.getName());
    }
}