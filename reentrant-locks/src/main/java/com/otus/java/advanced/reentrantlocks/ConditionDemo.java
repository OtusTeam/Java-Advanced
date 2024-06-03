package com.otus.java.advanced.reentrantlocks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo extends Thread {

    /*
    Even Odd
    Четные потоки используют четный объект Condition для ожидания, используя метод await(),
    а нечетные потоки используют нечетное условие для ожидания, используя методы await().

    Когда общий ресурс (t) четный, освобождается четный поток с помощью метода signal(),
    поскольку последнее напечатанное значение является нечетным значением, сгенерированным нечетным потоком.

    Аналогично, когда общий ресурс(t) нечетный, освобождается нечетный поток с помощью метода signal(),
    поскольку последнее напечатанное значение является четным значением, созданным четным потоком.

    Важно:
    Поток, использующий условие ожидания с помощью await(),
    должен избегать передачи сигнала об этом же условии с помощью метода signal().
    Пример: четный поток использует условие четности для ожидания с помощью await(),
    и тот же поток не должен отправлять сигнал с сигналом() условия четности.

    await() и signal() аналогичны wait() и notify() в синхронизированном блоке.
     */
    ReentrantLock lock = new ReentrantLock();

    Condition even = lock.newCondition();
    Condition odd = lock.newCondition();
    int t;
    ConditionDemo(int t) {
        this.t=t;
    }

    ConditionDemo() {
        this.t=0;
    }
    int MAX_COUNT = 10;
    public void run() {
        while (t <= MAX_COUNT) {
            lock.lock();
            try {
                if (t % 2 == 1 && Thread.currentThread().getName().equals("even")) {
                    even.await();
                } else if (t % 2 == 0 && Thread.currentThread().getName().equals("odd")) {
                    odd.await();
                } else {
                    System.out.println(Thread.currentThread().getName() + " Thread " + t);
                    t += 1;
                    if (t % 2 == 0) {
                        even.signal();
                    } else if (t % 2 == 1) {
                        odd.signal();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
