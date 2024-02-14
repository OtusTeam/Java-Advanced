package com.otus.firstopenlesson.controllers;

import com.otus.firstopenlesson.services.HashService;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
public class HashController {

    private final MeterRegistry meterRegistry;

    private final HashService hashService;

    @GetMapping("/hash")
    public ResponseEntity<String> hash(RequestEntity<String> rq) {
        log.debug("Income request: headers=" + rq.getHeaders() + ", body=" + rq.getBody());

        String result = null;
        if (rq.getBody() != null && !rq.getBody().isBlank()) {
            result = meterRegistry.timer("hash.time").record(() -> hashService.hash(rq.getBody()));
        }

        ResponseEntity<String> rs = ResponseEntity.of(Optional.of("Hash=" + result));
        log.debug("Outcome response: rs=" + rs);
        return rs;
    }
}
