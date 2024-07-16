package com.otus.java.advanced.synchronizers;

import java.util.concurrent.Semaphore;

public class SemaphoreEx {
    public static class Server {
        private static final boolean[] SERVER_SLOTS = new boolean[5];
        private static final Semaphore SEMAPHORE = new Semaphore(5, false);

        public static void main(String[] args) throws InterruptedException {
            for (int i = 1; i <= 15; i++) {
                new Thread(new User(i)).start();
                Thread.sleep(400);
            }
        }

        public static class User implements Runnable {
            private int userId;

            public User(int userId) {
                this.userId = userId;
            }

            @Override
            public void run() {
                System.out.printf("the user №%d sent a connection request.\n", userId);
                try {
                    SEMAPHORE.acquire();

                    int slotNumber = -1;

                    //Wait for space to become available and connect to the server
                    synchronized (SERVER_SLOTS){
                        for (int i = 0; i < 5; i++)
                            if (!SERVER_SLOTS[i]) {
                                SERVER_SLOTS[i] = true;
                                slotNumber = i;
                                System.out.printf("the user №%d connected to the server.\n", userId, i);
                                break;
                            }
                    }

                    Thread.sleep(5000);

                    synchronized (SERVER_SLOTS) {
                        SERVER_SLOTS[slotNumber] = false;
                    }

                    SEMAPHORE.release();
                    System.out.printf("user №%d disconnected from server.\n", userId);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
