package com.otus.java.advanced.reentrantlocks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

public class RepositoryWithStampedLock {
    private Map<String, String> map = new HashMap<>();
    private StampedLock lock = new StampedLock();

    public String getValueByKey(String key) {
        long writeStamp = lock.writeLock();

        try {
            return map.get(key);
        } finally {
            lock.unlockWrite(writeStamp);
        }
    }

    public void putValue(String key, String value) {
        long readStamp = lock.readLock();

        try {
            map.put(key, value);
        } finally {
            lock.unlockRead(readStamp);
        }
    }
}
