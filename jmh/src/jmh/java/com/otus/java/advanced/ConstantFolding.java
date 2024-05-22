package com.otus.java.advanced;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

public class ConstantFolding {

    /**
     * *    @Benchmark
     * *    public double foldedLog() {
     * *        return 2.0794415416798357;
     * *    }
     *
     * @return
     */
    @Benchmark
    public double foldedLog() {
        int x = 8;

        return Math.log(x);
    }


    @State(Scope.Benchmark)
    public static class Log {
        public int x = 8;
    }

    @Benchmark
    public double log(Log input) {
        return Math.log(input.x);
    }
}
