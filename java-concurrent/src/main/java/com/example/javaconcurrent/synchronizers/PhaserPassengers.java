package com.example.javaconcurrent.synchronizers;

import java.util.ArrayList;
import java.util.concurrent.Phaser;

public class PhaserPassengers {
    private static final Phaser PHASER = new Phaser(1);//Сразу регистрируем главный поток
    //Фазы 0 и 6 - это автобусный парк, 1 - 5 остановки

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Passenger> passengersList = new ArrayList<>();

        for (int i = 1; i < 5; i++) {           //Сгенерируем пассажиров на остановках
            if ((int) (Math.random() * 2) > 0)
                passengersList.add(new Passenger(i, i + 1));//Этот пассажир выходит на следующей

            if ((int) (Math.random() * 2) > 0)
                passengersList.add(new Passenger(i, 5));    //Этот пассажир выходит на конечной
        }

        for (int i = 0; i < 7; i++) {
            switch (i) {
                case 0:
                    System.out.println("Bus going from park.");
                    PHASER.arrive();//В фазе 0 всего 1 участник - автобус
                    break;
                case 6:
                    System.out.println("Bus arrived to the park.");
                    PHASER.arriveAndDeregister();//Снимаем главный поток, ломаем барьер
                    break;
                default:
                    int currentBusStop = PHASER.getPhase();
                    System.out.println("Stop № " + currentBusStop);

                    for (Passenger p : passengersList)          //Проверяем, есть ли пассажиры на остановке
                        if (p.departure == currentBusStop) {
                            PHASER.register();//Регистрируем поток, который будет участвовать в фазах
                            p.start();        // и запускаем
                        }

                    PHASER.arriveAndAwaitAdvance();//Сообщаем о своей готовности
            }
        }
    }

    public static class Passenger extends Thread {
        private int departure;
        private int destination;

        public Passenger(int departure, int destination) {
            this.departure = departure;
            this.destination = destination;
            System.out.println(this + " waiting on station № " + this.departure);
        }

        @Override
        public void run() {
            try {
                System.out.println(this + " set to the bus.");

                while (PHASER.getPhase() < destination) //Пока автобус не приедет на нужную остановку(фазу)
                    PHASER.arriveAndAwaitAdvance();     //заявляем в каждой фазе о готовности и ждем

                Thread.sleep(1);
                System.out.println(this + " leave bus.");
                PHASER.arriveAndDeregister();   //Отменяем регистрацию на нужной фазе
            } catch (InterruptedException e) {
            }
        }

        @Override
        public String toString() {
            return "Passenger{" + departure + " -> " + destination + '}';
        }
    }
}
