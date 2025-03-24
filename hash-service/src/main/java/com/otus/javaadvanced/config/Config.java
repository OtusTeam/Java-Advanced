package com.otus.javaadvanced.config;

import com.otus.javaadvanced.services.EncodeService;
import com.otus.javaadvanced.services.Encoder;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Configuration
public class Config {

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }

    @Bean
    public EncodeService encodeService(Encoder encoder) {
        return new EncodeService(encoder);
    }

    @Bean
    public Encoder encoder() {
        return (src) -> Base64.getEncoder().encode(src);
    }

    @Bean
    public FeatureFlags init() {
        return new FeatureFlags();
    }
}
