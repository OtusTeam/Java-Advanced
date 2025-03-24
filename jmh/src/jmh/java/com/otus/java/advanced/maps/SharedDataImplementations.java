package com.otus.java.advanced.maps;

import com.otus.java.advanced.SharedData;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SharedDataImplementations {

    private static final int UPPER_KEY = 100;
    private final Random random = new Random(1234);

    public static long getRandomLong() {
        return new Random().nextLong(UPPER_KEY);
    }


    public static class HashMapShared implements SharedData {

        private final Map<Long, Long> map = new HashMap<>();

        @Override
        public long write() {
            long k = getRandomLong();
            map.put(k, k);
            return k;
        }

        @Override
        public long read() {
            long k = getRandomLong();
            Long v = map.get(k);

            if (v == null) return k;
            else return v;
        }
    }


    public static class SynchronizedHashMapShared implements SharedData {

        private final Map<Long, Long> map = Collections.synchronizedMap(new HashMap<>());

        @Override
        public long write() {
            long k = getRandomLong();
            map.put(k, k);
            return k;
        }

        @Override
        public long read() {
            long k = getRandomLong();
            Long v = map.get(k);

            if (v == null) return k;
            else return v;
        }
    }


    public static class LockedHashMapShared implements SharedData {

        private Lock lock = new ReentrantLock();
        private final Map<Long, Long> map = new HashMap<>();

        @Override
        public long write() {
            lock.lock();
            try {
                long k = getRandomLong();
                map.put(k, k);
                return k;

            } finally {
                lock.unlock();
            }
        }

        @Override
        public long read() {
            lock.lock();
            try {
                long k = getRandomLong();
                Long v = map.get(k);

                if (v == null) return k;
                else return v;

            } finally {
                lock.unlock();
            }
        }
    }


    public static class ReadWriteLockedHashMapShared implements SharedData {

        private final Map<Long, Long> map = new HashMap<>();
        private ReadWriteLock lock = new ReentrantReadWriteLock();

        @Override
        public long write() {
            lock.writeLock().lock();
            try {
                long k = getRandomLong();
                map.put(k, k);
                return k;

            } finally {
                lock.writeLock().unlock();
            }
        }

        @Override
        public long read() {
            lock.readLock().lock();
            try {
                long k = getRandomLong();
                Long v = map.get(k);

                if (v == null) return k;
                else return v;

            } finally {
                lock.readLock().unlock();
            }
        }
    }


    public static class ConcurrentHashMapShared implements SharedData {

        private final ConcurrentMap<Long, Long> map = new ConcurrentHashMap<>();

        @Override
        public long write() {
            long k = getRandomLong();
            map.put(k, k);
            return k;
        }

        @Override
        public long read() {
            long k = getRandomLong();
            Long v = map.get(k);

            if (v == null) return k;
            else return v;
        }
    }
}
