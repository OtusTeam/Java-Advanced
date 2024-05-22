package com.otus.java.advanced;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

public class DeadCodeElimination {

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void doNothing() {
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void objectCreation() {
        new Object();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public Object objectCreationWithReturn() {
        return new Object();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void objectCreationWithBlackhole(Blackhole blackhole) {
        blackhole.consume(new Object());
    }
}
