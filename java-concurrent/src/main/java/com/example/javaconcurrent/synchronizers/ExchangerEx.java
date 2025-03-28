package com.otus.java.advanced.synchronizers;

import java.util.concurrent.Exchanger;

public class ExchangerEx {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        Thread thread1 = new Thread(() -> {
            try {
                String data1 = "Data from Service 1";
                System.out.println("Thread 1 is exchanging data: " + data1);
                Thread.sleep(2000); // имитация работы
                String receivedData = exchanger.exchange(data1);
                System.out.println("Thread 1 received: " + receivedData);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                String data2 = "Data from Service 2";
                System.out.println("Thread 2 is exchanging data: " + data2);
                Thread.sleep(3000); // имитация работы
                String receivedData = exchanger.exchange(data2);
                System.out.println("Thread 2 received: " + receivedData);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();
    }
}
