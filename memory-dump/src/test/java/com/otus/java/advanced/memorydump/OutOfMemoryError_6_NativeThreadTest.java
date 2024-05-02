package com.otus.java.advanced.memorydump;

import java.util.concurrent.TimeUnit;

/**
 * Number of threads = (MaxProcessMemory - JVMMemory - ReservedOsMemory) / (ThreadStackSize)
 * MaxProcessMemory  - Refers to the maximum memory of a process
 * JVMMemory         - JVM memory
 * ReservedOsMemory  - Reserved operating system memory
 * ThreadStackSize   - The size of the thread stack
 * <p>
 * java -Xss2m -Xms10m -Xmx10m -XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10M -XX:+HeapDumpOnOutOfMemoryError -verbose:gc
 * java -Xss128m -Xms10m -Xmx10m -XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10M -XX:+HeapDumpOnOutOfMemoryError -verbose:gc
 * <p>
 * -verbose:gc -XX:+PrintGCDetails
 */
public class OutOfMemoryError_6_NativeThreadTest {

    //@Test
    void test() {
        long count = 0;
        while (true) {
            long threadId = count;
            Thread thread = new Thread(() -> {
                Thread.currentThread().setName("MyCustomThread_" + threadId);
                try {
                    TimeUnit.DAYS.sleep(999999);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
            System.out.println(count++);
        }
    }

    public static void main(String[] args) {
        new OutOfMemoryError_6_NativeThreadTest().test();
    }
}
