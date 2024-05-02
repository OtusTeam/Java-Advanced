package com.otus.java.advanced.memorydump;

import java.util.ArrayList;
import java.util.List;

/**
 * java -XX:PermSize=2M -XX:MaxPermSize=5M OutOfMemoryErrorPermgenSpaceTest
 * /c/Program\ Files/Java/jre7/bin/java.exe -XX:PermSize=2M -XX:MaxPermSize=5M OutOfMemoryError_4_PermgenSpaceTest
 * <p>
 * /c/Program\ Files/Java/jdk1.7.0_80/bin/javac.exe OutOfMemoryError_4_PermgenSpaceTest.java
 * /c/Program\ Files/Java/jdk1.7.0_80/bin/java.exe -XX:PermSize=2M -XX:MaxPermSize=5M OutOfMemoryError_4_PermgenSpaceTest
 * <p>
 * -verbose:gc -XX:+PrintGCDetails
 */
public class OutOfMemoryError_4_PermgenSpaceTest {

    //@Test
    void test() {
        List<String> list = new ArrayList<>();
        int count = 0;
        while (true) {
            list.add(String.valueOf(count++).intern());
            System.out.println(count);
        }
    }

    public static void main(String[] args) {
        new OutOfMemoryError_4_PermgenSpaceTest().test();
    }
}
