package com.otus.java.advanced.resilience.examples;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class Controller {

    private final ClientApi clientApi;

    public Controller(ClientApi clientApi) {
        this.clientApi = clientApi;
    }

    @GetMapping("/retry")
    public String retryApi() {
        return clientApi.callRetryApi();
    }

    @GetMapping("/rate-limiter")
    public String rateLimiterApi() {
        return clientApi.callRateLimiterApi();
    }

    @GetMapping("/rate-rpm-limiter")
    public String rateRmpLimiterApi() {
        return clientApi.callRateRpmLimiterApi();
    }

    @GetMapping("/time-limiter")
    public CompletableFuture<String> timeLimiterApi() {
        return clientApi.callTimeLimiterApi();

    }

    @GetMapping("/circuit-breaker")
    public String circuitBreakerApi() {
        return clientApi.circuitBreakerApi();
    }

    @GetMapping("/bulkhead")
    public String bulkheadApi() {
        return clientApi.bulkhead();
    }
}
