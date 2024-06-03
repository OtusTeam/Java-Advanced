package com.otus.java.advanced.reentrantlocks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;
import org.junit.jupiter.api.Test;

public class StampedLockDemoTest {
    static final int MAX_T = 2;

    @Test
    public void test() {
        StampedLock lock = new StampedLock();
        try (ExecutorService pool = Executors.newFixedThreadPool(MAX_T)) {
            Runnable w1 = new StampedLockDemo("Job1", lock);
            Runnable w2 = new StampedLockDemo("Job2", lock);
            Runnable w3 = new StampedLockDemo("Job3", lock);
            Runnable w4 = new StampedLockDemo("Job4", lock);
            pool.execute(w1);
            pool.execute(w2);
            pool.execute(w3);
            pool.execute(w4);
        }
    }
}
