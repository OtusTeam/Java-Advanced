package com.otus.java.advanced.counters;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.infra.Blackhole;

public class Experiment_1_NoThreadSafeCounter extends BaseExperimentCounter {

    @Override
    public Counter buildCounter() {
        return new NoThreadSafeCounter();
    }

    public static class NoThreadSafeCounter implements Counter {

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

//    Benchmark                                                  Mode  Cnt   Score   Error  Units
//    Experiment_1_NoThreadSafeCounter.multithreadCounterRead    avgt    5   1,321 ± 0,324  ns/op
//    Experiment_1_NoThreadSafeCounter.multithreadCounterWrite   avgt    5  12,124 ± 1,058  ns/op
//    Experiment_1_NoThreadSafeCounter.singleThreadCounterRead   avgt    5   0,474 ± 0,197  ns/op
//    Experiment_1_NoThreadSafeCounter.singleThreadCounterWrite  avgt    5   0,471 ± 0,045  ns/op
}