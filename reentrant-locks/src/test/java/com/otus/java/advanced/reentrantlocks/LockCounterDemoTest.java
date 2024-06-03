package com.otus.java.advanced.reentrantlocks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import org.junit.jupiter.api.Test;

public class LockCounterDemoTest {
    static final int MAX_T = 2;

    @Test
    public void test() {
        ReentrantLock lock = new ReentrantLock();
        try (ExecutorService pool = Executors.newFixedThreadPool(MAX_T)) {
            Runnable w1 = new LockCounterDemo("Job1", lock);
            Runnable w2 = new LockCounterDemo("Job2", lock);
            Runnable w3 = new LockCounterDemo("Job3", lock);
            Runnable w4 = new LockCounterDemo("Job4", lock);
            pool.execute(w1);
            pool.execute(w2);
            pool.execute(w3);
            pool.execute(w4);
        }
    }
}
