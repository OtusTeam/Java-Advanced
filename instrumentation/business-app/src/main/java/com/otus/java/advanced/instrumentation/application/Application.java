package com.otus.java.advanced.instrumentation.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        Application.run(args);
    }

    public static void run(String[] args) throws Exception {
        log.info("Starting application");
        SomeService service = new SomeService();
        Random random = new Random(Instant.now().getEpochSecond());

        while (true) {
            int data = random.nextInt();
            log.info("Process data: {}", data);
            service.process(data);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
