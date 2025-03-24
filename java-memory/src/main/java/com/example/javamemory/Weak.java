package com.example.javamemory;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Weak {
    public static void main(String[] args) throws InterruptedException {
        //example1();
        example2();
    }

    private static void example1() throws InterruptedException {
        Object object = new Object();
        WeakReference<Object> weak = new WeakReference<>(object);
        object = null;
        System.gc();
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Example1:weak = " + weak.get());
    }

    private static void example2() throws InterruptedException {
        List<WeakReference<Object>> objects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            objects.add(new WeakReference<Object>(new Object() {}));
        }
        int liveObjectBefore = 0;
        for (WeakReference<Object> ref : objects) {
            Object object = ref.get();
            if (object != null) {
                liveObjectBefore++;
            }
        }
        System.out.println("Example2:liveObjectBefore - " + liveObjectBefore);
        System.gc();
        int liveObjectAfter = 0;
        for (WeakReference<Object> ref : objects) {
            Object object = ref.get();
            if (object != null) {
                liveObjectAfter++;
            }
        }
        System.out.println("Example2:liveObjectAfter - " + liveObjectAfter);

    }
}
