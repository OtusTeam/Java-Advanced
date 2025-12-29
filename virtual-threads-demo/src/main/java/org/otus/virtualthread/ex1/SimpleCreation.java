package org.otus.virtualthread.ex1;

import static java.text.MessageFormat.format;

public class SimpleCreation {

    public static void main(String[] args) throws InterruptedException {
        sample1();
        sample2();
        sample3();
        sample4();
    }

    private static void sample1() throws InterruptedException {
        Thread.Builder.OfVirtual ofVirtual = Thread.ofVirtual();
        Thread thread = ofVirtual.start(
                () -> System.out.println(format("S1: Hello from virtual thread! IsVirtual={0}", Thread.currentThread().isVirtual()))
        );
        thread.join();
    }

    private static void sample2() throws InterruptedException {
        Thread.Builder ofVirtual = Thread.ofVirtual();
        ofVirtual.name("otus-virtual-thread");
        Thread thread = ofVirtual.unstarted(
                () -> System.out.println(format("S2: Hello from virtual thread! IsVirtual={0}, name={1}",
                        Thread.currentThread().isVirtual(),
                        Thread.currentThread().getName()))
        );
        thread.start();
        thread.join();
    }

    private static void sample3() throws InterruptedException {
        Thread.Builder ofPlatform = Thread.ofPlatform();
        ofPlatform.name("otus-platform-thread");
        Thread thread = ofPlatform.start(
                () -> System.out.println(format("S3: Hello from platform thread! IsVirtual={0}, name={1}",
                        Thread.currentThread().isVirtual(),
                        Thread.currentThread().getName()))
        );
        thread.join();
    }

    private static void sample4() throws InterruptedException {
        Thread.Builder builder = Thread.ofVirtual().name("worker-", 0);

        Runnable task =
                () -> System.out.println(format("S4: Thread: ID={0}, name={1}, isVirtual={2}, toString={3}",
                        Thread.currentThread().threadId(),
                        Thread.currentThread().getName(),
                        Thread.currentThread().isVirtual(),
                        Thread.currentThread().toString()));

        // name "worker-0"
        Thread t1 = builder.start(task);
        t1.join();
        System.out.println(format("S4: {0} terminated", t1.getName()));

        // name "worker-1"
        Thread t2 = builder.start(task);
        t2.join();
        System.out.println(format("S4: {0} terminated", t2.getName()));
    }
}
