package com.otus.java.advanced.synchronizers;

import java.util.concurrent.CyclicBarrier;


public class CyclicBarrierEx {
        private static final CyclicBarrier BARRIER = new CyclicBarrier(10, new Game());

        public static void main(String[] args) throws InterruptedException {
            for (int i = 1; i <= 30; i++) {
                new Thread(new Gamer(i)).start();
                Thread.sleep(400);
            }
        }

        public static class Game implements Runnable {
            @Override
            public void run() {
                try {
                    System.out.println("start");
                    Thread.sleep(500);
                    System.out.println("finish");
                } catch (InterruptedException e) {
                }
            }
        }

        public static class Gamer implements Runnable {
            private int gamerNumber;

            public Gamer(int gamerNumber) {
                this.gamerNumber = gamerNumber;
            }

            @Override
            public void run() {
                try {
                    System.out.printf("Player №%d connected to the server.\n", gamerNumber);
                    BARRIER.await();
                    System.out.printf("Player №%d has finished the game.\n", gamerNumber);
                } catch (Exception e) {
                }
            }
        }
}

