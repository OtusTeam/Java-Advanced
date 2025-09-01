package ru.otus.observability.actuator;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActuatorConfig {

    @Bean
    @ConditionalOnProperty(name = "config.customConditional.enabled", havingValue = "true")
    public CustomConditional customConditional() {
        return new CustomConditional();
    }

}
