package com.otus.java.advanced.reentrantlocks;

import org.junit.jupiter.api.Test;

public class ConditionDemoTest {

    @Test
    void test() {
        ConditionDemo obj = new ConditionDemo(5);
        Thread even = new Thread(obj, "even");
        Thread odd = new Thread(obj, "odd");
        even.start();
        odd.start();
    }
}
