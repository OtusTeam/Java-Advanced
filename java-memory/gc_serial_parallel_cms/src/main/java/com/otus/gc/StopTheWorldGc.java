package com.otus.gc;

import java.util.ArrayList;
import java.util.List;

public class StopTheWorldGc {
    //-XX:+UseSerialGC -Xms526m -Xmx526m
    public static class WorkThread extends Thread {
        List<byte[]> list = new ArrayList<>();

        public void run() {
            try {
                while (true) {
                    for (int i = 0; i < 10000; i++) {
                        byte[] buffer = new byte[1024];
                        list.add(buffer);
                    }
                    if (list.size() > 400000) {
                        list.clear();
                        System.gc();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static class PrintThread extends Thread {
        public final long startTime = System.currentTimeMillis();

        public void run() {
            try {
                while (true) {
                    long t = System.currentTimeMillis() - startTime;
                    System.out.println(t / 1000 + "." + t % 1000);
                    Thread.sleep(1000);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
      //  WorkThread w = new WorkThread();
        PrintThread p = new PrintThread();
       // w.start();
        p.start();
    }

}
