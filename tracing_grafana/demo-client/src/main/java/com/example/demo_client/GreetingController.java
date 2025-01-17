package com.example.demo_client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GreetingController {
    private static final Logger log = LoggerFactory.getLogger(GreetingController.class);

    private final RestTemplate restTemplate;
    private final String greetingService;

    public GreetingController(RestTemplate restTemplate,
                              @Value("${com.example.greeting.service}") String greetingService) {
        this.restTemplate = restTemplate;
        this.greetingService = greetingService;
    }

    @PostMapping("/greet")
    public GreetingResponse greet(@RequestBody GreetingRequest greetingRequest) {
        log.info("Received /greet request");
        final var url = greetingService + "/hello/" + greetingRequest.getName();
        log.info("Sending request to {}", url);
        final var greeting = restTemplate.getForEntity(url, String.class).getBody();
        log.info("Received greeting: {}", greeting);
        return new GreetingResponse(greeting);
    }
}
