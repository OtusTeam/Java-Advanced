package com.otus.java.advanced.counters;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Experiment_4_LockedCounter extends BaseExperimentCounter {

    @Override
    public Counter buildCounter() {
        return new LockedCounter();
    }

    public static class LockedCounter implements Counter {

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

//    Benchmark                                            Mode  Cnt    Score    Error  Units
//    Experiment_4_LockedCounter.multithreadCounterRead    avgt    5  350,614 ± 11,630  ns/op
//    Experiment_4_LockedCounter.multithreadCounterWrite   avgt    5  347,502 ± 12,399  ns/op
//    Experiment_4_LockedCounter.singleThreadCounterRead   avgt    5   12,994 ±  0,568  ns/op
//    Experiment_4_LockedCounter.singleThreadCounterWrite  avgt    5   12,930 ±  0,310  ns/op
}

