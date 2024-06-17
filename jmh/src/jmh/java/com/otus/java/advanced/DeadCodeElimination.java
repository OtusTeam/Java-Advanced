package com.otus.java.advanced;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(1)
@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
public class DeadCodeElimination {

    @Benchmark
    public void doNothing() {
    }

    @Benchmark
    public void objectCreation() {
        new Object();
    }

    @Benchmark
    public Object objectCreationWithReturn() {
        return new Object();
    }

    @Benchmark
    public void objectCreationWithBlackhole(Blackhole blackhole) {
        blackhole.consume(new Object());
    }
}
