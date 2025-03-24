package org.example;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

public class TestCompressedOops {

    @Test
    public void test() {
        final Runtime runtime = Runtime.getRuntime();
        final DecimalFormat format = new DecimalFormat("#,###");

        final Object[] arr = new Object[Short.MAX_VALUE - 1];

        for (int x = 0; x < arr.length; x++) {
            arr[x] = "" + x;
        }

        System.out.println("Free memory: " + format.format(runtime.freeMemory()));
    }
}
