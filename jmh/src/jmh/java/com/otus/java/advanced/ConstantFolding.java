package com.otus.java.advanced;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(value = 1)
@Warmup(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
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
