package com.example.demo_server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MyController {
    private static final Logger log = LoggerFactory.getLogger(MyController.class);

    private final GreetingService greetingService;

    public MyController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/hello/{name}")
    public String userName(@PathVariable("name") String name) {
        log.info("Got a request");
        return greetingService.greet(name);
    }
}