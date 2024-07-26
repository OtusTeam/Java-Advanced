package com.otus.javaadvanced.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/*
    Добавление индикатора доступности внешненго сервиса
    Просмотр в /actuator/health блок "customService"
 */
@Component(value = "externalService")
public class CustomServiceHealthIndicator implements HealthIndicator {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Health getHealth(boolean includeDetails) {
        return HealthIndicator.super.getHealth((includeDetails));
    }

    @Override
    public Health health() {
        try {
            restTemplate.getForObject("http://localhost:8081/actuator", String.class);
            return new Health.Builder().up().build();
        } catch (RestClientException exception) {
            return new Health.Builder().down().build();
        }
    }
}
