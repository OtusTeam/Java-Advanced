package com.example.javaconcurrent.synchronizers;

import java.util.concurrent.Semaphore;

public class SemaphoreEx {
    public static class Server {
        //Место на сервере занято - true, свободно - false
        private static final boolean[] SERVER_SLOTS = new boolean[5];
        //Устанавливаем флаг "справедливый", в таком случае метод
        //aсquire() будет раздавать разрешения в порядке очереди
        private static final Semaphore SEMAPHORE = new Semaphore(5, true);

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
                    //acquire() запрашивает доступ к следующему за вызовом этого метода блоку кода,
                    //если доступ не разрешен, поток вызвавший этот метод блокируется до тех пор,
                    //пока семафор не разрешит доступ
                    SEMAPHORE.acquire();

                    int slotNumber = -1;

                    //Wait for space to become available and connect to the server
                    synchronized (SERVER_SLOTS){
                        for (int i = 0; i < 5; i++)
                            if (!SERVER_SLOTS[i]) {      //Если место свободно
                                SERVER_SLOTS[i] = true;  //занимаем его
                                slotNumber = i;         //Наличие свободного места, гарантирует семафор
                                System.out.printf("the user №%d connected to the server.\n", userId, i);
                                break;
                            }
                    }

                    Thread.sleep(5000);       //Имитируем работу пользователя

                    synchronized (SERVER_SLOTS) {
                        SERVER_SLOTS[slotNumber] = false;//Освобождаем место
                    }

                    //release(), напротив, освобождает ресурс
                    SEMAPHORE.release();
                    System.out.printf("user №%d disconnected from server.\n", userId);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
