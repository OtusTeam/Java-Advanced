package com.otus.java.advanced.synchronizers;

import java.util.concurrent.CountDownLatch;

public class CountDawnLatchEx {
    private static final CountDownLatch START = new CountDownLatch(8);
    private static final int trackLength = 500000;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 5; i++) {
            new Thread(new Car(i, (int) (Math.random() * 100 + 50))).start();
            Thread.sleep(1000);
        }

        while (START.getCount() > 3)
            Thread.sleep(100);

        Thread.sleep(1000);
        System.out.println("On start!");
        START.countDown();
        Thread.sleep(1000);
        System.out.println("Attansion!");
        START.countDown();
        Thread.sleep(1000);
        System.out.println("go!");
        START.countDown();
    }

    public static class Car implements Runnable {
        private int carNumber;
        private int carSpeed;

        public Car(int carNumber, int carSpeed) {
            this.carNumber = carNumber;
            this.carSpeed = carSpeed;
        }

        @Override
        public void run() {
            try {
                System.out.printf("The car №%d has arrived to the start line.\n", carNumber);
                START.countDown();
                START.await();
                Thread.sleep(trackLength / carSpeed);
                System.out.printf("The car №%d has finished!\n", carNumber);
            } catch (InterruptedException e) {
            }
        }
    }
}
