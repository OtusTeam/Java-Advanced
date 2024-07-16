package com.otus.java;

public class Fibonacci {

    //region LESSON Fibonacci classic
    public static long fibClassic(int n) {
        if (n < 2) {
            return n;
        }
        return fibClassic(n - 1) + fibClassic(n - 2);
    }
    //endregion

    //region LESSON Fibonacci tail recursion
    public static long tailRecFib(int n) {
        return tailRecFib(n, 0, 1);
    }

    private static int tailRecFib(int n, int a, int b) {
        if (n == 0) {
            return a;
        }
        if (n == 1) {
            return b;
        }
        return tailRecFib(n - 1, b, a + b);
    }
    //endregion
}
