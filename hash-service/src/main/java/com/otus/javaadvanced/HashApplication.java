package com.otus.javaadvanced;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class HashApplication /*implements ApplicationListener<ContextRefreshedEvent>*/ {

    public static void main(String[] args) {
        SpringApplication.run(HashApplication.class, args);
    }
}