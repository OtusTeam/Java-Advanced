package com.otus.java.advanced.counters;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@Fork(value = 1)
@Warmup(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
public class Experiment_6_AtomicCounter {

    @State(Scope.Benchmark)
    public static class LockedCounterState implements Counter {

        private final AtomicLong counter = new AtomicLong(0);

        @Override
        public long writeOperation() {
            return counter.incrementAndGet();
        }

        @Override
        public long readOperation() {
            return counter.get();
        }
    }

    @Benchmark
    public void singleThreadCounterRead(LockedCounterState counter, Blackhole blackhole) {
        blackhole.consume(counter.readOperation());
    }

    @Benchmark
    public void singleThreadCounterWrite(LockedCounterState counter, Blackhole blackhole) {
        blackhole.consume(counter.writeOperation());
    }

    @Benchmark
    @Threads(Threads.MAX)
    public void multithreadCounterRead(LockedCounterState counter, Blackhole blackhole) {
        blackhole.consume(counter.readOperation());
    }

    @Benchmark
    @Threads(Threads.MAX)
    public void multithreadCounterWrite(LockedCounterState counter, Blackhole blackhole) {
        blackhole.consume(counter.writeOperation());
    }

//    Benchmark                                            Mode  Cnt    Score   Error  Units
//    Experiment_6_AtomicCounter.multithreadCounterRead    avgt    5    1,648 ± 0,207  ns/op
//    Experiment_6_AtomicCounter.multithreadCounterWrite   avgt    5  191,790 ± 5,947  ns/op
//    Experiment_6_AtomicCounter.singleThreadCounterRead   avgt    5    0,569 ± 0,023  ns/op
//    Experiment_6_AtomicCounter.singleThreadCounterWrite  avgt    5    4,511 ± 0,162  ns/op
}

