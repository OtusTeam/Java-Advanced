package com.otus.java.advanced.counters;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
@Fork(value = 1)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
//@Warmup(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
//@Measurement(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
public abstract class BaseExperimentCounter {

    protected Counter counter;

    @Setup(Level.Trial)
    public void setUp() {
        counter = buildCounter();
    }

    public abstract Counter buildCounter();
}
