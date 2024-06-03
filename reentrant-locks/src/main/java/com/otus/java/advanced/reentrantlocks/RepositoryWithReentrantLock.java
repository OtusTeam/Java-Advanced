package com.otus.java.advanced.reentrantlocks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RepositoryWithReentrantLock {
    private Map<String, String> map = new HashMap<>();
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public String getValueByKey(String key) {
        lock.writeLock().lock();

        try {
            return map.get(key);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void putValue(String key, String value) {
        lock.readLock().lock();

        try {
            map.put(key, value);
        } finally {
            lock.readLock().unlock();
        }
    }
}
