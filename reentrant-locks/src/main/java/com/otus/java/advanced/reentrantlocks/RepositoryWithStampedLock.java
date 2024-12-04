package com.otus.java.advanced.reentrantlocks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

public class RepositoryWithStampedLock {
    private Map<String, String> map = new HashMap<>();
    private StampedLock lock = new StampedLock();

    public String getValueByKey(String key) {
        long readStamp = lock.readLock();

        try {
            return map.get(key);
        } finally {
            lock.unlockRead(readStamp);
        }
    }

    public void putValue(String key, String value) {
        long writeStamp = lock.writeLock();

        try {
            map.put(key, value);
        } finally {
            lock.unlockWrite(writeStamp);
        }
    }
}
