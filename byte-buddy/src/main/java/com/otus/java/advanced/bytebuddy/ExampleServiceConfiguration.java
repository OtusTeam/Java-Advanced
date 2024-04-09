package com.otus.java.advanced.bytebuddy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExampleServiceConfiguration {

    @Bean
    public ExampleService exampleService() {
        return new ExampleService();
    }

}
