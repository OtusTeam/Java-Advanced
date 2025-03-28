package com.example.javaconcurrent.racecondition;

import java.util.concurrent.atomic.AtomicInteger;

class Counter implements Runnable
{
    private AtomicInteger c = new AtomicInteger(0);
    public void increment()
    {
        try
        {
            Thread.sleep(10);
        }
        catch (InterruptedException e)
        {
//Auto-generated catch block
            e.printStackTrace();
        }
        c.incrementAndGet();
    }
    public void decrement()
    {
        c.decrementAndGet();
    }
    public int getValue()
    {
        return c.get();
    }
    @Override
    public void run()
    {
        System.out.println("Value before increment " + Thread.currentThread().getName() + " " + this.getValue());
//incrementing
        this.increment();
        System.out.println("Value for Thread After increment " + Thread.currentThread().getName() + " " + this.getValue());
//decrementing
        this.decrement();
        System.out.println("Value for Thread at last " + Thread.currentThread().getName() + " " + this.getValue());
    }
}
public class RaceCondition
{
    public static void main(String args[])
    {
        Counter counter = new Counter();
        Thread t1 = new Thread(counter, "Thread-1");
        Thread t2 = new Thread(counter, "Thread-2");
        Thread t3 = new Thread(counter, "Thread-3");
        t1.start();
        t2.start();
        t3.start();
    }
}
