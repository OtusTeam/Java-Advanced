package com.example.javamemory;

import java.lang.ref.PhantomReference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public class ReferenceQueue {
    public static void main(String[] args) throws InterruptedException {
        Object object = new Object();
        java.lang.ref.ReferenceQueue<Object> queue = new java.lang.ref.ReferenceQueue<>();
        PhantomReference<Object> phantom = new PhantomReference<>(object, queue);
        WeakReference<Object> weakReference = new WeakReference<>(object, queue);
        object = null;

        System.gc();

        TimeUnit.SECONDS.sleep(3);
        System.out.println("from link " + phantom.get());
        //System.out.println("from link weak " + weakReference.get());
        System.out.println("from queue " + queue.poll());
    }
}
