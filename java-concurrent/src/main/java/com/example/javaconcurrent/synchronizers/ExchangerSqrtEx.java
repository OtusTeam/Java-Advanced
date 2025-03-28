package com.example.javaconcurrent.synchronizers;

import java.util.concurrent.Exchanger;

public class ExchangerSqrtEx {
    public static void main(String[] args) {
        Exchanger<Integer> exchanger = new Exchanger<>();

        // Создание и запуск потоков
        Thread producerThread = new Thread(new Producer(exchanger));
        Thread consumerThread = new Thread(new Consumer(exchanger));

        producerThread.start();
        consumerThread.start();
    }
}

class Producer implements Runnable {
    private final Exchanger<Integer> exchanger;

    public Producer(Exchanger<Integer> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Producer generate number: " + i);
                // Обмен данных с потребителем
                exchanger.exchange(i);
            }
            // Отправляем сигнал о завершении
            exchanger.exchange(null); // Используем null как сигнал о завершении
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumer implements Runnable {
    private final Exchanger<Integer> exchanger;

    public Consumer(Exchanger<Integer> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Ожидание данных от производителя
                Integer data = exchanger.exchange(null);
                if (data == null) { // Проверка на сигнал о завершении
                    break; // Завершаем цикл, если получили сигнал о завершении
                }
                // Обработка данных (вычисление квадрата)
                Integer processedData = data * data;
                System.out.println("Consumer handling number: " + data + ", sqrt: " + processedData);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
