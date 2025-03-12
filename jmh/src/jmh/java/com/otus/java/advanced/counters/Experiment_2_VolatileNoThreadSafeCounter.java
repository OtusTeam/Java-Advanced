package com.otus.java.advanced.counters;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@Fork(value = 1)
@Warmup(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
public class Experiment_2_VolatileNoThreadSafeCounter {

    @State(Scope.Benchmark)
    public static class VolatileNoThreadSafeCounterState implements Counter {

        private volatile long counter = 0;

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
    public void singleThreadCounterRead(VolatileNoThreadSafeCounterState counter, Blackhole blackhole) {
        blackhole.consume(counter.readOperation());
    }

    @Benchmark
    public void singleThreadCounterWrite(VolatileNoThreadSafeCounterState counter, Blackhole blackhole) {
        blackhole.consume(counter.writeOperation());
    }

    @Benchmark
    @Threads(Threads.MAX)
    public void multithreadCounterRead(VolatileNoThreadSafeCounterState counter, Blackhole blackhole) {
        blackhole.consume(counter.readOperation());
    }

    @Benchmark
    @Threads(Threads.MAX)
    public void multithreadCounterWrite(VolatileNoThreadSafeCounterState counter, Blackhole blackhole) {
        blackhole.consume(counter.writeOperation());
    }

//    Benchmark                                                          Mode  Cnt    Score   Error  Units
//    Experiment_2_VolatileNoThreadSafeCounter.multithreadCounterRead    avgt    5    1,245 ± 0,119  ns/op
//    Experiment_2_VolatileNoThreadSafeCounter.multithreadCounterWrite   avgt    5  259,487 ± 8,843  ns/op
//    Experiment_2_VolatileNoThreadSafeCounter.singleThreadCounterRead   avgt    5    0,445 ± 0,026  ns/op
//    Experiment_2_VolatileNoThreadSafeCounter.singleThreadCounterWrite  avgt    5    4,522 ± 0,385  ns/op
}
