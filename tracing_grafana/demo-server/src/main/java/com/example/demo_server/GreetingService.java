package com.example.demo_server;

import io.micrometer.observation.annotation.Observed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {
    private static final Logger log = LoggerFactory.getLogger(GreetingService.class);

    @Observed(name = "greet",
            contextualName = "greet-for-name",
            lowCardinalityKeyValues = {"container", "{container-name}"})
    public String greet(String name) {
        log.info("Sleeping");
        try {
            Thread.sleep(200L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Woke");
        return "Hello, " + name;
    }
}
