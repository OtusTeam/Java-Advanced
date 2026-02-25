package com.otus.javaadvanced;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.ContextRefreshedEvent;

@SpringBootApplication
@Slf4j
@EnableAspectJAutoProxy
public class HashApplication /*implements ApplicationListener<ApplicationStartedEvent>*/ {

    public static void main(String[] args) {
        SpringApplication.run(HashApplication.class, args);
    }

    //region lesson CDS & ProjectLeyden
/*
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        log.info("Application started. Exiting...");
        Runtime.getRuntime().halt(-777);
    }
*/
    //endregion
}