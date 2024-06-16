package com.otus.java.advanced.synchronizers;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierAndJoin {
    public static void main(String[] args) {
        // Пример с использованием CyclicBarrier
        CyclicBarrier barrier = new CyclicBarrier(3);

        Runnable task = () -> {
            System.out.println(Thread.currentThread().getName() + " начал выполнение задачи.");
            try {
                barrier.await(); // Поток ожидает другие потоки
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " завершил выполнение задачи.");
        };

        for (int i = 0; i < 3; i++) {
            new Thread(task).start();
        }

        // Пример с использованием join()
        Thread t1 = new Thread(() -> {
            System.out.println("Поток 1 начал выполнение.");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Поток 1 завершил выполнение.");
        });

        Thread t2 = new Thread(() -> {
            System.out.println("Поток 2 начал выполнение.");
            try {
                t1.join(); // Поток 2 ждет завершения потока t1
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Поток 2 завершил выполнение.");
        });

        t1.start();
        t2.start();
    }
}
