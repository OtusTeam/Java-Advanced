package ru.otus.observability.actuator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ActuatorController {

    private final CacheableExample cacheableExample;

    private final Logger logger = LoggerFactory.getLogger(ActuatorController.class);

    public ActuatorController(CacheableExample cacheableExample) {
        this.cacheableExample = cacheableExample;
    }

    @GetMapping("/helloOtus")
    public String sayHello() {
        logger.info("Info");
        logger.debug("Debug");
        logger.warn("Warn");
        logger.error("Error");
        cacheableExample.getProductById("1");
        return "Привет, Otus!";
    }

}


