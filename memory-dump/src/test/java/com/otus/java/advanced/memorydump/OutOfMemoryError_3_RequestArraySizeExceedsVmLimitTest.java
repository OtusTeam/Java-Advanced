package com.otus.java.advanced.memorydump;

/**
 * cd ./memory-dump/src/test/java/com/otus/java/advanced/memorydump/
 * java -XX:+HeapDumpOnOutOfMemoryError ./OutOfMemoryError_3_RequestArraySizeExceedsVmLimitTest.java
 * java -Xms10m -Xmx10m -XX:+HeapDumpOnOutOfMemoryError ./OutOfMemoryError_3_RequestArraySizeExceedsVmLimitTest.java
 * java -Xms64g -Xmx64g -XX:+HeapDumpOnOutOfMemoryError ./OutOfMemoryError_3_RequestArraySizeExceedsVmLimitTest.java
 * <p>
 * java -Xms128g -Xmx128g -XX:+HeapDumpOnOutOfMemoryError ./OutOfMemoryError_3_RequestArraySizeExceedsVmLimitTest.java - ERROR
 * <p>
 * -verbose:gc -XX:+PrintGCDetails
 */
public class OutOfMemoryError_3_RequestArraySizeExceedsVmLimitTest {

    //@Test
    void maxArraySizeExceedsVmLimit_Error() {
        long[] array = new long[Integer.MAX_VALUE]; // 2,147,483,647 * 8 / 1024 / 1024 / 1024 ~ 16g
        System.out.println("Done");
    }

    //@Test
    void maxArraySizePreExceedsVmLimit_Error() {
        long[] array = new long[Integer.MAX_VALUE - 1];
        System.out.println("Done");
    }

    //@Test
    void maxArraySizePrePreExceedsVmLimit_Success() {
        long[] array = new long[Integer.MAX_VALUE - 2];
        System.out.println("Done");
    }

    //@Test
    void twoGigsArray_Success() {
        long[] array = new long[268435456]; // 2g * 1024mb * 1024kb * 1024b / 8
        System.out.println("Done");
    }

    public static void main(String[] args) {
        new OutOfMemoryError_3_RequestArraySizeExceedsVmLimitTest().maxArraySizeExceedsVmLimit_Error();
        new OutOfMemoryError_3_RequestArraySizeExceedsVmLimitTest().maxArraySizePreExceedsVmLimit_Error();
        new OutOfMemoryError_3_RequestArraySizeExceedsVmLimitTest().maxArraySizePrePreExceedsVmLimit_Success();
        new OutOfMemoryError_3_RequestArraySizeExceedsVmLimitTest().twoGigsArray_Success();
    }
}
