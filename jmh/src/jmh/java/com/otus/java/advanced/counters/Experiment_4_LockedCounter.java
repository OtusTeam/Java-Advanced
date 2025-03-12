package com.otus.java.advanced.counters;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@Fork(value = 1)
@Warmup(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
public class Experiment_4_LockedCounter {

    @State(Scope.Benchmark)
    public static class LockedCounterState implements Counter {

        private long counter = 0;
        private Lock lock = new ReentrantLock();

        @Override
        public long writeOperation() {
            lock.lock();
            try {
                return ++counter;
            } finally {
                lock.unlock();
            }
        }

        @Override
        public long readOperation() {
            lock.lock();
            try {
                return counter;
            } finally {
                lock.unlock();
            }
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

//    Benchmark                                            Mode  Cnt    Score    Error  Units
//    Experiment_4_LockedCounter.multithreadCounterRead    avgt    5  350,614 ± 11,630  ns/op
//    Experiment_4_LockedCounter.multithreadCounterWrite   avgt    5  347,502 ± 12,399  ns/op
//    Experiment_4_LockedCounter.singleThreadCounterRead   avgt    5   12,994 ±  0,568  ns/op
//    Experiment_4_LockedCounter.singleThreadCounterWrite  avgt    5   12,930 ±  0,310  ns/op
}

