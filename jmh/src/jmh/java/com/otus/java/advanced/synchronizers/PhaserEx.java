package com.otus.java.advanced.synchronizers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Phaser;

public class PhaserEx {
    public static void main(String[] args) {
        Phaser phaser = new Phaser(2);

        Thread writerThread1 = new Thread(() -> {
            try {
                phaser.arriveAndAwaitAdvance();
                FileWriter writer = new FileWriter("output.txt", true);
                writer.write("This is written by Thread 1\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread writerThread2 = new Thread(() -> {
            try {
                phaser.arriveAndAwaitAdvance();
                FileWriter writer = new FileWriter("output.txt", true);
                writer.write("This is written by Thread 2\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        writerThread1.start();
        writerThread2.start();

        try {
            writerThread1.join();
            writerThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
