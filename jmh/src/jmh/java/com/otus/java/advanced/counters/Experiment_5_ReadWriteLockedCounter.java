package com.otus.java.advanced.counters;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Experiment_5_ReadWriteLockedCounter extends BaseExperimentCounter {

    @Override
    public Counter buildCounter() {
        return new LockedCounter();
    }

    public static class LockedCounter implements Counter {

        private long counter = 0;
        private ReadWriteLock lock = new ReentrantReadWriteLock();

        @Override
        public long writeOperation() {
            lock.writeLock().lock();
            try {
                return ++counter;
            } finally {
                lock.writeLock().unlock();
            }
        }

        @Override
        public long readOperation() {
            lock.readLock().lock();
            try {
                return counter;
            } finally {
                lock.readLock().unlock();
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

//    Benchmark                                                     Mode  Cnt     Score    Error  Units
//    Experiment_5_ReadWriteLockedCounter.multithreadCounterRead    avgt    5  3479,343 ± 37,161  ns/op
//    Experiment_5_ReadWriteLockedCounter.multithreadCounterWrite   avgt    5   367,933 ±  9,313  ns/op
//    Experiment_5_ReadWriteLockedCounter.singleThreadCounterRead   avgt    5    16,807 ±  1,099  ns/op
//    Experiment_5_ReadWriteLockedCounter.singleThreadCounterWrite  avgt    5    14,619 ±  5,389  ns/op
}

