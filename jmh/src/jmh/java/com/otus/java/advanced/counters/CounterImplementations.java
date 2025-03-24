package com.otus.java.advanced.counters;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CounterImplementations {


    public static class NoThreadSafeCounter implements Counter {

        private long counter = 0;

        @Override
        public long write() {
            return ++counter;
        }

        @Override
        public long read() {
            return counter;
        }
    }


    public static class VolatileNoThreadSafeCounter implements Counter {

        private volatile long counter = 0;

        @Override
        public long write() {
            return ++counter;
        }

        @Override
        public long read() {
            return counter;
        }
    }


    public static class SynchronizedCounter implements Counter {

        private long counter = 0;

        @Override
        public synchronized long write() {
            return ++counter;
        }

        @Override
        public synchronized long read() {
            return counter;
        }
    }


    public static class LockedCounter implements Counter {

        private long counter = 0;
        private Lock lock = new ReentrantLock();

        @Override
        public long write() {
            lock.lock();
            try {
                return ++counter;
            } finally {
                lock.unlock();
            }
        }

        @Override
        public long read() {
            lock.lock();
            try {
                return counter;
            } finally {
                lock.unlock();
            }
        }
    }


    public static class ReadWriteLockedCounter implements Counter {

        private long counter = 0;
        private ReadWriteLock lock = new ReentrantReadWriteLock();

        @Override
        public long write() {
            lock.writeLock().lock();
            try {
                return ++counter;
            } finally {
                lock.writeLock().unlock();
            }
        }

        @Override
        public long read() {
            lock.readLock().lock();
            try {
                return counter;
            } finally {
                lock.readLock().unlock();
            }
        }
    }


    public static class AtomicCounter implements Counter {

        private final AtomicLong counter = new AtomicLong(0);

        @Override
        public long write() {
            return counter.incrementAndGet();
        }

        @Override
        public long read() {
            return counter.get();
        }
    }
}
