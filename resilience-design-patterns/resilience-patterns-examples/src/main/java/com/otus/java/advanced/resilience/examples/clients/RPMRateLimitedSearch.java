package com.otus.java.advanced.resilience.examples.clients;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RPMRateLimitedSearch {

    private final ClientRest clientRest;

    public RPMRateLimitedSearch(ClientRest clientRest) {
        this.clientRest = clientRest;
    }

    @RateLimiter(name = "rpm_limiter")
    public String callRateLimiterApi() {
        log.info("Call rate limit api");
        return clientRest.callApi();
    }
}
