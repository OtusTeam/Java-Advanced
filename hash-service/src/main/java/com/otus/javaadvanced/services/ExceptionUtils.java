package com.otus.javaadvanced.services;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public final class ExceptionUtils {

    private static long exceptionCount = 0;

    private static Lock lock = new ReentrantLock();

    public static long countException(Exception e) {
        lock.lock();
        try {
            return ++exceptionCount;
        } finally {
            lock.unlock();
        }
    }

    public static void acceptCount(long count) {
        long index = count % 100;

        if (index == 0) {
            log.debug("Current exception count: {}", count);
        }
    }
}
