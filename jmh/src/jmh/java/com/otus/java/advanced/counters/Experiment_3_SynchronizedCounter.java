package com.otus.java.advanced.counters;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.infra.Blackhole;

public class Experiment_3_SynchronizedCounter extends BaseExperimentCounter {

    @Override
    public Counter buildCounter() {
        return new SynchronizedCounter();
    }

    public static class SynchronizedCounter implements Counter {

        private long counter = 0;

        @Override
        public synchronized long writeOperation() {
            return ++counter;
        }

        @Override
        public synchronized long readOperation() {
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

//    Benchmark                                                  Mode  Cnt    Score    Error  Units
//    Experiment_3_SynchronizedCounter.multithreadCounterRead    avgt    5  409,997 ±  8,030  ns/op
//    Experiment_3_SynchronizedCounter.multithreadCounterWrite   avgt    5  443,279 ± 34,245  ns/op
//    Experiment_3_SynchronizedCounter.singleThreadCounterRead   avgt    5   16,417 ±  1,567  ns/op
//    Experiment_3_SynchronizedCounter.singleThreadCounterWrite  avgt    5   16,446 ±  1,681  ns/op
}
