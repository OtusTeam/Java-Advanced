package org.otus.virtualthread.ex3;

import java.util.concurrent.ThreadFactory;

public class BatchCreation {

    //    public static final int THEAD_COUNT = 10; // lesson 1
//    public static final int THEAD_COUNT = 1_000_000; // lesson 2    For heap dump & further analyzing
    public static final int THEAD_COUNT = 100; // lesson 3   For VisualVM

    public static final int CAPACITY = 1;

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1) {
            System.err.println("Usage: java BatchCreation <isVirtual>");
            System.exit(1);
        }

        boolean isVirtual = Boolean.parseBoolean(args[0]);
        if (isVirtual) {
            startVirtual();
        } else {
            startPlatform();
        }
    }

    private static void startPlatform() throws InterruptedException {
        Thread.Builder builder = Thread.ofPlatform();

        startThreads(builder);
    }

    private static void startVirtual() throws InterruptedException {
        Thread.Builder builder = Thread.ofVirtual();

        startThreads(builder);
    }

    private static void startThreads(Thread.Builder builder) throws InterruptedException {
        ThreadFactory factory = builder.factory();

        Runnable task = new MyRunnableWithSleep();
//        Runnable task = () -> {
//            long[] value = new long[CAPACITY];
//            while (true) {
//                for (int i = 0; i < 10_000; i++) {
//                    value[0] += i * i;
//                }
//                accept(value);
//            }
//        };

        Thread last = null;
        for (int i = 0; i < THEAD_COUNT; i++) {
            last = factory.newThread(task);
            last.start();
            if (i % 10 == 0) {
                System.out.println("Thread " + last.threadId() + "  started (virtual=" + last.isVirtual() + " )");
            }
        }

        if (builder instanceof Thread.Builder.OfVirtual) {
            last.join();
        }
    }

    private static long accept(long[] value) {
        return value[0] * value[0];
    }
}
