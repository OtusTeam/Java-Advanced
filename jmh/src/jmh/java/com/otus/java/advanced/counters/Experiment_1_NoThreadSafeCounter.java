package com.otus.java.advanced.counters;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@Fork(value = 1)
@Warmup(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
public class Experiment_1_NoThreadSafeCounter {

    @State(Scope.Benchmark)
    public static class NoThreadSafeCounterState implements Counter {

        private long counter = 0;

        @Override
        public long writeOperation() {
            return ++counter;
        }

        @Override
        public long readOperation() {
            return counter;
        }
    }

    @Benchmark
    public void singleThreadCounterRead(NoThreadSafeCounterState counter, Blackhole blackhole) {
        blackhole.consume(counter.readOperation());
    }

    @Benchmark
    public void singleThreadCounterWrite(NoThreadSafeCounterState counter, Blackhole blackhole) {
        blackhole.consume(counter.writeOperation());
    }

    @Benchmark
    @Threads(Threads.MAX)
    public void multithreadCounterRead(NoThreadSafeCounterState counter, Blackhole blackhole) {
        blackhole.consume(counter.readOperation());
    }

    @Benchmark
    @Threads(Threads.MAX)
    public void multithreadCounterWrite(NoThreadSafeCounterState counter, Blackhole blackhole) {
        blackhole.consume(counter.writeOperation());
    }

//    Benchmark                                                  Mode  Cnt   Score   Error  Units
//    Experiment_1_NoThreadSafeCounter.multithreadCounterRead    avgt    5   1,321 ± 0,324  ns/op
//    Experiment_1_NoThreadSafeCounter.multithreadCounterWrite   avgt    5  12,124 ± 1,058  ns/op
//    Experiment_1_NoThreadSafeCounter.singleThreadCounterRead   avgt    5   0,474 ± 0,197  ns/op
//    Experiment_1_NoThreadSafeCounter.singleThreadCounterWrite  avgt    5   0,471 ± 0,045  ns/op
}