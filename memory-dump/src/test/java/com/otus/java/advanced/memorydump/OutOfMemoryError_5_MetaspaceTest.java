package com.otus.java.advanced.memorydump;

import javassist.CannotCompileException;
import javassist.ClassPool;

import java.util.ArrayList;
import java.util.List;

/**
 * java -XX:MetaspaceSize=8M -XX:MaxMetaspaceSize=16M -XX:+HeapDumpOnOutOfMemoryError --add-opens java.base/java.lang=ALL-UNNAMED
 * <p>
 * java -Xms10m -Xmx10m -XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10M -verbose:gc -XX:+HeapDumpOnOutOfMemoryError --add-opens java.base/java.lang=ALL-UNNAMED
 * <p>
 * -verbose:gc -XX:+PrintGCDetails
 */
public class OutOfMemoryError_5_MetaspaceTest {

    private final ClassPool classPool = ClassPool.getDefault();

    //@Test
    void loadClass_Error() throws CannotCompileException {
        for (int i = 0; i < 70_000; i++) {
            Class<?> clazz = classPool.makeClass("SurrogateClass " + i).toClass();
            if (i % 1000 == 0) {
                System.out.println(clazz.getName());
            }
        }
    }

    //@Test
    void intern_Error() throws CannotCompileException {
        List<String> list = new ArrayList<>();
        int count = 0;
        while (true) {
            list.add(String.valueOf(count++).intern());
            if (count % 10000 == 0) {
                System.out.println(count);
            }
        }
    }

    public static void main(String[] args) throws CannotCompileException {
        new OutOfMemoryError_5_MetaspaceTest().loadClass_Error();
//        new OutOfMemoryError_5_MetaspaceTest().intern_Error();
    }
}
