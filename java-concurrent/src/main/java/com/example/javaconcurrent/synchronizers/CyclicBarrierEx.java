package com.example.javaconcurrent.synchronizers;

import java.util.concurrent.CyclicBarrier;


public class CyclicBarrierEx {
        private static final CyclicBarrier BARRIER = new CyclicBarrier(10, new Game());
        //Инициализируем барьер на три потока и таском, который будет выполняться, когда
        //у барьера соберется три потока. После этого, они будут освобождены.

        public static void main(String[] args) throws InterruptedException {
            for (int i = 1; i <= 30; i++) {
                new Thread(new Gamer(i)).start();
                Thread.sleep(400);
            }
        }

        //Таск, который будет выполняться при достижении сторонами барьера
        public static class Game implements Runnable {
            @Override
            public void run() {
                try {
                    System.out.println("Game started");
                    Thread.sleep(500);
                    System.out.println("Game over!");
                } catch (InterruptedException e) {
                }
            }
        }

        //Стороны, которые будут достигать барьера
        public static class Gamer implements Runnable {
            private int gamerNumber;

            public Gamer(int gamerNumber) {
                this.gamerNumber = gamerNumber;
            }

            @Override
            public void run() {
                try {
                    System.out.printf("Gamer №%d connected to the server.\n", gamerNumber);
                    //Для указания потоку о том что он достиг барьера, нужно вызвать метод await()
                    //После этого данный поток блокируется, и ждет пока остальные стороны достигнут барьера
                    BARRIER.await();
                    System.out.printf("Gamer №%d finished the game.\n", gamerNumber);
                } catch (Exception e) {
                }
            }
        }
}

