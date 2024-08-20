package com.otus.java.advanced.resilience.examples.clients;

import com.otus.java.advanced.resilience.examples.ClientApi;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class ClientAdapter implements ClientApi {

    private final ClientRest clientRest;

    private final RPMRateLimitedSearch rpmRateLimitedSearch;

    public ClientAdapter(ClientRest clientRest, RPMRateLimitedSearch rpmRateLimitedSearch) {
        this.clientRest = clientRest;
        this.rpmRateLimitedSearch = rpmRateLimitedSearch;
    }


    @Override
    @Retry(name = "default", fallbackMethod = "fallback")
    // @Retry(name = "throwingException")
    public String callRetryApi() {
        log.info("Call retry api");
        return clientRest.callApi();
    }

    @Override
    @RateLimiter(name = "default")
    public String callRateLimiterApi() {
        log.info("Call rate limit api");
        return clientRest.callApi();
    }

    @Override
    @RateLimiter(name = "default")
    public String callRateRpmLimiterApi() {
        return rpmRateLimitedSearch.callRateLimiterApi();
    }

    @Override
    @TimeLimiter(name = "default")
    public CompletableFuture<String> callTimeLimiterApi() {
        log.info("Call time limit api");
        return CompletableFuture.supplyAsync(clientRest::callApi);
    }

    @Override
    @CircuitBreaker(name = "default")
    public String circuitBreakerApi() {
        log.info("Call circuit breaker api");
        return clientRest.callApi();
    }

    @Override
    @Bulkhead(name="default")
    public String bulkhead() {
        log.info("Call bulkhead api");
        return clientRest.callApi();
    }

    public String fallback(IllegalStateException e) {
        log.info("fallbackMethod Call api");
        return clientRest.fallback();
    }
}
