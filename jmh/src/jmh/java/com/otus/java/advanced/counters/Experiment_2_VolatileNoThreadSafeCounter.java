package com.otus.java.advanced.counters;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.infra.Blackhole;

public class Experiment_2_VolatileNoThreadSafeCounter extends BaseExperimentCounter {

    @Override
    public Counter buildCounter() {
        return new VolatileNoThreadSafeCounter();
    }

    public static class VolatileNoThreadSafeCounter implements Counter {

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

//    Benchmark                                                          Mode  Cnt    Score   Error  Units
//    Experiment_2_VolatileNoThreadSafeCounter.multithreadCounterRead    avgt    5    1,245 ± 0,119  ns/op
//    Experiment_2_VolatileNoThreadSafeCounter.multithreadCounterWrite   avgt    5  259,487 ± 8,843  ns/op
//    Experiment_2_VolatileNoThreadSafeCounter.singleThreadCounterRead   avgt    5    0,445 ± 0,026  ns/op
//    Experiment_2_VolatileNoThreadSafeCounter.singleThreadCounterWrite  avgt    5    4,522 ± 0,385  ns/op
}
