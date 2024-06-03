package com.otus.java.advanced.reentrantlocks;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

public class StampedLockDemo implements Runnable {

    // Параметр справедливости, используемый для создания объекта блокировки, снижает производительность программы.
    private String name;
    private StampedLock lock;
    public StampedLockDemo(String name, StampedLock lock) {
        this.name = name;
        this.lock = lock;
    }
    public void run() {
        boolean done = false;
        while (!done) {
            // Getting Outer Lock
            long readLockStamp = lock.tryReadLock();

            // Returns True if lock is free
            if (readLockStamp != 0) {
                try {
                    Date d = new Date();
                    SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
                    System.out.println(
                            "task name - " + name
                                    + " outer lock acquired at "
                                    + ft.format(d)
                                    + " Doing outer work");
                    Thread.sleep(1500);

                    // Getting Inner Lock
                    long writeLockStamp = lock.tryWriteLock();
                    if (writeLockStamp != 0) {
                        try {
                            d = new Date();
                            ft = new SimpleDateFormat("hh:mm:ss");
                            System.out.println(
                                    "task name - " + name
                                            + " inner lock acquired at "
                                            + ft.format(d)
                                            + " Doing inner work"
                            );
                            //System.out.println("Lock Hold Count - " + lock.getHoldCount());
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            // Inner lock release
                            System.out.println("task name - " + name + " releasing inner lock");

                            lock.unlockWrite(writeLockStamp);
                        }
                    }
                    //System.out.println("Lock Hold Count - " + lock.getHoldCount());
                    System.out.println("task name - " + name + " work done");

                    done = true;
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    // Outer lock release
                    System.out.println("task name - " + name + " releasing outer lock");

                    lock.unlockRead(readLockStamp);
                    //System.out.println("Lock Hold Count - " + lock.getHoldCount());
                }
            }
            else {
                System.out.println("task name - " + name + " waiting for lock");
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
