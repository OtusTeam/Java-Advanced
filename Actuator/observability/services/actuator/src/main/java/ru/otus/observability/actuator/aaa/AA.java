package ru.otus.observability.actuator.aaa;

import org.springframework.stereotype.Component;

@Component
public class AA {
    private final BB bb;

    public AA(BB bb) {
        this.bb = bb;
    }
}
