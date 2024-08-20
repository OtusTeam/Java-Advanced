package com.otus.java.advanced.resilience.examples;

import java.util.concurrent.CompletableFuture;

public interface ClientApi {

    String callRetryApi();

    String callRateLimiterApi();

    String callRateRpmLimiterApi();

    CompletableFuture<String> callTimeLimiterApi();

    String circuitBreakerApi();

    String bulkhead();
}
