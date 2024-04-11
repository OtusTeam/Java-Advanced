package com.otus.java.advanced.instrumentation.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class SomeService {
    private static final Logger log = LoggerFactory.getLogger(SomeService.class);

    public void process(int data) throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);

        /* LESSON Demo of 'Reload changed classes' idea's feature */
        log.info("Successful operation: {}", data);
    }
}
