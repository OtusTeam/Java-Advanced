package com.example.java21.virtualthreads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class VirtualThreads {
    public static void main(String[] args) throws Exception {
        try(ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            executorService.execute(() -> System.out.println("Hello from virtual thread: " + Thread.currentThread().isVirtual()));
        }
        ThreadFactory factory = Thread.ofVirtual().factory();
        Thread thread = factory.newThread(() -> System.out.println(STR."Virtual thread from threadFactory = \{Thread.currentThread().isVirtual()}"));
        thread.start();
        thread.join();

    }
}
