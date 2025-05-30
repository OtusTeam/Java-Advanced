package com.example.javaconcurrent.datarace;

import java.util.concurrent.atomic.AtomicInteger;

public class DataRaceExample {
    private static final AtomicInteger counter = new AtomicInteger(0);

//    private static volatile int counter = 0; // visibility есть, но не атомарность!

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
//                counter++; // НЕ атомарная операция: read → inc → write
                counter.incrementAndGet();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
//                counter++;
                counter.incrementAndGet();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        // Если после запуска счётчик < 2000 — часть инкрементов теряется из-за гонки
        System.out.println("Res: " + counter);
    }
}
