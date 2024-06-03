package com.otus.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FibonacciTest {

    @Test
    public void testFib1() {
        assertEquals(144, Fibonacci.fibClassic(12));
        assertEquals(144, Fibonacci.tailRecFib(12));
    }

    @Test
    public void testFib2() {
        assertEquals(832040, Fibonacci.fibClassic(30));
        assertEquals(832040, Fibonacci.tailRecFib(30));
    }
}