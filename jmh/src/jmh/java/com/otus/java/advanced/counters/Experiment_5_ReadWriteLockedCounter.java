package com.otus.java.advanced.counters;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@Fork(value = 1)
@Warmup(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
public class Experiment_5_ReadWriteLockedCounter {

    @State(Scope.Benchmark)
    public static class LockedCounterState implements Counter {

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

//    Benchmark                                                     Mode  Cnt     Score    Error  Units
//    Experiment_5_ReadWriteLockedCounter.multithreadCounterRead    avgt    5  3479,343 ± 37,161  ns/op
//    Experiment_5_ReadWriteLockedCounter.multithreadCounterWrite   avgt    5   367,933 ±  9,313  ns/op
//    Experiment_5_ReadWriteLockedCounter.singleThreadCounterRead   avgt    5    16,807 ±  1,099  ns/op
//    Experiment_5_ReadWriteLockedCounter.singleThreadCounterWrite  avgt    5    14,619 ±  5,389  ns/op
}

