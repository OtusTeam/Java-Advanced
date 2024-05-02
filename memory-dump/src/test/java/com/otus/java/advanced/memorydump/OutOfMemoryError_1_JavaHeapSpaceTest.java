package com.otus.java.advanced.memorydump;

import com.otus.java.advanced.HeapUtils;

/**
 * java -Xms10m -Xmx2g -XX:+HeapDumpOnOutOfMemoryError
 */
public class OutOfMemoryError_1_JavaHeapSpaceTest {

    //@Test
    void enough_space_success() {
        HeapUtils.printMemoryUsage("Before test: ");
        Integer[] array = new Integer[1000 * 1000 * 100];

        System.out.println();
        HeapUtils.printMemoryUsage("After test: ");
        System.out.println("Successful");
    }

    //@Test
    void not_enough_space_error() {
        HeapUtils.printMemoryUsage("Before test: ");
        Integer[] array = new Integer[1000 * 1000 * 1000];

        System.out.println();
        HeapUtils.printMemoryUsage("After test: ");
        System.out.println("Successful");
    }

    public static void main(String[] args) {
        new OutOfMemoryError_1_JavaHeapSpaceTest().enough_space_success();
//        new OutOfMemoryError_1_JavaHeapSpaceTest().not_enough_space_error();
    }
}
