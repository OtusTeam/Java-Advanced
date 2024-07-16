package com.otus.java.advanced.synchronizers;

import java.util.ArrayList;
import java.util.concurrent.Phaser;

public class PhaserPassengers {
    private static final Phaser PHASER = new Phaser(1);

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Passenger> passengersList = new ArrayList<>();

        for (int i = 1; i < 5; i++) {
            if ((int) (Math.random() * 2) > 0)
                passengersList.add(new Passenger(i, i + 1));

            if ((int) (Math.random() * 2) > 0)
                passengersList.add(new Passenger(i, 5));
        }

        for (int i = 0; i < 7; i++) {
            switch (i) {
                case 0:
                    System.out.println("Bus left the park.");
                    PHASER.arrive();
                    break;
                case 6:
                    System.out.println("Bus went to the park.");
                    PHASER.arriveAndDeregister();
                    break;
                default:
                    int currentBusStop = PHASER.getPhase();
                    System.out.println("bus stop № " + currentBusStop);

                    for (Passenger p : passengersList)
                        if (p.departure == currentBusStop) {
                            PHASER.register();
                            p.start();
                        }

                    PHASER.arriveAndAwaitAdvance();
            }
        }
    }

    public static class Passenger extends Thread {
        private int departure;
        private int destination;

        public Passenger(int departure, int destination) {
            this.departure = departure;
            this.destination = destination;
            System.out.println(this + " waits on bus stop  № " + this.departure);
        }

        @Override
        public void run() {
            try {
                System.out.println(this + " get in the bus.");

                while (PHASER.getPhase() < destination)
                    PHASER.arriveAndAwaitAdvance();

                Thread.sleep(1);
                System.out.println(this + " left the bus.");
                PHASER.arriveAndDeregister();
            } catch (InterruptedException e) {
            }
        }

        @Override
        public String toString() {
            return "Passanger{" + departure + " -> " + destination + '}';
        }
    }
}
