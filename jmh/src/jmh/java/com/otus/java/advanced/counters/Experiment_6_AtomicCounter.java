package com.otus.java.advanced.counters;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.atomic.AtomicLong;

public class Experiment_6_AtomicCounter extends BaseExperimentCounter {

    @Override
    public Counter buildCounter() {
        return new LockedCounter();
    }

    public static class LockedCounter implements Counter {

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
    @Threads(Threads.MAX)
    public void multithreadCounterRead(Blackhole blackhole) {
        blackhole.consume(counter.readOperation());
    }

    @Benchmark
    @Threads(Threads.MAX)
    public void multithreadCounterWrite(Blackhole blackhole) {
        blackhole.consume(counter.writeOperation());
    }

    @Benchmark
    public void singleThreadCounterRead(Blackhole blackhole) {
        blackhole.consume(counter.readOperation());
    }

    @Benchmark
    public void singleThreadCounterWrite(Blackhole blackhole) {
        blackhole.consume(counter.writeOperation());
    }

//    Benchmark                                            Mode  Cnt    Score   Error  Units
//    Experiment_6_AtomicCounter.multithreadCounterRead    avgt    5    1,648 ± 0,207  ns/op
//    Experiment_6_AtomicCounter.multithreadCounterWrite   avgt    5  191,790 ± 5,947  ns/op
//    Experiment_6_AtomicCounter.singleThreadCounterRead   avgt    5    0,569 ± 0,023  ns/op
//    Experiment_6_AtomicCounter.singleThreadCounterWrite  avgt    5    4,511 ± 0,162  ns/op
}

