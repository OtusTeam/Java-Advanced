package com.otus.java.advanced.counters;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;


@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@Fork(value = 1)
@Warmup(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
public class Experiment_3_SynchronizedCounter {

    @State(Scope.Benchmark)
    public static class SynchronizedCounterState implements Counter {

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
    public void singleThreadCounterRead(SynchronizedCounterState counter, Blackhole blackhole) {
        blackhole.consume(counter.readOperation());
    }

    @Benchmark
    public void singleThreadCounterWrite(SynchronizedCounterState counter, Blackhole blackhole) {
        blackhole.consume(counter.writeOperation());
    }

    @Benchmark
    @Threads(Threads.MAX)
    public void multithreadCounterRead(SynchronizedCounterState counter, Blackhole blackhole) {
        blackhole.consume(counter.readOperation());
    }

    @Benchmark
    @Threads(Threads.MAX)
    public void multithreadCounterWrite(SynchronizedCounterState counter, Blackhole blackhole) {
        blackhole.consume(counter.writeOperation());
    }

//    Benchmark                                                  Mode  Cnt    Score    Error  Units
//    Experiment_3_SynchronizedCounter.multithreadCounterRead    avgt    5  409,997 ±  8,030  ns/op
//    Experiment_3_SynchronizedCounter.multithreadCounterWrite   avgt    5  443,279 ± 34,245  ns/op
//    Experiment_3_SynchronizedCounter.singleThreadCounterRead   avgt    5   16,417 ±  1,567  ns/op
//    Experiment_3_SynchronizedCounter.singleThreadCounterWrite  avgt    5   16,446 ±  1,681  ns/op
}
