package ru.otus.observability.actuator;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service

public class CacheableExample {

    @Cacheable("products")
    public String getProductById(String id) {
        return "Product-" + id;
    }
}
