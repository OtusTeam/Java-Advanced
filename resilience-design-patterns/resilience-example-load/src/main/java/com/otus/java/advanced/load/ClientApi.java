package com.otus.java.advanced.load;

public interface ClientApi {

    String sendOne(int sleepTimeout) throws InterruptedException;

    String sendTwo();
}
